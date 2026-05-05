package com.tripify.hotels.service.kafka.model;

public record RateAvailabilityDto(
        Integer roomsLeft,
        Boolean soldOut
) {
}
