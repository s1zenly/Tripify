package com.tripify.auth.service.repository.contracts;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.tripify.auth.service.domain.model.RefreshToken;

public interface RefreshTokenRepository {

    void save(RefreshToken refreshToken);

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    int revoke(UUID id, Instant now);
}
