package com.tripify.auth.service.web;

import java.util.Map;

import com.tripify.auth.service.exception.BadRequestException;
import com.tripify.auth.service.exception.ForbiddenException;
import com.tripify.auth.service.exception.InternalServerException;
import com.tripify.auth.service.utils.Constants;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tripify.auth.service.exception.RateLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<String> handleRateLimit(RateLimitExceededException exception) {
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .header(HttpHeaders.RETRY_AFTER, String.valueOf(exception.getRetryAfterSeconds()))
                .body(Constants.TO_MANY_REQUESTS_MESSAGE);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbidden(ForbiddenException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException exception) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "error", "BAD_REQUEST",
                        "message", exception.getMessage()
                ));
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<Map<String, Object>> handleInternalServer(
            InternalServerException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", "INTERNAL_SERVER_ERROR",
                        "message", exception.getMessage()
                ));
    }
}
