package com.tripify.hotels.parser.service;

import java.util.UUID;

import com.tripify.hotels.parser.dto.HotelsResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelsKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(HotelsKafkaProducer.class);

    private final NewTopic hotelsParsedTopic;
    private final KafkaTemplate<String, HotelsResponseDto> kafkaTemplate;

    public void sendHotels(HotelsResponseDto hotelDto) {
        String key = UUID.randomUUID().toString();

        kafkaTemplate.send(hotelsParsedTopic.name(), key, hotelDto)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        logger.error("Failed to send hotel to Kafka: topic={}, key={}", hotelsParsedTopic.name(), key, exception);
                        return;
                    }
                    logger.info(
                            "Hotel sent to Kafka: topic={}, key={}, partition={}, offset={}",
                            result.getRecordMetadata().topic(),
                            key,
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset()
                    );
                });
    }
}
