package com.tripify.hotels.service.kafka.model;

import java.math.BigDecimal;

public record DiscountDto(
        Integer percent,
        BigDecimal amount
) {
}
