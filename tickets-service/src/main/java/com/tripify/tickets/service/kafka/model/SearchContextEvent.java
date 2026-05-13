package com.tripify.tickets.service.kafka.model;

import com.tripify.tickets.service.domain.ResolvedTripLocation;
import com.tripify.tickets.service.domain.TripLocation;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public record SearchContextEvent(
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

    public static SearchContextEvent of(
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
        ResolvedTripLocation origin = TripLocation.resolve(originCountry, originCity);
        ResolvedTripLocation destination = TripLocation.resolve(destinationCountry, destinationCity);

        return new SearchContextEvent(
                origin.country().alpha2(),
                origin.city().iataCode(),
                destination.country().alpha2(),
                destination.city().iataCode(),
                dateFrom,
                dateTo,
                currency == null ? null : currency.trim().toUpperCase(Locale.ROOT),
                adults,
                children,
                budget,
                filters == null ? List.of() : List.copyOf(filters)
        );
    }
}
