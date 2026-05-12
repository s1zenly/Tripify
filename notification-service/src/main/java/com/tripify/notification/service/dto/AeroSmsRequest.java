package com.tripify.notification.service.dto;

public record AeroSmsRequest(

        String number,

        String text,

        String sign
) {
}
