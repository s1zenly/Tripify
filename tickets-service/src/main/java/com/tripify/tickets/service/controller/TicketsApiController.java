package com.tripify.tickets.service.controller;

import com.tripify.tickets.generated.api.TicketsApi;
import com.tripify.tickets.generated.model.Currency;
import com.tripify.tickets.generated.model.TicketDetailResponse;
import com.tripify.tickets.generated.model.TicketFiltersResponse;
import com.tripify.tickets.generated.model.TicketsResponse;
import com.tripify.tickets.service.domain.ResolvedTripLocation;
import com.tripify.tickets.service.domain.TripLocations;
import com.tripify.tickets.service.exception.TicketNotFoundException;
import com.tripify.tickets.service.kafka.model.GenerationMode;
import com.tripify.tickets.service.kafka.model.PackHeadersEvent;
import com.tripify.tickets.service.kafka.model.SearchContextEvent;
import com.tripify.tickets.service.kafka.model.UserType;
import com.tripify.tickets.service.mapper.TicketApiMapper;
import com.tripify.tickets.service.mapper.TicketDetailApiMapper;
import com.tripify.tickets.service.model.search.TicketSearchRequest;
import com.tripify.tickets.service.model.unified.Passengers;
import com.tripify.tickets.service.model.unified.UnifiedOffer;
import com.tripify.tickets.service.service.TicketFilterQueryService;
import com.tripify.tickets.service.service.TicketPackViewPublisher;
import com.tripify.tickets.service.service.TicketsSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TicketsApiController implements TicketsApi {

    private static final double SEARCH_BUDGET_RATIO = 0.40;

    private final TicketsSearchService ticketsSearchService;
    private final TicketFilterQueryService ticketFilterQueryService;
    private final TicketApiMapper ticketApiMapper;
    private final TicketDetailApiMapper ticketDetailApiMapper;
    private final TicketPackViewPublisher ticketPackViewPublisher;

    @Override
    public ResponseEntity<TicketsResponse> searchTickets(
            String xAnonymousId,
            String xGenerationId,
            String xRequestId,
            String originCountry,
            String originCity,
            String destinationCountry,
            String destinationCity,
            LocalDate dateFrom,
            LocalDate dateTo,
            Currency currency,
            Integer adults,
            Integer children,
            @Nullable Long budget,
            @Nullable List<String> filters
    ) {
        TicketSearchRequest request = buildSearchRequest(
                originCountry,
                originCity,
                destinationCountry,
                destinationCity,
                dateFrom,
                dateTo,
                adults,
                budget,
                currency,
                children,
                filters
        );

        var result = ticketsSearchService.search(request);
        return ResponseEntity.ok(ticketApiMapper.toTicketsResponse(result.offers(), currency.getValue(), true));
    }

    @Override
    public ResponseEntity<TicketDetailResponse> searchTicket(
            String xAnonymousId,
            String xGenerationId,
            Integer xPackRevision,
            GenerationMode xGenerationMode,
            String xRequestId,
            String originCountry,
            String originCity,
            String destinationCountry,
            String destinationCity,
            LocalDate dateFrom,
            LocalDate dateTo,
            Currency currency,
            Integer adults,
            @Nullable Integer xTicketsRevision,
            Integer children,
            @Nullable Long budget,
            @Nullable List<String> filters
    ) {
        TripLocations.resolveOrBadRequest(originCountry, originCity);
        TripLocations.resolveOrBadRequest(destinationCountry, destinationCity);

        TicketSearchRequest request = buildSearchRequest(
                originCountry,
                originCity,
                destinationCountry,
                destinationCity,
                dateFrom,
                dateTo,
                adults,
                budget,
                currency,
                children,
                filters
        );

        var result = ticketsSearchService.search(request);
        if (result.offers().isEmpty()) {
            throw TicketNotFoundException.forEmptySearch();
        }

        UnifiedOffer firstOffer = result.offers().getFirst();
        TicketDetailResponse response = ticketDetailApiMapper.toTicketDetailResponse(
                firstOffer,
                currency.getValue()
        );

        publishTicketPackView(
                xAnonymousId,
                xGenerationId,
                xPackRevision,
                xGenerationMode,
                xRequestId,
                xTicketsRevision,
                originCountry,
                originCity,
                destinationCountry,
                destinationCity,
                dateFrom,
                dateTo,
                currency,
                adults,
                children,
                budget,
                filters,
                response
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TicketFiltersResponse> getTicketFilters() {
        return ResponseEntity.ok(ticketFilterQueryService.getFiltersCatalog());
    }

    @Override
    public ResponseEntity<TicketDetailResponse> getTicketById(
            String xAnonymousId,
            String xGenerationId,
            Integer xPackRevision,
            GenerationMode xGenerationMode,
            String xRequestId,
            String id,
            String originCountry,
            String originCity,
            String destinationCountry,
            String destinationCity,
            LocalDate dateFrom,
            LocalDate dateTo,
            Currency currency,
            Integer adults,
            @Nullable Integer xTicketsRevision,
            Integer children,
            @Nullable Long budget,
            @Nullable List<String> filters
    ) {
        TripLocations.resolveOrBadRequest(originCountry, originCity);
        TripLocations.resolveOrBadRequest(destinationCountry, destinationCity);

        var offer = ticketsSearchService.getById(id);
        TicketDetailResponse response = ticketDetailApiMapper.toTicketDetailResponse(offer, currency.getValue());

        publishTicketPackView(
                xAnonymousId,
                xGenerationId,
                xPackRevision,
                xGenerationMode,
                xRequestId,
                xTicketsRevision,
                originCountry,
                originCity,
                destinationCountry,
                destinationCity,
                dateFrom,
                dateTo,
                currency,
                adults,
                children,
                budget,
                filters,
                response
        );

        return ResponseEntity.ok(response);
    }

    private void publishTicketPackView(
            String xAnonymousId,
            String xGenerationId,
            Integer xPackRevision,
            GenerationMode xGenerationMode,
            String xRequestId,
            Integer xTicketsRevision,
            String originCountry,
            String originCity,
            String destinationCountry,
            String destinationCity,
            LocalDate dateFrom,
            LocalDate dateTo,
            Currency currency,
            Integer adults,
            Integer children,
            Long budget,
            List<String> filters,
            TicketDetailResponse response
    ) {
        String userId = extractUserIdOrNull();
        UserType userType = userId != null ? UserType.AUTH : UserType.ANONYMOUS;

        PackHeadersEvent headers = TicketPackViewPublisher.toHeaders(
                userType,
                userId,
                xAnonymousId,
                xGenerationId,
                xPackRevision,
                xGenerationMode,
                xRequestId,
                xTicketsRevision
        );
        SearchContextEvent searchContext = TicketPackViewPublisher.toSearchContext(
                originCountry,
                originCity,
                destinationCountry,
                destinationCity,
                dateFrom,
                dateTo,
                currency.getValue(),
                adults,
                children,
                budget,
                filters
        );

        ticketPackViewPublisher.publishTicketDetailView(headers, searchContext, response.getTicket());
    }

    private static TicketSearchRequest buildSearchRequest(
            String originCountry,
            String originCity,
            String destinationCountry,
            String destinationCity,
            LocalDate dateFrom,
            LocalDate dateTo,
            Integer adults,
            Long budget,
            Currency currency,
            Integer children,
            List<String> filters
    ) {
        ResolvedTripLocation origin = TripLocations.resolveOrBadRequest(originCountry, originCity);
        ResolvedTripLocation destination = TripLocations.resolveOrBadRequest(destinationCountry, destinationCity);

        return TicketSearchRequest.builder()
                .originCityCode(origin.city().iataCode())
                .destinationCityCode(destination.city().iataCode())
                .departureDate(dateFrom)
                .returnDate(dateTo)
                .budgetMaxAmount(searchBudget(budget))
                .currency(com.tripify.tickets.service.model.unified.Currency.fromCode(currency.getValue()))
                .passengers(Passengers.builder()
                        .adults(adults)
                        .children(children)
                        .build())
                .filters(filters)
                .build();
    }

    private static Long searchBudget(Long budget) {
        if (budget == null) {
            return null;
        }
        return Math.round(budget * SEARCH_BUDGET_RATIO);
    }

    private static String extractUserIdOrNull() {
        String gatewayUserId = requestHeader("X-User-Id");
        if (gatewayUserId != null && !gatewayUserId.isBlank()) {
            log.info("X-User-Id propagated by gateway: {}", gatewayUserId.trim());
            return gatewayUserId.trim();
        }
        log.debug("X-User-Id header is absent; falling back to JWT/anonymous");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof Jwt jwt)) {
            return null;
        }
        return jwt.getSubject();
    }

    private static String requestHeader(String name) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (!(attributes instanceof ServletRequestAttributes servletAttributes)) {
            return null;
        }
        return servletAttributes.getRequest().getHeader(name);
    }
}
