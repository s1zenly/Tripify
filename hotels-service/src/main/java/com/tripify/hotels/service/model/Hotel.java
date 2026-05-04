package com.tripify.hotels.service.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.tripify.hotels.service.utils.HotelIdGenerator;

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
        BigDecimal latitude,
        BigDecimal longitude,
        Integer reviewsTotal,
        BigDecimal reviewsRating,
        Instant parsedAt,
        Instant providedAt,
        Instant createdAt,
        Instant updatedAt
) {

    public static UUID generateId(Long externalHotelId, String providerName) {
        return HotelIdGenerator.generate(providerName, externalHotelId);
    }
}
