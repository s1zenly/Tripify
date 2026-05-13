package com.tripify.users.service.exception.app;

public class InvalidUserRegisteredEventException extends RuntimeException {

    public InvalidUserRegisteredEventException(String message) {
        super(message);
    }
}
