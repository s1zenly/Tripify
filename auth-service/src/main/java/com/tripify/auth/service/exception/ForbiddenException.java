package com.tripify.auth.service.exception;

import com.tripify.auth.generated.model.ErrorCode;

public class ForbiddenException extends RuntimeException {

    private final ErrorCode errorCode;

    public ForbiddenException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
