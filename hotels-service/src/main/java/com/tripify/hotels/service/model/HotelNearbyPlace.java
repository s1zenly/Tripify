package com.tripify.hotels.service.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record HotelNearbyPlace(
        UUID id,
        UUID hotelId,
        String category,
        String title,
        BigDecimal distanceValue,
        String distanceUnit,
        Instant createdAt
) {
}
