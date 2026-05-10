package com.tripify.auth.service.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import com.tripify.auth.service.config.property.JwtProperties;
import com.tripify.auth.service.domain.model.AccessToken;
import com.tripify.auth.service.domain.model.User;
import com.tripify.auth.service.infra.TimeProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtProperties jwtProperties;

    public AccessToken generateAccessToken(User user, Instant now) {
        Instant expiresAt = now.plus(jwtProperties.accessTokenTtlMinutes(), ChronoUnit.MINUTES);

        String accessToken = Jwts.builder()
                .issuer(jwtProperties.issuer())
                .subject(user.id().toString())
                .claim("phone", user.phone())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(getSigningKey())
                .compact();

        return new AccessToken(accessToken, expiresAt);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtProperties.secret()
                .getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
