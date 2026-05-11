package com.tripify.auth.service.scenario;

import java.time.Instant;

import com.tripify.auth.generated.model.ErrorCode;
import com.tripify.auth.generated.model.RefreshTokenRequest;
import com.tripify.auth.generated.model.TokenResponse;
import com.tripify.auth.service.Service.RefreshTokenService;
import com.tripify.auth.service.Service.SessionService;
import com.tripify.auth.service.Service.UserService;
import com.tripify.auth.service.client.ConverterModelToDTO;
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
public class RefreshTokenScenario implements Scenario<RefreshTokenRequest, TokenResponse> {

    private final Hasher hasher;
    private final TimeProvider timeProvider;
    private final UserService userService;
    private final TransactionTemplate transactionTemplate;
    private final SessionService sessionService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public TokenResponse run(RefreshTokenRequest request) {
        Instant now = timeProvider.nowUtc();
        String oldRawRefreshToken = request.getRefreshToken();
        String tokenHash = hasher.hashSHA256(oldRawRefreshToken);

        RefreshToken oldRefreshToken = refreshTokenService.findByTokenHash(tokenHash);
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
            log.error("Failed execute refresh token transaction for token = {}", oldRawRefreshToken, exception);
            throw new InternalServerException("Failed to create refresh tokens");
        }

        return ConverterModelToDTO.createTokenResponse(sessionTokens);
    }
}
