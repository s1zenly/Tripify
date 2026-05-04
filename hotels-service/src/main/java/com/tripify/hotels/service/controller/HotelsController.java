package com.tripify.hotels.service.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.generated.api.HotelsApi;
import com.tripify.hotels.generated.model.HotelDetails;
import com.tripify.hotels.generated.model.HotelFiltersResponse;
import com.tripify.hotels.generated.model.HotelsResponse;
import com.tripify.hotels.service.service.HotelFilterQueryService;
import com.tripify.hotels.service.service.HotelPackViewPublisher;
import com.tripify.hotels.service.service.HotelQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            String xAnonymousId,
            String xGenerationId,
            Integer xPackRevision,
            String xGenerationMode,
            String xRequestId,
            String country,
            String city,
            Integer xHotelsRevision
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
        HotelDetails response = hotelQueryService.getHotelById(hotelId, currency);

        hotelPackViewPublisher.publishHotelDetailView(
                HotelPackViewPublisher.toHeaders(
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
}
