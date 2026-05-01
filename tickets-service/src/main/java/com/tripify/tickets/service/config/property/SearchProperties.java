package com.tripify.tickets.service.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "tripify.search")
public record SearchProperties(
        Duration providerTimeout
) {
    public SearchProperties {
        if (providerTimeout == null || providerTimeout.isZero() || providerTimeout.isNegative()) {
            providerTimeout = Duration.ofSeconds(15);
        }
    }
}
