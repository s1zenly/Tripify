package com.tripify.auth.service.exception;

import com.tripify.auth.generated.model.ErrorCode;
import lombok.Getter;

@Getter
public class RateLimitExceededException extends RuntimeException {

    private final ErrorCode errorCode;
    private final long retryAfterSeconds;

    public RateLimitExceededException(long retryAfterSeconds, String message) {
        super(message);
        this.errorCode = ErrorCode.TOO_MANY_REQUESTS;
        this.retryAfterSeconds = retryAfterSeconds;
    }
}