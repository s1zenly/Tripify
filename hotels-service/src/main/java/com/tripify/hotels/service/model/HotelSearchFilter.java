package com.tripify.hotels.service.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.tripify.hotels.service.model.filter.ResolvedSearchFilters;

public record HotelSearchFilter(
        String country,
        String city,
        BigDecimal maxPriceUsd,
        ResolvedSearchFilters resolvedFilters,
        int limit,
        UUID lastId,
        BigDecimal lastPriceUsd
) {
}
