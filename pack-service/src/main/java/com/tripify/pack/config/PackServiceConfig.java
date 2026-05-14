package com.tripify.pack.config;

import com.tripify.pack.config.property.KafkaTopicsProperties;
import com.tripify.pack.config.property.PackRevisionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableConfigurationProperties({PackRevisionProperties.class, KafkaTopicsProperties.class})
public class PackServiceConfig {
}
