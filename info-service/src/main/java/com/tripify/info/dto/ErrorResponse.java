package com.tripify.info.dto;

public record ErrorResponse(
        String error,
        String message
) {
}
