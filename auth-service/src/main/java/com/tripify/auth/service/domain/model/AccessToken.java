package com.tripify.auth.service.domain.model;

import java.time.Instant;

public record AccessToken(
        String value,
        Instant expiresAt
) {
}
