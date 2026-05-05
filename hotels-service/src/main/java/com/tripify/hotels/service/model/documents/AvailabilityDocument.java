package com.tripify.hotels.service.model.documents;

public record AvailabilityDocument(
        Integer roomsLeft,
        Boolean soldOut
) {
}
