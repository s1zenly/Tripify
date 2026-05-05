package com.tripify.hotels.service.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tripify.s3")
public record S3Properties(
    String endpoint,
    String region,
    String bucket,
    String roomsBucket,
    String reviewsBucket,
    boolean uploadEnabled,
    String accessKey,
    String secretKey
) {
}