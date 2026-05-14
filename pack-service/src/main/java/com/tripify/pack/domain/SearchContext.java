package com.tripify.pack.domain;

import java.time.LocalDate;
import java.util.List;

public record SearchContext(
        String originCountry,
        String originCity,
        String destinationCountry,
        String destinationCity,
        LocalDate dateFrom,
        LocalDate dateTo,
        String currency,
        int adults,
        Integer children,
        Long budget,
        List<String> filters
) {
}
