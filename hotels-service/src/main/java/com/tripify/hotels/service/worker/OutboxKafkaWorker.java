package com.tripify.hotels.service.worker;

import java.time.Instant;
import java.util.List;

import com.tripify.hotels.service.config.property.OutboxWorkerProperties;
import com.tripify.hotels.service.model.outbox.OutboxEvent;
import com.tripify.hotels.service.repository.contract.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "tripify.outbox.worker", name = "enabled", havingValue = "true")
public class OutboxKafkaWorker {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OutboxWorkerProperties workerProperties;

    @Transactional
    @Scheduled(fixedDelayString = "${tripify.outbox.worker.fixed-delay-ms}")
    public void publishPendingEvents() {
        List<OutboxEvent> events = outboxEventRepository.findPendingForPublish(
                workerProperties.batchSize(),
                workerProperties.maxAttempts()
        );

        if (events.isEmpty()) {
            return;
        }

        events.forEach(this::publishOne);
    }

    private void publishOne(OutboxEvent event) {
        Instant now = Instant.now();

        try {
            kafkaTemplate
                    .send(event.topic(), event.aggregateId(), event.payload())
                    .get();

            outboxEventRepository.markAsPublished(event.id(), now);

            log.info(
                    "Outbox event published. eventId={}, eventType={}, topic={}, aggregateId={}",
                    event.id(),
                    event.eventType(),
                    event.topic(),
                    event.aggregateId()
            );
        } catch (Exception exception) {
            outboxEventRepository.markAsFailedAttempt(
                    event.id(),
                    exception.getMessage(),
                    now,
                    workerProperties.maxAttempts()
            );

            log.error(
                    "Failed to publish outbox event. eventId={}, eventType={}, topic={}, aggregateId={}",
                    event.id(),
                    event.eventType(),
                    event.topic(),
                    event.aggregateId(),
                    exception
            );
        }
    }
}
