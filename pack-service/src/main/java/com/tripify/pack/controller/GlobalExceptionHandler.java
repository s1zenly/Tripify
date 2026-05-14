package com.tripify.pack.controller;

import com.tripify.pack.generated.model.ErrorResponse;
import com.tripify.pack.generated.model.PackErrorCode;
import com.tripify.pack.service.exception.PackNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PackNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePackNotFound(PackNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error(PackErrorCode.PACK_NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error(PackErrorCode.VALIDATION_ERROR, exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error(PackErrorCode.INTERNAL_ERROR, "Internal server error"));
    }

    private static ErrorResponse error(PackErrorCode code, String message) {
        return new ErrorResponse()
                .code(code)
                .message(message)
                .timestamp(OffsetDateTime.now(ZoneOffset.UTC));
    }
}
