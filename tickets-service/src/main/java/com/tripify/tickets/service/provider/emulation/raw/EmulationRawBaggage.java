package com.tripify.tickets.service.provider.emulation.raw;

import lombok.Builder;

@Builder
public record EmulationRawBaggage(
        EmulationRawBaggageItem checkedBag,
        EmulationRawBaggageItem carryOn
) {
}
