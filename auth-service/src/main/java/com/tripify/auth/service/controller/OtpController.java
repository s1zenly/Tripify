package com.tripify.auth.service.controller;

import com.tripify.auth.generated.api.OtpApi;
import com.tripify.auth.generated.model.OtpRequest;
import com.tripify.auth.generated.model.OtpVerifyRequest;
import com.tripify.auth.generated.model.TokenResponse;
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
    private final Scenario<OtpVerifyRequest, TokenResponse> verifyCodeScenario;

    @Override
    public ResponseEntity<Void> requestOtp(OtpRequest otpRequest) {
        getOtpCodeScenario.run(otpRequest);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }



    @Override
    public ResponseEntity<TokenResponse> verifyOtp(OtpVerifyRequest otpVerifyRequest) {
        TokenResponse tokens = verifyCodeScenario.run(otpVerifyRequest);
        return ResponseEntity.ok(tokens);
    }
}
