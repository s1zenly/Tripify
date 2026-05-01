package com.tripify.tickets.service.model.search;

import com.tripify.tickets.service.model.unified.Currency;
import com.tripify.tickets.service.model.unified.Passengers;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record TicketSearchRequest(
        @NotBlank String originCityCode,
        @NotBlank String destinationCityCode,
        @NotNull LocalDate departureDate,
        @NotNull LocalDate returnDate,
        Long budgetMaxAmount,
        @NotNull Currency currency,
        @NotNull @Valid Passengers passengers,
        List<String> filters
) {
    public TicketSearchRequest {
        filters = filters == null ? List.of() : List.copyOf(filters);
    }
}
