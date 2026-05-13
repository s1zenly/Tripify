package com.tripify.auth.service.controller;

import com.tripify.auth.generated.api.OtpApi;
import com.tripify.auth.generated.model.OtpRequest;
import com.tripify.auth.generated.model.OtpVerifyRequest;
import com.tripify.auth.service.Service.CookieService;
import com.tripify.auth.service.domain.model.SessionTokens;
import com.tripify.auth.service.scenario.Scenario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OtpController implements OtpApi {

    private final Scenario<OtpRequest, Void> getOtpCodeScenario;
    private final Scenario<OtpVerifyRequest, SessionTokens> verifyCodeScenario;
    private final CookieService cookieService;

    @Override
    public ResponseEntity<Void> requestOtp(OtpRequest otpRequest) {
        getOtpCodeScenario.run(otpRequest);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public ResponseEntity<Void> verifyOtp(OtpVerifyRequest otpVerifyRequest) {
        SessionTokens sessionTokens = verifyCodeScenario.run(otpVerifyRequest);

        return ResponseEntity.noContent()
                .headers(cookieService.createSessionCookieHeaders(sessionTokens))
                .build();
    }
}
