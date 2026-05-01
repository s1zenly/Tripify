package com.tripify.tickets.service.provider.emulation.raw;

import lombok.Builder;

import java.util.List;

@Builder
public record EmulationRawOffer(
        String offerId,
        String proposalId,
        String agencyId,
        String buyUrl,
        EmulationRawPassengers pax,
        EmulationRawPrice price,
        List<EmulationRawLeg> legs,
        EmulationRawBaggage baggage,
        EmulationRawFareRules fareRules,
        EmulationRawCarrier validatingCarrier,
        EmulationRawAvailability availability
) {
}
