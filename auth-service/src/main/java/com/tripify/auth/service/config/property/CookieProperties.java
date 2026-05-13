package com.tripify.auth.service.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security.cookies")
public record CookieProperties(
        String accessTokenName,
        String refreshTokenName,
        boolean secure,
        String sameSite,
        long accessMaxAgeSeconds,
        long refreshMaxAgeSeconds
) {
}
