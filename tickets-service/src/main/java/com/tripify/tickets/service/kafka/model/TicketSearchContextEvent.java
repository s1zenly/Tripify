package com.tripify.tickets.service.kafka.model;

import java.time.LocalDate;

public record TicketSearchContextEvent(
        String originCityCode,
        String destinationCityCode,
        LocalDate departureDate,
        LocalDate returnDate,
        String currency,
        int adults,
        int children,
        int infants,
        Long budgetMaxAmount
) {
}
