package com.tripify.tickets.service.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "tripify.redis.offers")
public record RedisOfferStoreProperties(
        String keyPrefix,
        Duration defaultTtl
) {
    public RedisOfferStoreProperties {
        if (keyPrefix == null || keyPrefix.isBlank()) {
            keyPrefix = "ticket:offer:";
        }
        if (defaultTtl == null || defaultTtl.isZero() || defaultTtl.isNegative()) {
            defaultTtl = Duration.ofMinutes(30);
        }
    }
}
