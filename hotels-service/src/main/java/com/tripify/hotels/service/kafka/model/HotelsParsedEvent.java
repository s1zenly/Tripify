package com.tripify.hotels.service.kafka.model;

import java.time.Instant;
import java.util.List;

public record HotelsParsedEvent(
        List<KafkaHotelDto> hotels,
        CountryInfoDto countryInfo,
        String cityName,
        String providerName,
        Instant parsedAt,
        Instant providedAt,
        Integer totalHotels
) {
}
