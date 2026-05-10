package com.tripify.auth.service.scenario;

import java.time.Instant;
import java.util.Map;

import com.tripify.auth.generated.model.ErrorCode;
import com.tripify.auth.generated.model.OtpVerifyRequest;
import com.tripify.auth.generated.model.TokenResponse;
import com.tripify.auth.service.Service.OtpRateLimiter;
import com.tripify.auth.service.Service.PhoneValidationService;
import com.tripify.auth.service.Service.SessionService;
import com.tripify.auth.service.Service.UserService;
import com.tripify.auth.service.client.ConverterModelToDTO;
import com.tripify.auth.service.domain.enums.OtpStatus;
import com.tripify.auth.service.domain.model.OtpRequest;
import com.tripify.auth.service.domain.model.SessionTokens;
import com.tripify.auth.service.domain.model.User;
import com.tripify.auth.service.exception.BadRequestException;
import com.tripify.auth.service.exception.InternalServerException;
import com.tripify.auth.service.exception.UnauthorizedException;
import com.tripify.auth.service.infra.TimeProvider;
import com.tripify.auth.service.helper.Hasher;
import com.tripify.auth.service.repository.contracts.OtpRequestRepository;
import com.tripify.auth.service.utils.Constants;
import com.tripify.auth.service.utils.Headers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.MultiValueMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class VerifyCodeScenario implements Scenario<OtpVerifyRequest, TokenResponse> {

    private final OtpRequestRepository otpRequestRepository;

    private final UserService userService;
    private final SessionService sessionService;
    private final PhoneValidationService phoneValidationService;
    private final TransactionTemplate transactionTemplate;
    private final OtpRateLimiter otpRateLimiter;
    private final TimeProvider timeProvider;
    private final Hasher hasher;

    @Override
    public TokenResponse run(OtpVerifyRequest request) {
        Instant now = timeProvider.nowUtc();
        String normalizedPhone = phoneValidationService.validateToE164(request.getPhoneNumber());
        log.info("Success validate phone number - {}", request.getPhoneNumber());

        otpRateLimiter.checkVerifyOtpRequestAllowed(normalizedPhone);

        OtpRequest otpRequest = otpRequestRepository.findLatestPendingByPhone(normalizedPhone)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.OTP_NOT_FOUND, "Invalid OTP code"));
        log.info("OtpRequest - {}", otpRequest);

        if (!otpRequest.expiresAt().isAfter(now)) {
            log.error("EXPIRED");
            otpRequestRepository.changeOtpStatus(otpRequest.id(), OtpStatus.EXPIRED, now);
            throw new BadRequestException(ErrorCode.OTP_EXPIRED, "OTP code has expired");
        }

        log.info("Start check matches otp codes");
        boolean codeMatches = hasher.matchesBCrypt(request.getCode(), otpRequest.otpHash());

        if (!codeMatches) {
            failedOtpCompare(otpRequest, now);
        }

        try {
            SessionTokens sessionTokens = transactionTemplate.execute(__ -> {
                otpRequestRepository.changeOtpStatus(otpRequest.id(), OtpStatus.VERIFIED, now);
                User user = userService.createOrActivateUser(otpRequest.phone(), now);

                return sessionService.createSession(user, now);
            });

            return ConverterModelToDTO.createTokenResponse(sessionTokens);
        } catch (DataAccessException exception) {
            log.error("Failed execute session token transaction for phone = {}", normalizedPhone, exception);
            throw new InternalServerException("Failed to create session tokens request");
        }
    }

    private void failedOtpCompare(OtpRequest otpRequest, Instant now) {
        int attempts = otpRequestRepository.incrementAttempts(otpRequest.id(), now);
        int remainingAttempts = Math.max(Constants.MAX_OTP_VERIFY_ATTEMPTS - attempts, 0);

        if (attempts >= Constants.MAX_OTP_VERIFY_ATTEMPTS) {
            otpRequestRepository.changeOtpStatus(otpRequest.id(), OtpStatus.FAILED, now);
            throw new UnauthorizedException(ErrorCode.OTP_FAILED, "The code is blocked due to a large number of incorrect attempts");
        }

        throw new UnauthorizedException(
                ErrorCode.OTP_INVALID,
                new HttpHeaders(MultiValueMap.fromSingleValue(
                        Map.of(Headers.REMAINING_ATTEMPTS_HEADER, String.valueOf(remainingAttempts))
                )),
                "Invalid OTP"
        );
    }
}
