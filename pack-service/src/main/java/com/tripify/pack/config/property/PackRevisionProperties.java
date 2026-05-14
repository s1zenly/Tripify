package com.tripify.pack.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "tripify.pack")
public record PackRevisionProperties(
        Duration waitingTimeout
) {
    public PackRevisionProperties {
        if (waitingTimeout == null) {
            waitingTimeout = Duration.ofMinutes(30);
        }
    }
}
