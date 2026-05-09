package com.tripify.auth.service.worker;

import java.time.Instant;
import java.util.List;

import com.tripify.auth.service.domain.model.OutboxEvent;
import com.tripify.auth.service.infra.TimeProvider;
import com.tripify.auth.service.repository.contracts.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "app.outbox.worker",
        name = "enabled",
        havingValue = "true"
)
public class OutboxKafkaWorker {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TimeProvider timeProvider;

    @Value("${app.outbox.worker.batch-size}")
    private int batchSize;

    @Value("${app.outbox.worker.max-attempts}")
    private int maxAttempts;

    @Transactional
    @Scheduled(fixedDelayString = "${app.outbox.worker.fixed-delay-ms}")
    public void publishPendingEvents() {
        List<OutboxEvent> events = outboxEventRepository.findPendingForPublish(batchSize, maxAttempts);

        if (events == null || events.isEmpty()) {
            log.info("Empty outbox events list");
            return;
        }

        events.forEach(this::publishOne);
    }

    private void publishOne(OutboxEvent event) {
        Instant now = timeProvider.nowUtc();
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
            outboxEventRepository.markAsFailedAttempt(event.id(), exception.getMessage(), now, maxAttempts);

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