package com.tripify.auth.service.controller;

import com.tripify.auth.generated.api.TokensApi;
import com.tripify.auth.generated.model.LogoutRequest;
import com.tripify.auth.generated.model.RefreshTokenRequest;
import com.tripify.auth.generated.model.TokenResponse;
import com.tripify.auth.service.Service.RefreshTokenService;
import com.tripify.auth.service.scenario.RefreshTokenScenario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TokensController implements TokensApi {

    private final RefreshTokenScenario refreshTokenScenario;

    @Override
    public ResponseEntity<TokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        TokenResponse tokens = refreshTokenScenario.run(refreshTokenRequest);

        return ResponseEntity.ok(tokens);
    }

    @Override
    public ResponseEntity<Void> logout(LogoutRequest logoutRequest) {
        return null;
    }
}
