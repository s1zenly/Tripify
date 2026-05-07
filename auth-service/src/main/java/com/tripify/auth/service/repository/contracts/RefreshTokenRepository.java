package com.tripify.auth.service.repository.contracts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tripify.auth.service.domain.model.RefreshToken;

public interface RefreshTokenRepository {

    void save(RefreshToken refreshToken);

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    List<RefreshToken> findActiveByUserId(UUID userId);

    void revoke(UUID id);

    void revokeAllByUserId(UUID userId);
}
