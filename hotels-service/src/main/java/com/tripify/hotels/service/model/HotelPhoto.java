package com.tripify.hotels.service.model;

import java.time.Instant;
import java.util.UUID;

public record HotelPhoto(
        UUID id,
        UUID hotelId,
        String s3Key,
        Integer sortOrder,
        String description,
        Instant createdAt
) {
}
