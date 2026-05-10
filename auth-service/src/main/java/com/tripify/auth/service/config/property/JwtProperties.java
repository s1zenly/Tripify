package com.tripify.auth.service.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.jwt")
public record JwtProperties(
        String issuer,
        long accessTokenTtlMinutes,
        long refreshTokenTtlDays,
        String secret
) {

}
