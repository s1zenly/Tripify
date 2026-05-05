package com.tripify.hotels.service.model;

import java.time.Instant;
import java.util.UUID;

public record HotelTermsPlacement(
        UUID hotelId,
        String checkInAfterTime,
        String checkInBeforeTime,
        String checkOutAfterTime,
        String checkOutBeforeTime,
        String timezone,
        Boolean petFriendly,
        Boolean partyFriendly,
        Integer ageRestriction,
        String additionalInfo,
        Instant createdAt,
        Instant updatedAt
) {
}
