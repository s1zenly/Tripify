package com.tripify.tickets.service.provider.emulation.raw;

import lombok.Builder;

@Builder
public record EmulationRawPassengers(
        int adt,
        int chd,
        int inf
) {
}
