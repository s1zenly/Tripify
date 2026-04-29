package com.tripify.hotels.parser.config;

import java.util.HashMap;
import java.util.Map;

import com.tripify.hotels.parser.dto.HotelsResponseDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

@Configuration
public class KafkaConfig {

    @Value("${app.kafka.topics.hotels-parsed}")
    private String hotelsParsedTopic;

    @Bean
    public ProducerFactory<String, HotelsResponseDto> hotelsProducerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, HotelsResponseDto> hotelsKafkaTemplate(
            ProducerFactory<String, HotelsResponseDto> hotelsProducerFactory
    ) {
        return new KafkaTemplate<>(hotelsProducerFactory);
    }

    @Bean
    public NewTopic hotelsParsedTopic() {
        return TopicBuilder.name(hotelsParsedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
