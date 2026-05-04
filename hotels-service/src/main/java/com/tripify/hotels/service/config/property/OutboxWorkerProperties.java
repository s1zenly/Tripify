package com.tripify.hotels.service.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tripify.outbox.worker")
public record OutboxWorkerProperties(
        boolean enabled,
        long fixedDelayMs,
        int batchSize,
        int maxAttempts
) {
}
