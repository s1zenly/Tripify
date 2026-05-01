package com.tripify.tickets.service.model.unified;

import lombok.Builder;

@Builder
public record Baggage(
        BaggageAllowance checked,
        BaggageAllowance handLuggage
) {
}
