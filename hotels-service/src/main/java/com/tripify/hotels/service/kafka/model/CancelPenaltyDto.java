package com.tripify.hotels.service.kafka.model;

import java.math.BigDecimal;

public record CancelPenaltyDto(
        String type,
        BigDecimal amount,
        Integer percent
) {
}
