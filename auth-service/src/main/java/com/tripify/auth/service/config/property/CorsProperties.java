package com.tripify.auth.service.config.property;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security.cors")
public record CorsProperties(
        List<String> allowedOrigins
) {
}
