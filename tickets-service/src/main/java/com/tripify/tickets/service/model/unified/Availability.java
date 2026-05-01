package com.tripify.tickets.service.model.unified;

import lombok.Builder;

import java.time.Instant;

@Builder
public record Availability(
        Integer seatsLeft,
        Instant lastSeenAt,
        Instant expiresAt
) {
}
