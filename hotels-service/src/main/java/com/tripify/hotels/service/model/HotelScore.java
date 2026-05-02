package com.tripify.hotels.service.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record HotelScore(
        UUID hotelId,
        BigDecimal finalScore,
        BigDecimal priceScore,
        BigDecimal ratingScore,
        BigDecimal locationScore,
        BigDecimal facilitiesScore,
        Instant createdAt,
        Instant updatedAt
) {
}
