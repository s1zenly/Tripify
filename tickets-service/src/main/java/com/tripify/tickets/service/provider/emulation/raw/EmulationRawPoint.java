package com.tripify.tickets.service.provider.emulation.raw;

import lombok.Builder;

@Builder
public record EmulationRawPoint(
        String cityIata,
        String cityTitle,
        String airportIata,
        String airportTitle,
        String terminal
) {
}
