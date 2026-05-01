package com.tripify.tickets.service.provider.emulation.raw;

import lombok.Builder;

import java.time.Instant;

@Builder
public record EmulationRawAvailability(
        Integer seats,
        Instant seenAt,
        Instant expiresAt
) {
}
