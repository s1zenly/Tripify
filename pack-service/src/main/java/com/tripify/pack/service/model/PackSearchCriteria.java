package com.tripify.pack.service.model;

import java.time.LocalDate;

public record PackSearchCriteria(
        String originCountry,
        String originCity,
        String destinationCountry,
        String destinationCity,
        LocalDate dateFrom,
        LocalDate dateTo,
        int adults,
        Integer children,
        Long budget
) {
}
