package com.tripify.auth.service.controller;

import java.util.Optional;

import com.tripify.auth.generated.api.OtpApi;
import com.tripify.auth.generated.model.OtpRequest;
import com.tripify.auth.generated.model.OtpVerifyRequest;
import com.tripify.auth.generated.model.TokenResponse;
import com.tripify.auth.service.Service.OtpRateLimiter;
import com.tripify.auth.service.Service.OtpService;
import com.tripify.auth.service.Service.PhoneValidationService;
import com.tripify.auth.service.Service.UserService;
import com.tripify.auth.service.domain.enums.OtpPurpose;
import com.tripify.auth.service.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OtpController implements OtpApi {

    private final UserService userService;
    private final OtpRateLimiter otpRateLimiter;
    private final OtpService otpService;
    private final PhoneValidationService phoneValidationService;

    @Override
    public ResponseEntity<Void> requestOtp(OtpRequest otpRequest) {
        String normalizedPhoneNumber = phoneValidationService.validateToE164(otpRequest.getPhoneNumber());
        log.info("Success validate phone number - {}", otpRequest.getPhoneNumber());

        otpRateLimiter.checkRequestOtpAllowed(normalizedPhoneNumber);
        Optional<User> user = userService.checkUserByBlocked(normalizedPhoneNumber);


        otpService.create(normalizedPhoneNumber, defineOtpPurpose(user));

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    private OtpPurpose defineOtpPurpose(Optional<User> user) {
        return user.isPresent() ? OtpPurpose.LOGIN : OtpPurpose.REGISTER;
    }

    @Override
    public ResponseEntity<TokenResponse> verifyOtp(OtpVerifyRequest otpVerifyRequest) {
        return null;
    }
}
