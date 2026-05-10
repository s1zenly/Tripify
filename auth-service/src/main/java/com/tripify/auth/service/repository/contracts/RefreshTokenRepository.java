package com.tripify.auth.service.repository.contracts;

import com.tripify.auth.service.domain.model.RefreshToken;

public interface RefreshTokenRepository {

    void save(RefreshToken refreshToken);
}
