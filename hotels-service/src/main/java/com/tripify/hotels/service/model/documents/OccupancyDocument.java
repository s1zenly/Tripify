package com.tripify.hotels.service.model.documents;

public record OccupancyDocument(
        Integer minAdults,
        Integer maxAdults,
        Integer maxChildren,
        Integer maxGuests
) {
}
