package com.tripify.notification.service.service;

import java.time.Instant;

import com.tripify.notification.service.model.DlqMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DlqService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void save(String topic, Object event, Exception ex) {
        DlqMessage message = new DlqMessage(
                event,
                ex.getMessage(),
                Instant.now()
        );

        kafkaTemplate.send(topic, message);
    }
}
