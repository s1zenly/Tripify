package com.tripify.auth.service.domain.model;

import java.util.UUID;

public record LogoutRequest(
        String refreshToken,
        UUID userId
) {
}
