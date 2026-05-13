package com.tripify.auth.service.controller;

import java.util.UUID;

import com.tripify.auth.generated.api.TokensApi;
import com.tripify.auth.generated.model.ErrorCode;
import com.tripify.auth.service.Service.CookieService;
import com.tripify.auth.service.domain.model.LogoutRequest;
import com.tripify.auth.service.domain.model.SessionTokens;
import com.tripify.auth.service.exception.UnauthorizedException;
import com.tripify.auth.service.scenario.LogoutScenario;
import com.tripify.auth.service.scenario.RefreshTokenScenario;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TokensController implements TokensApi {

    private final RefreshTokenScenario refreshTokenScenario;
    private final LogoutScenario logoutScenario;
    private final CookieService cookieService;
    private final HttpServletRequest httpServletRequest;

    @Override
    public ResponseEntity<Void> refreshToken() {
        String refreshToken = cookieService.getCookieValue(httpServletRequest, cookieService.refreshTokenCookieName())
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_REFRESH, "Refresh token cookie is missing"));

        SessionTokens sessionTokens = refreshTokenScenario.run(refreshToken);

        return ResponseEntity.noContent()
                .headers(cookieService.createSessionCookieHeaders(sessionTokens))
                .build();
    }

    @Override
    public ResponseEntity<Void> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());

        cookieService.getCookieValue(httpServletRequest, cookieService.refreshTokenCookieName())
                .ifPresent(refreshToken -> logoutScenario.run(new LogoutRequest(refreshToken, userId)));

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .headers(cookieService.createClearCookieHeaders())
                .build();
    }
}
