package com.tripify.auth.service.Service;

import java.time.Duration;

import com.tripify.auth.service.clients.RedisClient;
import com.tripify.auth.service.exception.RateLimitExceededException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpRateLimiter {

    private static final Duration WINDOW = Duration.ofMinutes(10);
    private static final int MAX_REQUESTS = 5;

    private final RedisClient redisClient;

    public void checkRequestOtpAllowed(String phoneE164) {
        String key = "rate:otp:request:" + phoneE164;

        long attempts = redisClient.incrementWithTtl(key, WINDOW);

        if (attempts > MAX_REQUESTS) {
            long retryAfterSeconds = redisClient.ttl(key);

            throw new RateLimitExceededException(
                    retryAfterSeconds > 0 ? retryAfterSeconds : WINDOW.toSeconds()
            );
        }
    }
}
