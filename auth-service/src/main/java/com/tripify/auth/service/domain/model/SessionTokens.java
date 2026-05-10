package com.tripify.auth.service.domain.model;

import java.time.Instant;

public record SessionTokens(
        String accessToken,
        String rawRefreshToken,
        Instant expiresAt
) {
}
