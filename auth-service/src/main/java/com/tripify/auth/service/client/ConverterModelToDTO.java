package com.tripify.auth.service.client;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.tripify.auth.generated.model.TokenResponse;
import com.tripify.auth.service.domain.model.SessionTokens;

public class ConverterModelToDTO {

    public static TokenResponse createTokenResponse(SessionTokens sessionTokens) {
        return new TokenResponse(
                Constants.BEARER_TOKEN_TYPE,
                sessionTokens.accessToken(),
                sessionTokens.rawRefreshToken(),
                OffsetDateTime.ofInstant(
                        sessionTokens.expiresAt(),
                        ZoneOffset.UTC
                )
        );
    }
}
