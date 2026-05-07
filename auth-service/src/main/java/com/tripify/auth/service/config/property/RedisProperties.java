package com.tripify.auth.service.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redis")
public record RedisProperties(
        String host,
        int port,
        String password,
        int timeoutMs,
        int maxTotal,
        int maxIdle,
        int minIdle
) {
}
