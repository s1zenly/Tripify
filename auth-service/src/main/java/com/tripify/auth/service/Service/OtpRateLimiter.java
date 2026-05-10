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

    private static final Duration WINDOW = Duration.ofMinutes(10);
    private static final int MAX_REQUESTS = 5;

    private final RedisClient redisClient;

    public void checkRequestOtpAllowed(String phoneE164) {
        String key = Constants.PREFIX_RATE_LIMITER_OTP_KEY + phoneE164;

        long attempts = redisClient.incrementWithTtl(key, WINDOW);
        log.info("Phone - {} made {} attempts", phoneE164, attempts);

        if (attempts > MAX_REQUESTS) {
            long retryAfterSeconds = redisClient.ttl(key);

            throw new RateLimitExceededException(
                    retryAfterSeconds > 0 ? retryAfterSeconds : WINDOW.toSeconds(), "OTP requests too many"
            );
        }
    }
}
