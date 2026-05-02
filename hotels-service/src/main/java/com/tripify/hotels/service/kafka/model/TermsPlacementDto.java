package com.tripify.hotels.service.kafka.model;

public record TermsPlacementDto(
        PlacementTimeDto checkIn,
        PlacementTimeDto checkOut,
        Boolean cancellation,
        RefundRuleDto refundRule,
        Boolean smoking,
        Boolean petFriendly,
        Boolean partyFriendly,
        Integer ageRestriction,
        String additionalInfo
) {
}
