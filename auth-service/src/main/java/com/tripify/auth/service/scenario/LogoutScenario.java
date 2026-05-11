package com.tripify.auth.service.scenario;

import java.time.Instant;
import java.util.Optional;

import com.tripify.auth.service.Service.RefreshTokenService;
import com.tripify.auth.service.domain.model.LogoutRequest;
import com.tripify.auth.service.domain.model.RefreshToken;
import com.tripify.auth.service.helper.Hasher;
import com.tripify.auth.service.infra.TimeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogoutScenario implements Scenario<LogoutRequest, Void> {

    private final RefreshTokenService refreshTokenService;

    private final TimeProvider timeProvider;
    private final Hasher hasher;

    @Override
    public Void run(LogoutRequest request) {
        Instant now = timeProvider.nowUtc();
        String refreshTokenHash = hasher.hashSHA256(request.refreshToken());

        Optional<RefreshToken> refreshTokenOptional = refreshTokenService.findByTokenHash(refreshTokenHash);
        if (refreshTokenOptional.isEmpty()) {
            return null;
        }

        RefreshToken refreshToken = refreshTokenOptional.get();
        if (!refreshToken.userId().equals(request.userId())) {
            return null;
        }

        refreshTokenService.revokeRefreshToken(refreshToken.id(), now);

        return null;
    }
}
