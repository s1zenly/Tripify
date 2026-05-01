package com.tripify.tickets.service.config;

import com.tripify.tickets.service.config.property.KafkaTopicsProperties;
import com.tripify.tickets.service.config.property.RedisOfferStoreProperties;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({KafkaTopicsProperties.class, RedisOfferStoreProperties.class})
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, String> kafkaProducerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> kafkaProducerFactory) {
        return new KafkaTemplate<>(kafkaProducerFactory);
    }

    @Bean
    public NewTopic ticketsSearchCompletedTopic(KafkaTopicsProperties kafkaTopics) {
        return TopicBuilder.name(kafkaTopics.ticketsSearchCompleted())
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic ticketsPackViewedTopic(KafkaTopicsProperties kafkaTopics) {
        return TopicBuilder.name(kafkaTopics.ticketsPackViewed())
                .partitions(3)
                .replicas(1)
                .build();
    }
}
