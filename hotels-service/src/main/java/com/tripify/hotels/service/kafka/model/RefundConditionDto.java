package com.tripify.hotels.service.kafka.model;

public record RefundConditionDto(
        Integer quantityPercent,
        String condition
) {
}
