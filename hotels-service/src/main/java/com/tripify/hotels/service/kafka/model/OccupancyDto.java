package com.tripify.hotels.service.kafka.model;

public record OccupancyDto(
        Integer minAdults,
        Integer maxAdults,
        Integer maxChildren,
        Integer maxGuests
) {
}
