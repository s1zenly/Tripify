package com.tripify.users.service.exception.client;

import com.tripify.users.generated.model.ErrorCode;
import lombok.Getter;

public class InternalServerException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    public InternalServerException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
