package com.tripify.hotels.service.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.generated.api.HotelsApi;
import com.tripify.hotels.generated.model.HotelDetails;
import com.tripify.hotels.generated.model.HotelFiltersResponse;
import com.tripify.hotels.generated.model.HotelsResponse;
import com.tripify.hotels.service.kafka.model.UserType;
import com.tripify.hotels.service.service.HotelFilterQueryService;
import com.tripify.hotels.service.service.HotelPackViewPublisher;
import com.tripify.hotels.service.service.HotelQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotelsController implements HotelsApi {

    private final HotelQueryService hotelQueryService;
    private final HotelFilterQueryService hotelFilterQueryService;
    private final HotelPackViewPublisher hotelPackViewPublisher;

    @Override
    public ResponseEntity<HotelsResponse> getHotels(
            String xAnonymousId,
            String xGenerationId,
            Integer xPackRevision,
            String xGenerationMode,
            String xRequestId,
            String country,
            String city,
            LocalDate checkIn,
            LocalDate checkOut,
            String currency,
            Integer guests,
            Integer xHotelsRevision,
            Long budget,
            List<String> filters,
            Integer limit,
            UUID cursor
    ) {
        return ResponseEntity.ok(hotelQueryService.getHotels(
                country,
                city,
                checkIn,
                checkOut,
                currency,
                guests,
                budget,
                limit,
                cursor,
                filters
        ));
    }

    @Override
    public ResponseEntity<HotelFiltersResponse> getHotelFilters(
            String xRequestId,
            String country,
            String city
    ) {
        return ResponseEntity.ok(hotelFilterQueryService.getAvailableFilters(country, city));
    }

    @Override
    public ResponseEntity<HotelDetails> getHotelById(
            String xAnonymousId,
            String xGenerationId,
            Integer xPackRevision,
            String xGenerationMode,
            String xRequestId,
            UUID hotelId,
            String country,
            String city,
            LocalDate checkIn,
            LocalDate checkOut,
            String currency,
            Integer guests,
            Integer xHotelsRevision,
            Long budget,
            List<String> filters
    ) {
        HotelDetails response = hotelQueryService.getHotelById(hotelId, currency, checkIn, checkOut);

        String userId = extractUserIdOrNull();
        UserType userType = userId != null ? UserType.AUTH : UserType.ANONYMOUS;

        hotelPackViewPublisher.publishHotelDetailView(
                HotelPackViewPublisher.toHeaders(
                        userType,
                        userId,
                        xAnonymousId,
                        xGenerationId,
                        xPackRevision,
                        xGenerationMode,
                        xRequestId,
                        xHotelsRevision
                ),
                HotelPackViewPublisher.toSearch(
                        country,
                        city,
                        checkIn,
                        checkOut,
                        currency,
                        guests,
                        budget,
                        filters
                ),
                response
        );

        return ResponseEntity.ok(response);
    }

    private static String extractUserIdOrNull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof Jwt jwt)) {
            return null;
        }
        return jwt.getSubject();
    }
}
