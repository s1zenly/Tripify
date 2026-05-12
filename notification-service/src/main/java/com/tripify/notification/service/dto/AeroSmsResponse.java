package com.tripify.notification.service.dto;

import java.util.Map;

public record AeroSmsResponse(

        boolean success,

        String message,

        Map<String, String> data
) {
}