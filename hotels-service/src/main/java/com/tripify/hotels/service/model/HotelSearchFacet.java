package com.tripify.hotels.service.model;

import java.time.Instant;
import java.util.UUID;

public record HotelSearchFacet(
        UUID hotelId,
        String facet,
        Instant createdAt
) {
}
