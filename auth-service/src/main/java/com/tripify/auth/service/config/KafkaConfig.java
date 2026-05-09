package com.tripify.auth.service.config;

import com.tripify.auth.service.config.property.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic otpRequestedTopic(KafkaTopics kafkaTopics) {
        return TopicBuilder
                .name(kafkaTopics.otpRequested())
                .partitions(3)
                .replicas(1)
                .build();
    }
}
