package com.tripify.hotels.service.model;

import java.time.Instant;
import java.util.UUID;

public record HotelRefundCondition(
        UUID id,
        UUID hotelId,
        Integer quantityPercent,
        String conditionDescription,
        Instant createdAt
) {
}
