package com.tripify.hotels.service.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
public record HotelReviewsSummary(
        UUID hotelId,
        BigDecimal rating,
        Integer reviewsTotal,
        BigDecimal cleanliness,
        BigDecimal service,
        BigDecimal priceQuality,
        BigDecimal room,
        BigDecimal location,
        Instant createdAt,
        Instant updatedAt
) {
}
