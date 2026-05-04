package com.tripify.hotels.service.controller;

import java.time.OffsetDateTime;

import com.tripify.hotels.generated.model.ErrorCode;
import com.tripify.hotels.generated.model.ErrorResponse;
import com.tripify.hotels.service.exception.BadRequestException;
import com.tripify.hotels.service.exception.HotelNotFoundException;
import com.tripify.hotels.service.exception.HotelPackViewPublishException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException exception) {
        return ResponseEntity.badRequest().body(errorResponse(ErrorCode.VALIDATION_ERROR, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Validation failed");

        return ResponseEntity.badRequest().body(errorResponse(ErrorCode.VALIDATION_ERROR, message));
    }

    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(HotelNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse(ErrorCode.HOTEL_NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(HotelPackViewPublishException.class)
    public ResponseEntity<ErrorResponse> handlePackViewPublish(HotelPackViewPublishException exception) {
        log.error("Failed to enqueue hotel pack view event", exception);
        return ResponseEntity.internalServerError()
                .body(errorResponse(ErrorCode.INTERNAL_ERROR, "Failed to record pack view event"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternal(Exception exception) {
        log.error("Unhandled exception", exception);
        return ResponseEntity.internalServerError()
                .body(errorResponse(ErrorCode.INTERNAL_ERROR, "Internal server error"));
    }

    private static ErrorResponse errorResponse(ErrorCode code, String message) {
        return new ErrorResponse()
                .code(code)
                .message(message)
                .timestamp(OffsetDateTime.now());
    }
}
