package com.tripify.hotels.service.kafka.model;

import java.math.BigDecimal;

public record NearbyPlaceDto(
        String title,
        BigDecimal distance,
        String unit
) {
}
