package com.tripify.info.controller;

import com.tripify.info.dto.ErrorResponse;
import com.tripify.info.exception.CountryInfoNotFoundException;
import com.tripify.info.exception.InfoErrorCode;
import com.tripify.info.exception.InfoServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CountryInfoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCountryInfoNotFound(CountryInfoNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(exception.getErrorCode().name(), exception.getMessage()));
    }

    @ExceptionHandler(InfoServiceException.class)
    public ResponseEntity<ErrorResponse> handleInfoService(InfoServiceException exception) {
        HttpStatus status = switch (exception.getErrorCode()) {
            case COUNTRY_CONFIG_NOT_FOUND, COUNTRY_INFO_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case INVALID_COUNTRY_CODE, INVALID_STAY_DATES, INVALID_CURRENCY_CODE -> HttpStatus.BAD_REQUEST;
            case TUTU_PAGE_DOWNLOAD_FAILED, TUTU_PAGE_PARSE_FAILED, COUNTRY_INFO_REFRESH_FAILED,
                 WEATHER_FORECAST_FAILED -> HttpStatus.BAD_GATEWAY;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        if (status.is5xxServerError()) {
            log.error("Info service error: {}", exception.getMessage(), exception);
        }

        return ResponseEntity.status(status)
                .body(new ErrorResponse(exception.getErrorCode().name(), exception.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(InfoErrorCode.INVALID_COUNTRY_CODE.name(), exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception exception) {
        log.error("Unhandled exception", exception);
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(InfoErrorCode.INTERNAL_ERROR.name(), "Internal server error"));
    }
}
