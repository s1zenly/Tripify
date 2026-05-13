package com.tripify.hotels.service.kafka.model;

import java.math.BigDecimal;

public record HotelScoreBreakdownDto(
        BigDecimal price,
        BigDecimal rating,
        BigDecimal location,
        BigDecimal facilities
) {
}
