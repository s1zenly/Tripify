package com.tripify.auth.service.domain.model;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;

@Builder
public record UserRegisteredEvent(
        UUID userId,
        String phoneNumber,
        Instant registeredAt
) {
}
