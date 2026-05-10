package com.tripify.auth.service.exception;

import com.tripify.auth.generated.model.ErrorCode;

public class NotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public NotFoundException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}