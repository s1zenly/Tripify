package com.tripify.hotels.service.kafka.model;

public record PlacementTimeDto(
        String afterTime,
        String beforeTime,
        String timezone
) {
}