package com.tripify.notification.service.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tripify.notification.providers.aero-sms")
public record AeroSmsProperties(
        boolean enabled,
        String name,
        String email,
        String apiKey,
        String sign
) {
}
