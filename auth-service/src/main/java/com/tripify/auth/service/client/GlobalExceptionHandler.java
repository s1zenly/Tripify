package com.tripify.auth.service.client;

import com.tripify.auth.generated.model.ErrorCode;
import com.tripify.auth.generated.model.ErrorDetail;
import com.tripify.auth.generated.model.ErrorResponse;
import com.tripify.auth.service.exception.BadRequestException;
import com.tripify.auth.service.exception.ForbiddenException;
import com.tripify.auth.service.exception.InternalServerException;
import com.tripify.auth.service.exception.NotFoundException;
import com.tripify.auth.service.exception.RateLimitExceededException;
import com.tripify.auth.service.exception.UnauthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleRateLimit(RateLimitExceededException exception) {
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .header(HttpHeaders.RETRY_AFTER, String.valueOf(exception.getRetryAfterSeconds()))
                .body(error(
                        ErrorCode.TOO_MANY_REQUESTS,
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error(
                        exception.getErrorCode(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error(
                        exception.getErrorCode(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleInternalServer(InternalServerException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error(
                        ErrorCode.INTERNAL_SERVER_ERROR,
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .headers(exception.getHttpHeaders())
                .body(error(
                        exception.getErrorCode(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            NotFoundException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error(
                        exception.getErrorCode(),
                        exception.getMessage()
                ));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error(
                        ErrorCode.INTERNAL_SERVER_ERROR,
                        Messages.UNEXPECTED_ERROR_MESSAGE
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        List<ErrorDetail> details = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toErrorDetail)
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error(
                        ErrorCode.VALIDATION_ERROR,
                        Messages.FAILED_VALIDATION_MESSAGE,
                        details
                ));
    }

    private ErrorDetail toErrorDetail(FieldError fieldError) {
        return new ErrorDetail()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage());
    }

    private ErrorResponse error(ErrorCode code, String message) {
        return error(code, message, null);
    }

    private ErrorResponse error(
            ErrorCode code,
            String message,
            List<ErrorDetail> details
    ) {
        return new ErrorResponse()
                .code(code)
                .message(message)
                .details(details)
                .timestamp(OffsetDateTime.now());
    }
}
