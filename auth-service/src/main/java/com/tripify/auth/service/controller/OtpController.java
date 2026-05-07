package com.tripify.auth.service.controller;

import com.tripify.auth.generated.api.OtpApi;
import com.tripify.auth.generated.model.OtpRequest;
import com.tripify.auth.generated.model.OtpVerifyRequest;
import com.tripify.auth.generated.model.TokenResponse;
import com.tripify.auth.service.Service.OtpRateLimiter;
import com.tripify.auth.service.Service.OtpService;
import com.tripify.auth.service.Service.PhoneValidationService;
import com.tripify.auth.service.Service.UserService;
import com.tripify.auth.service.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OtpController implements OtpApi {

    private final UserService userService;
    private final OtpRateLimiter otpRateLimiter;
    private final PhoneValidationService phoneValidationService;

    @Override
    public ResponseEntity<Void> requestOtp(OtpRequest otpRequest) {
        String normalizedPhoneNumber = phoneValidationService.normalizeToE164(
                otpRequest.getCountryCode(),
                otpRequest.getPhoneNumber()
        );

        otpRateLimiter.checkRequestOtpAllowed(normalizedPhoneNumber);
        User user = userService.getOrRestore(normalizedPhoneNumber);

        //TODO: исправить
        return null;
    }

    @Override
    public ResponseEntity<TokenResponse> verifyOtp(OtpVerifyRequest otpVerifyRequest) {
        return null;
    }
}
