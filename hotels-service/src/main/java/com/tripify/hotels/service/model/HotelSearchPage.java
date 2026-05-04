package com.tripify.hotels.service.model;

import java.util.List;
import java.util.UUID;

public record HotelSearchPage(
        List<Hotel> hotels,
        UUID nextCursor
) {
}
