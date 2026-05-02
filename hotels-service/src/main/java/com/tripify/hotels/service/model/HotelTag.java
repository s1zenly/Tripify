package com.tripify.hotels.service.model;

import java.time.Instant;
import java.util.UUID;

public record HotelTag(
        UUID hotelId,
        String tag,
        Instant createdAt
) {
}
