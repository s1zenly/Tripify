package com.tripify.auth.service.scenario;

import java.time.Instant;

import com.tripify.auth.generated.model.ErrorCode;
import com.tripify.auth.service.Service.RefreshTokenService;
import com.tripify.auth.service.Service.SessionService;
import com.tripify.auth.service.Service.UserService;
import com.tripify.auth.service.domain.model.RefreshToken;
import com.tripify.auth.service.domain.model.SessionTokens;
import com.tripify.auth.service.domain.model.User;
import com.tripify.auth.service.exception.InternalServerException;
import com.tripify.auth.service.exception.UnauthorizedException;
import com.tripify.auth.service.helper.Hasher;
import com.tripify.auth.service.infra.TimeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenScenario implements Scenario<String, SessionTokens> {

    private final Hasher hasher;
    private final TimeProvider timeProvider;
    private final UserService userService;
    private final TransactionTemplate transactionTemplate;
    private final SessionService sessionService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public SessionTokens run(String rawRefreshToken) {
        if (rawRefreshToken == null || rawRefreshToken.isBlank()) {
            throw new UnauthorizedException(ErrorCode.INVALID_REFRESH, "Refresh token cookie is missing");
        }

        Instant now = timeProvider.nowUtc();
        String tokenHash = hasher.hashSHA256(rawRefreshToken);

        RefreshToken oldRefreshToken = refreshTokenService.findByTokenHash(tokenHash)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_REFRESH, "Invalid refresh token"));

        if (!oldRefreshToken.isActive(now)) {
            throw new UnauthorizedException(ErrorCode.BAD_REFRESH, "Token revoked or expired");
        }

        User user = userService.getActiveUserById(oldRefreshToken.userId());
        SessionTokens sessionTokens = sessionService.createSession(user, now);

        try {
            transactionTemplate.executeWithoutResult(status -> {
                refreshTokenService.revokeRefreshToken(oldRefreshToken.id(), now);
                refreshTokenService.saveRefreshToken(sessionTokens.refreshToken());
            });
        } catch (DataAccessException exception) {
            log.error("Failed execute refresh token transaction for token = {}", rawRefreshToken, exception);
            throw new InternalServerException("Failed to create refresh tokens");
        }

        return sessionTokens;
    }
}
