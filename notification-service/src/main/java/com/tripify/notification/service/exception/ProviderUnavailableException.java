package com.tripify.notification.service.exception;

public class ProviderUnavailableException extends RuntimeException {
    public ProviderUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProviderUnavailableException(String message) {
        super(message);
    }
}
