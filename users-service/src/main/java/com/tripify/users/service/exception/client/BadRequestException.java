package com.tripify.users.service.exception.client;

import com.tripify.users.generated.model.ErrorCode;
import lombok.Getter;

public class BadRequestException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    public BadRequestException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
