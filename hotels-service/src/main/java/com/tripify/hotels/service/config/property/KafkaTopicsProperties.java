package com.tripify.hotels.service.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tripify.kafka.topics")
public record KafkaTopicsProperties(
        String hotelsParsed,
        String hotelsPackViewed
) {
}
