package com.tripify.tickets.service.provider.emulation.raw;

import lombok.Builder;

@Builder
public record EmulationRawBaggageItem(
        boolean included,
        Integer count,
        Integer weight
) {
}
