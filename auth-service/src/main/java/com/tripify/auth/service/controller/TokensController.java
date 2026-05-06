package com.tripify.auth.service.controller;

import com.tripify.auth.generated.api.TokensApi;
import com.tripify.auth.generated.model.LogoutRequest;
import com.tripify.auth.generated.model.RefreshTokenRequest;
import com.tripify.auth.generated.model.TokenResponse;
import org.springframework.http.ResponseEntity;

public class TokensController implements TokensApi {

    @Override
    public ResponseEntity<TokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> logout(LogoutRequest logoutRequest) {
        return null;
    }
}
