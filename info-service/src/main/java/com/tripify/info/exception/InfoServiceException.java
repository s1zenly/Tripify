package com.tripify.info.exception;

public class InfoServiceException extends RuntimeException {

    private final InfoErrorCode errorCode;

    public InfoServiceException(InfoErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public InfoServiceException(InfoErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public InfoErrorCode getErrorCode() {
        return errorCode;
    }
}
