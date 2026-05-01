package com.tripify.tickets.service.provider.emulation.raw;

import lombok.Builder;

@Builder
public record EmulationRawFareRules(
        boolean canRefund,
        boolean canExchange,
        String familyName
) {
}
