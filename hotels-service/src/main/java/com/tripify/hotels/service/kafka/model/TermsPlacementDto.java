package com.tripify.hotels.service.kafka.model;

public record TermsPlacementDto(
        PlacementTimeDto checkIn,
        PlacementTimeDto checkOut,
        Boolean petFriendly,
        Boolean partyFriendly,
        Integer ageRestriction,
        String additionalInfo
) {
}
