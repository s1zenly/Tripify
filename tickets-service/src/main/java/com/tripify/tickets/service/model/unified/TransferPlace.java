package com.tripify.tickets.service.model.unified;

import lombok.Builder;

@Builder
public record TransferPlace(
        String airportCode,
        String airportName,
        Integer layoverMinutes
) {
}
