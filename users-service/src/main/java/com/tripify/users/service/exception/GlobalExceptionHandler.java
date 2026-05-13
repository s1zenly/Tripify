package com.tripify.users.service.exception;

import java.time.OffsetDateTime;
import java.util.List;

import com.tripify.users.generated.model.ErrorCode;
import com.tripify.users.generated.model.ErrorDetail;
import com.tripify.users.generated.model.ErrorResponse;
import com.tripify.users.service.client.Messages;
import com.tripify.users.service.exception.client.BadRequestException;
import com.tripify.users.service.exception.client.InternalServerException;
import com.tripify.users.service.exception.client.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserProfileNotFound(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
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
                        exception.getErrorCode(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error(
                        exception.getErrorCode(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedError(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error(
                        ErrorCode.INTERNAL_SERVER_ERROR,
                        Messages.UNEXPECTED_ERROR_MESSAGE
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
