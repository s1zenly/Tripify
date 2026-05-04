package com.tripify.hotels.service.kafka.model;

import java.time.LocalDate;
import java.util.List;

public record HotelSearchContextEvent(
        String country,
        String city,
        LocalDate checkIn,
        LocalDate checkOut,
        String currency,
        int guests,
        Long budget,
        List<String> filters
) {
}
