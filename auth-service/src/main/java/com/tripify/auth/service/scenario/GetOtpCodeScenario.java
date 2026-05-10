package com.tripify.auth.service.scenario;

import java.util.Optional;

import com.tripify.auth.generated.model.OtpRequest;
import com.tripify.auth.service.Service.OtpRateLimiter;
import com.tripify.auth.service.Service.OtpService;
import com.tripify.auth.service.Service.PhoneValidationService;
import com.tripify.auth.service.Service.UserService;
import com.tripify.auth.service.domain.enums.OtpPurpose;
import com.tripify.auth.service.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetOtpCodeScenario implements Scenario<OtpRequest, Void> {

    private final UserService userService;
    private final OtpRateLimiter otpRateLimiter;
    private final OtpService otpService;
    private final PhoneValidationService phoneValidationService;

    @Override
    public Void run(OtpRequest otpRequest) {
        String normalizedPhoneNumber = phoneValidationService.validateToE164(otpRequest.getPhoneNumber());
        log.info("Success validate phone number - {}", otpRequest.getPhoneNumber());

        otpRateLimiter.checkRequestOtpAllowed(normalizedPhoneNumber);
        Optional<User> user = userService.checkUserByBlocked(normalizedPhoneNumber);

        otpService.createOtpRequest(normalizedPhoneNumber, defineOtpPurpose(user));
        return null;
    }

    private OtpPurpose defineOtpPurpose(Optional<User> user) {
        return user.isPresent() ? OtpPurpose.LOGIN : OtpPurpose.REGISTER;
    }
}
