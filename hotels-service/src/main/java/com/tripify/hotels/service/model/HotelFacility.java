package com.tripify.hotels.service.model;

import java.time.Instant;
import java.util.UUID;

public record HotelFacility(
        UUID hotelId,
        String facilityType,
        Boolean isFree,
        Instant createdAt
) {
}
