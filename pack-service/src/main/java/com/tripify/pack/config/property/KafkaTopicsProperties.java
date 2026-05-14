package com.tripify.pack.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tripify.kafka.topics")
public record KafkaTopicsProperties(
        String hotelsPackViewed,
        String ticketsPackViewed
) {
}
