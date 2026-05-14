package com.tripify.pack.persistence.mongo.document;

import java.time.LocalDate;
import java.util.List;

public record SearchContextDocument(
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
