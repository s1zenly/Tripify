package com.tripify.auth.service.controller;

import java.util.UUID;

import com.tripify.auth.generated.api.TokensApi;
import com.tripify.auth.generated.model.LogoutRequest;
import com.tripify.auth.generated.model.RefreshTokenRequest;
import com.tripify.auth.generated.model.TokenResponse;
import com.tripify.auth.service.client.ConverterDTOtoModel;
import com.tripify.auth.service.scenario.LogoutScenario;
import com.tripify.auth.service.scenario.RefreshTokenScenario;
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

    @Override
    public ResponseEntity<TokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        TokenResponse tokens = refreshTokenScenario.run(refreshTokenRequest);

        return ResponseEntity.ok(tokens);
    }

    @Override
    public ResponseEntity<Void> logout(LogoutRequest logoutRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());

        logoutScenario.run(ConverterDTOtoModel.createLogoutRequest(logoutRequest, userId));

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
