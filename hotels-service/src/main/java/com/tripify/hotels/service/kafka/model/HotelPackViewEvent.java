package com.tripify.hotels.service.kafka.model;

import java.time.Instant;

import com.tripify.hotels.generated.model.HotelCard;
import com.tripify.hotels.generated.model.HotelDetails;

public record HotelPackViewEvent(
        Instant occurredAt,
        PackHeadersEvent headers,
        HotelSearchContextEvent search,
        HotelCard hotelCard,
        HotelDetails hotelDetails
) {
}
