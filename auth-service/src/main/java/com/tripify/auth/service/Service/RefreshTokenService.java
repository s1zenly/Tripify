package com.tripify.auth.service.Service;

import java.time.Instant;
import java.util.UUID;

import com.tripify.auth.generated.model.ErrorCode;
import com.tripify.auth.service.domain.model.RefreshToken;
import com.tripify.auth.service.exception.UnauthorizedException;
import com.tripify.auth.service.repository.contracts.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByTokenHash(String tokenHash) {
        return refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_REFRESH, "Invalid refresh token"));
    }

    public void revokeRefreshToken(UUID refreshTokenId, Instant now) {
        int revokedRows = refreshTokenRepository.revoke(refreshTokenId, now);

        if (revokedRows != 1) {
            throw new UnauthorizedException(ErrorCode.REFRESH_REVOKED, "Refresh token already used");
        }
    }

    public void saveRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }
}
