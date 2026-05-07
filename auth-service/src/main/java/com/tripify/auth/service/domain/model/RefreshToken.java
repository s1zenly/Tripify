package com.tripify.auth.service.domain.model;

import java.time.Instant;
import java.util.UUID;

public record RefreshToken(
        UUID id,
        UUID userId,
        String tokenHash,
        boolean revoked,
        Instant expiresAt,
        Instant createdAt
) {
}
