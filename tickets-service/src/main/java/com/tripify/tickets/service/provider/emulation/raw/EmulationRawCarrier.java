package com.tripify.tickets.service.provider.emulation.raw;

import lombok.Builder;

@Builder
public record EmulationRawCarrier(
        String iata,
        String title,
        String logo
) {
}
