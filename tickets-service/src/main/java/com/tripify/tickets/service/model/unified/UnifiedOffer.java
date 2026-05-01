package com.tripify.tickets.service.model.unified;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record UnifiedOffer(
        String unifiedOfferId,
        ProviderInfo provider,
        String deeplink,
        Passengers passengers,
        Price price,
        List<Journey> journeys,
        Baggage baggage,
        Fare fare,
        Airline validatingAirline,
        Availability availability
) {
}
