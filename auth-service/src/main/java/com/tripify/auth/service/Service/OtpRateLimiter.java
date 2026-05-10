package com.tripify.auth.service.Service;

import java.time.Duration;

import com.tripify.auth.service.clients.RedisClient;
import com.tripify.auth.service.exception.RateLimitExceededException;
import com.tripify.auth.service.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpRateLimiter {

    private static final Duration REQUEST_WINDOW = Duration.ofMinutes(5);
    private static final Duration VERIFY_WINDOW = Duration.ofMinutes(2);


    private final RedisClient redisClient;

    public void checkRequestOtpAllowed(String phoneE164) {
        String key = Constants.PREFIX_RATE_LIMITER_OTP_REQUEST_KEY + phoneE164;

        long attempts = redisClient.incrementWithTtl(key, REQUEST_WINDOW);
        log.info("Phone - {} made {} OTP request attempts", phoneE164, attempts);

        if (attempts > Constants.MAX_OTP_REQUESTS) {
            long retryAfterSeconds = redisClient.ttl(key);

            throw new RateLimitExceededException(
                    retryAfterSeconds > 0 ? retryAfterSeconds : REQUEST_WINDOW.toSeconds(), "OTP requests too many"
            );
        }
    }

    public void checkVerifyOtpRequestAllowed(String phoneE164) {
        String key = Constants.PREFIX_RATE_LIMITER_OTP_VERIFY_KEY + phoneE164;

        long attempts = redisClient.incrementWithTtl(key, VERIFY_WINDOW);
        log.info("Phone - {} made {} OTP verify attempts", phoneE164, attempts);

        if (attempts > Constants.MAX_OTP_VERIFY_REQUESTS) {
            long retryAfterSeconds = redisClient.ttl(key);

            throw new RateLimitExceededException(
                    retryAfterSeconds > 0 ? retryAfterSeconds : VERIFY_WINDOW.toSeconds(), "OTP verify attempts too many"
            );
        }
    }
}
