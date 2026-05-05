package com.tripify.hotels.service.kafka.model;

import java.math.BigDecimal;

public record RoomAreaDto(
        BigDecimal value,
        String unit
) {
}
