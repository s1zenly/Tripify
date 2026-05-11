package com.tripify.auth.service.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.tripify.auth.service.config.property.JwtProperties;
import com.tripify.auth.service.domain.model.AccessToken;
import com.tripify.auth.service.domain.model.RefreshToken;
import com.tripify.auth.service.domain.model.SessionTokens;
import com.tripify.auth.service.domain.model.User;
import com.tripify.auth.service.helper.Hasher;
import com.tripify.auth.service.helper.RefreshTokenGenerator;
import com.tripify.auth.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final JwtService jwtService;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final JwtProperties jwtProperties;
    private final Hasher hasher;

    public SessionTokens createSession(User user, Instant now) {
        log.info("Start creating session keys for user - {}", user);
        AccessToken accessToken = jwtService.generateAccessToken(user, now);
        String rawRefreshToken = refreshTokenGenerator.generate();
        String refreshTokenHash = hasher.hashSHA256(rawRefreshToken);

        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID(),
                user.id(),
                refreshTokenHash,
                false,
                null,
                now.plus(jwtProperties.refreshTokenTtlDays(), ChronoUnit.DAYS),
                now
        );

        return new SessionTokens(
                accessToken.value(),
                rawRefreshToken,
                refreshToken,
                accessToken.expiresAt()
        );
    }
}
