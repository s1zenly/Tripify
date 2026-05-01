package com.tripify.tickets.service.provider.emulation.raw;

import lombok.Builder;

@Builder
public record EmulationRawPrice(
        long value,
        String currency,
        Long originalValue,
        String originalCurrency
) {
}
