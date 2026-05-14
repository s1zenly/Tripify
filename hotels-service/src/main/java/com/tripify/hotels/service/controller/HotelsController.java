package com.tripify.hotels.service.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.generated.api.HotelsApi;
import com.tripify.hotels.generated.model.HotelDetails;
import com.tripify.hotels.generated.model.HotelFiltersResponse;
import com.tripify.hotels.generated.model.HotelsResponse;
import com.tripify.hotels.service.domain.ResolvedTripLocation;
import com.tripify.hotels.service.domain.TripLocations;
import com.tripify.hotels.service.kafka.model.GenerationMode;
import com.tripify.hotels.service.kafka.model.PackHeadersEvent;
import com.tripify.hotels.service.kafka.model.SearchContextEvent;
import com.tripify.hotels.service.kafka.model.UserType;
import com.tripify.hotels.service.service.HotelFilterQueryService;
import com.tripify.hotels.service.service.HotelPackViewPublisher;
import com.tripify.hotels.service.service.HotelQueryService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
public class HotelsController implements HotelsApi {

    private static final double SEARCH_BUDGET_RATIO = 0.60;

    private final HotelQueryService hotelQueryService;
    private final HotelFilterQueryService hotelFilterQueryService;
    private final HotelPackViewPublisher hotelPackViewPublisher;

    @Override
    public ResponseEntity<HotelsResponse> getHotels(
            String xAnonymousId,
            String xGenerationId,
            String xRequestId,
            String originCountry,
            String originCity,
            String destinationCountry,
            String destinationCity,
            LocalDate dateFrom,
            LocalDate dateTo,
            String currency,
            Integer adults,
            Integer children,
            @Nullable Long budget,
            @Nullable List<String> filters,
            Integer limit,
            UUID cursor
    ) {
        TripLocations.resolveOrBadRequest(originCountry, originCity);
        ResolvedTripLocation destination = TripLocations.resolveOrBadRequest(destinationCountry, destinationCity);

        return ResponseEntity.ok(hotelQueryService.getHotels(
                destination.country().alpha2(),
                destination.city().iataCode(),
                dateFrom,
                dateTo,
                currency,
                adults,
                children,
                searchBudget(budget),
                limit,
                cursor,
                filters
        ));
    }

    @Override
    public ResponseEntity<HotelDetails> searchHotel(
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
            String currency,
            Integer adults,
            @Nullable Integer xHotelsRevision,
            Integer children,
            @Nullable Long budget,
            @Nullable List<String> filters
    ) {
        TripLocations.resolveOrBadRequest(originCountry, originCity);
        ResolvedTripLocation destination = TripLocations.resolveOrBadRequest(destinationCountry, destinationCity);

        HotelDetails response = hotelQueryService.searchFirstHotelDetail(
                destination.country().alpha2(),
                destination.city().iataCode(),
                dateFrom,
                dateTo,
                currency,
                adults,
                children,
                searchBudget(budget),
                filters
        );

        publishHotelPackView(
                xAnonymousId,
                xGenerationId,
                xPackRevision,
                xGenerationMode,
                xRequestId,
                xHotelsRevision,
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
    public ResponseEntity<HotelFiltersResponse> getHotelFilters() {
        return ResponseEntity.ok(hotelFilterQueryService.getFiltersCatalog());
    }

    @Override
    public ResponseEntity<HotelDetails> getHotelById(
            String xAnonymousId,
            String xGenerationId,
            Integer xPackRevision,
            GenerationMode xGenerationMode,
            String xRequestId,
            UUID hotelId,
            String originCountry,
            String originCity,
            String destinationCountry,
            String destinationCity,
            LocalDate dateFrom,
            LocalDate dateTo,
            String currency,
            Integer adults,
            @Nullable Integer xHotelsRevision,
            Integer children,
            @Nullable Long budget,
            @Nullable List<String> filters
    ) {
        TripLocations.resolveOrBadRequest(originCountry, originCity);
        TripLocations.resolveOrBadRequest(destinationCountry, destinationCity);

        HotelDetails response = hotelQueryService.getHotelById(hotelId, currency, dateFrom, dateTo);

        publishHotelPackView(
                xAnonymousId,
                xGenerationId,
                xPackRevision,
                xGenerationMode,
                xRequestId,
                xHotelsRevision,
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

    private void publishHotelPackView(
            String xAnonymousId,
            String xGenerationId,
            Integer xPackRevision,
            GenerationMode xGenerationMode,
            String xRequestId,
            Integer xHotelsRevision,
            String originCountry,
            String originCity,
            String destinationCountry,
            String destinationCity,
            LocalDate dateFrom,
            LocalDate dateTo,
            String currency,
            Integer adults,
            Integer children,
            Long budget,
            List<String> filters,
            HotelDetails response
    ) {
        String userId = extractUserIdOrNull();
        UserType userType = userId != null ? UserType.AUTH : UserType.ANONYMOUS;

        PackHeadersEvent headers = HotelPackViewPublisher.toHeaders(
                userType,
                userId,
                xAnonymousId,
                xGenerationId,
                xPackRevision,
                xGenerationMode,
                xRequestId,
                xHotelsRevision
        );
        SearchContextEvent searchContext = HotelPackViewPublisher.toSearchContext(
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
                filters
        );

        hotelPackViewPublisher.publishHotelDetailView(headers, searchContext, response);
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
