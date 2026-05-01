package com.tripify.tickets.service.service;

import com.tripify.tickets.service.config.property.SearchProperties;
import com.tripify.tickets.service.exception.ProviderException;
import com.tripify.tickets.service.exception.ProviderRateLimitedException;
import com.tripify.tickets.service.exception.TicketNotFoundException;
import com.tripify.tickets.service.model.search.AggregatedTicketSearchResult;
import com.tripify.tickets.service.model.search.ProviderSearchResult;
import com.tripify.tickets.service.model.search.ProviderSearchStatus;
import com.tripify.tickets.service.model.search.TicketSearchRequest;
import com.tripify.tickets.service.model.unified.UnifiedOffer;
import com.tripify.tickets.service.model.unified.UnifiedOffersResponse;
import com.tripify.tickets.service.provider.TicketProvider;
import com.tripify.tickets.service.provider.TicketsProvider;
import com.tripify.tickets.service.service.currency.TicketOfferCurrencyService;
import com.tripify.tickets.service.service.filter.TicketFilterApplier;
import com.tripify.tickets.service.service.filter.TicketFilterResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TicketsSearchService {

    private static final Logger logger = LoggerFactory.getLogger(TicketsSearchService.class);

    private final Map<TicketProvider, TicketsProvider> providersByType;
    private final ProviderAvailability providerAvailability;
    private final TicketOfferStore ticketOfferStore;
    private final TicketSearchAnalyticsPublisher ticketSearchAnalyticsPublisher;
    private final TicketFilterResolver ticketFilterResolver;
    private final TicketFilterApplier ticketFilterApplier;
    private final TicketOfferCurrencyService ticketOfferCurrencyService;
    private final Duration providerTimeout;

    public TicketsSearchService(
            List<TicketsProvider> providers,
            ProviderAvailability providerAvailability,
            TicketOfferStore ticketOfferStore,
            TicketSearchAnalyticsPublisher ticketSearchAnalyticsPublisher,
            TicketFilterResolver ticketFilterResolver,
            TicketFilterApplier ticketFilterApplier,
            TicketOfferCurrencyService ticketOfferCurrencyService,
            SearchProperties searchProperties
    ) {
        this.providersByType = providers.stream()
                .collect(Collectors.toMap(TicketsProvider::getProvider, Function.identity()));
        this.providerAvailability = providerAvailability;
        this.ticketOfferStore = ticketOfferStore;
        this.ticketSearchAnalyticsPublisher = ticketSearchAnalyticsPublisher;
        this.ticketFilterResolver = ticketFilterResolver;
        this.ticketFilterApplier = ticketFilterApplier;
        this.ticketOfferCurrencyService = ticketOfferCurrencyService;
        this.providerTimeout = searchProperties.providerTimeout();
    }

    public AggregatedTicketSearchResult search(TicketSearchRequest request) {
        List<CompletableFuture<ProviderSearchResult>> providerFutures = providersByType.keySet().stream()
                .filter(providerAvailability::isEnabled)
                .map(provider -> searchProviderAsync(provider, request))
                .toList();

        CompletableFuture.allOf(providerFutures.toArray(CompletableFuture[]::new)).join();

        List<ProviderSearchResult> providerResults = providerFutures.stream()
                .map(CompletableFuture::join)
                .toList();

        List<UnifiedOffer> allOffers = new ArrayList<>();
        for (ProviderSearchResult result : providerResults) {
            if (result.response() != null && result.response().offers() != null) {
                allOffers.addAll(result.response().offers());
            }
        }

        allOffers = ticketOfferCurrencyService.normalizeToStorage(allOffers);
        allOffers = filterByBudget(allOffers, request);

        List<String> resolvedFilters = ticketFilterResolver.resolve(request.filters());
        List<UnifiedOffer> visibleOffers = ticketFilterApplier.apply(allOffers, resolvedFilters);

        logger.info(
                "Ticket search completed: origin={}, destination={}, offers={}, visible={}, filters={}",
                request.originCityCode(),
                request.destinationCityCode(),
                allOffers.size(),
                visibleOffers.size(),
                resolvedFilters
        );

        ticketOfferStore.saveAll(visibleOffers);
        ticketSearchAnalyticsPublisher.publishSearchCompleted(
                TicketKafkaEventFactory.toSearch(request),
                allOffers,
                providerResults
        );

        return AggregatedTicketSearchResult.builder()
                .offers(visibleOffers)
                .byProvider(Map.of())
                .completedAt(Instant.now())
                .build();
    }

    private CompletableFuture<ProviderSearchResult> searchProviderAsync(
            TicketProvider provider,
            TicketSearchRequest request
    ) {
        Instant startedAt = Instant.now();

        return CompletableFuture
                .supplyAsync(() -> executeProviderSearch(provider, request, startedAt))
                .orTimeout(providerTimeout.toMillis(), TimeUnit.MILLISECONDS)
                .exceptionally(exception -> handleProviderFailure(provider, startedAt, exception));
    }

    private ProviderSearchResult executeProviderSearch(
            TicketProvider provider,
            TicketSearchRequest request,
            Instant startedAt
    ) {
        if (!providerAvailability.isAvailable(provider)) {
            logger.debug("Skipping temporarily disabled provider: provider={}", provider);
            return buildResult(
                    provider,
                    emptyResponse(),
                    ProviderSearchStatus.SKIPPED,
                    startedAt,
                    "Provider temporarily disabled after rate limit"
            );
        }

        try {
            UnifiedOffersResponse response = providersByType.get(provider).searchOffers(request);
            return buildResult(provider, response, ProviderSearchStatus.SUCCESS, startedAt, null);
        } catch (ProviderRateLimitedException exception) {
            providerAvailability.disableAfterRateLimit(provider);
            throw exception;
        } catch (ProviderException exception) {
            logger.error("Provider returned error: provider={}, origin={}, destination={}",
                    provider, request.originCityCode(), request.destinationCityCode(), exception);
            throw exception;
        }
    }

    private ProviderSearchResult handleProviderFailure(
            TicketProvider provider,
            Instant startedAt,
            Throwable exception
    ) {
        Throwable cause = unwrap(exception);

        if (cause instanceof TimeoutException) {
            logger.warn("Provider search timed out: provider={}, timeout={}", provider, providerTimeout);
            return buildResult(
                    provider,
                    emptyResponse(),
                    ProviderSearchStatus.TIMEOUT,
                    startedAt,
                    "Provider search timed out after " + providerTimeout.toMillis() + "ms"
            );
        }

        if (cause instanceof ProviderRateLimitedException rateLimited) {
            providerAvailability.disableAfterRateLimit(rateLimited.getProvider());
            logger.warn("Provider rate limited: provider={}", provider);
            return buildResult(
                    provider,
                    emptyResponse(),
                    ProviderSearchStatus.RATE_LIMITED,
                    startedAt,
                    cause.getMessage()
            );
        }

        if (cause instanceof ProviderException providerException) {
            logger.error("Provider search failed: provider={}", providerException.getProvider(), cause);
            return buildResult(
                    provider,
                    emptyResponse(),
                    ProviderSearchStatus.FAILED,
                    startedAt,
                    cause.getMessage()
            );
        }

        logger.error("Unexpected provider search failure: provider={}", provider, cause);
        return buildResult(
                provider,
                emptyResponse(),
                ProviderSearchStatus.FAILED,
                startedAt,
                cause.getMessage()
        );
    }

    private static ProviderSearchResult buildResult(
            TicketProvider provider,
            UnifiedOffersResponse response,
            ProviderSearchStatus status,
            Instant startedAt,
            String error
    ) {
        Instant completedAt = Instant.now();
        return ProviderSearchResult.builder()
                .provider(provider)
                .response(response)
                .status(status)
                .durationMs(Duration.between(startedAt, completedAt).toMillis())
                .error(error)
                .completedAt(completedAt)
                .build();
    }

    private static Throwable unwrap(Throwable exception) {
        if (exception instanceof CompletionException completionException && completionException.getCause() != null) {
            return completionException.getCause();
        }
        return exception;
    }

    public UnifiedOffer getById(String tid) {
        return ticketOfferStore.findById(tid)
                .orElseThrow(() -> new TicketNotFoundException(tid));
    }

    private List<UnifiedOffer> filterByBudget(List<UnifiedOffer> offers, TicketSearchRequest request) {
        if (request.budgetMaxAmount() == null) {
            return offers;
        }

        long maxAmountInStorage = ticketOfferCurrencyService.toStorageAmount(
                request.budgetMaxAmount(),
                request.currency().getCode()
        );

        return offers.stream()
                .filter(offer -> offer.price() != null)
                .filter(offer -> offer.price().amount() <= maxAmountInStorage)
                .toList();
    }

    private static UnifiedOffersResponse emptyResponse() {
        return UnifiedOffersResponse.builder().offers(List.of()).build();
    }
}
