package com.tripify.auth.service.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

import com.tripify.auth.service.config.property.CookieProperties;
import com.tripify.auth.service.domain.model.SessionTokens;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieService {

    private static final String ACCESS_TOKEN_PATH = "/";
    private static final String REFRESH_TOKEN_PATH = "/auth/refresh";

    private final CookieProperties cookieProperties;

    public ResponseCookie createAccessCookie(String accessToken) {
        return buildCookie(
                cookieProperties.accessTokenName(),
                accessToken,
                ACCESS_TOKEN_PATH,
                cookieProperties.accessMaxAgeSeconds()
        );
    }

    public ResponseCookie createRefreshCookie(String refreshToken) {
        return buildCookie(
                cookieProperties.refreshTokenName(),
                refreshToken,
                REFRESH_TOKEN_PATH,
                cookieProperties.refreshMaxAgeSeconds()
        );
    }

    public ResponseCookie deleteAccessCookie() {
        return buildCookie(
                cookieProperties.accessTokenName(),
                "",
                ACCESS_TOKEN_PATH,
                0
        );
    }

    public ResponseCookie deleteRefreshCookie() {
        return buildCookie(
                cookieProperties.refreshTokenName(),
                "",
                REFRESH_TOKEN_PATH,
                0
        );
    }

    public HttpHeaders createSessionCookieHeaders(SessionTokens sessionTokens) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, createAccessCookie(sessionTokens.accessToken()).toString());
        headers.add(HttpHeaders.SET_COOKIE, createRefreshCookie(sessionTokens.rawRefreshToken()).toString());
        return headers;
    }

    public HttpHeaders createClearCookieHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, deleteAccessCookie().toString());
        headers.add(HttpHeaders.SET_COOKIE, deleteRefreshCookie().toString());
        return headers;
    }

    public Optional<String> getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .map(Cookie::getValue)
                .filter(value -> !value.isBlank())
                .findFirst();
    }

    public String accessTokenCookieName() {
        return cookieProperties.accessTokenName();
    }

    public String refreshTokenCookieName() {
        return cookieProperties.refreshTokenName();
    }

    private ResponseCookie buildCookie(String name, String value, String path, long maxAgeSeconds) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(cookieProperties.secure())
                .sameSite(cookieProperties.sameSite())
                .path(path)
                .maxAge(Duration.ofSeconds(maxAgeSeconds))
                .build();
    }
}
