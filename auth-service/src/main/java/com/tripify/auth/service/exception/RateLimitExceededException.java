package com.tripify.auth.service.exception;

import com.tripify.auth.service.utils.Constants;
import lombok.Getter;

public class RateLimitExceededException extends RuntimeException {

    @Getter
    private final long retryAfterSeconds;

    public RateLimitExceededException(long retryAfterSeconds) {
        super(Constants.TO_MANY_REQUESTS_MESSAGE);
        this.retryAfterSeconds = retryAfterSeconds;
    }
}
