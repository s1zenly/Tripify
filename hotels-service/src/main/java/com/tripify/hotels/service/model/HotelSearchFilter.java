package com.tripify.hotels.service.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.tripify.hotels.service.model.filter.ResolvedSearchFilters;

public record HotelSearchFilter(
        String country,
        String city,
        BigDecimal maxPricePerNightUsd,
        Integer minGuests,
        long nights,
        ResolvedSearchFilters resolvedFilters,
        int limit,
        UUID lastId
) {
}
