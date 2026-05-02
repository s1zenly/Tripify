package com.tripify.hotels.service.kafka.model;

import java.math.BigDecimal;

public record GpsCoordinatesDto(
        BigDecimal latitude,
        BigDecimal longitude
) {
}
