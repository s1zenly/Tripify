package com.tripify.hotels.service.kafka.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record KafkaHotelDto(
        Long hid,
        String title,
        String link,
        String description,
        String address,
        String city,
        String country,
        String currency,
        BigDecimal price,
        Integer hotelClass,
        GpsCoordinatesDto gpsCoordinates,
        Map<String, List<NearbyPlaceDto>> nearbyPlaces,
        ReviewsDto reviews,
        TermsPlacementDto termsPlacement,
        PaymentMethodsDto paymentMethods,
        List<FacilityDto> facilities
) {
}
