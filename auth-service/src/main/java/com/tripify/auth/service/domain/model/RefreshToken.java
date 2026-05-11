package com.tripify.auth.service.domain.model;

import java.time.Instant;
import java.util.UUID;

public record RefreshToken(
        UUID id,
        UUID userId,
        String tokenHash,
        boolean revoked,
        Instant revokedAt,
        Instant expiresAt,
        Instant createdAt
) {

    public boolean isActive(Instant now) {
        return !revoked && expiresAt.isAfter(now);
    }
}
