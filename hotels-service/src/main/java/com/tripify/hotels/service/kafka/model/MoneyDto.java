package com.tripify.hotels.service.kafka.model;

import java.math.BigDecimal;

public record MoneyDto(
        BigDecimal amount,
        String currency
) {
}
