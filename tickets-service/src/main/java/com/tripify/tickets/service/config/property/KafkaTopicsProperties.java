package com.tripify.tickets.service.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tripify.kafka.topics")
public record KafkaTopicsProperties(
        String ticketsSearchCompleted,
        String ticketsPackViewed
) {
}
