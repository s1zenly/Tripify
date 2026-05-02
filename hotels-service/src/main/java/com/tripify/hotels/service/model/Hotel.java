package com.tripify.hotels.service.model;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

public record Hotel(
        UUID id,
        Long externalHotelId,
        String providerName,
        String title,
        String externalLink,
        String description,
        String address,
        String city,
        String country,
        String currency,
        BigDecimal price,
        Integer hotelClass,
        String reviewsMongoId,
        BigDecimal latitude,
        BigDecimal longitude,
        Integer reviewsTotal,
        BigDecimal reviewsRating,
        Instant parsedAt,
        Instant providedAt,
        Instant createdAt,
        Instant updatedAt
) {

    public static UUID buildHotelId(Long externalHotelId, String providerName) {
        String source = externalHotelId + ":" + providerName.toLowerCase();

        return UUID.nameUUIDFromBytes(
                source.getBytes(StandardCharsets.UTF_8)
        );
    }
}
