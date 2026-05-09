package com.tripify.auth.service.repository.contracts;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tripify.auth.service.domain.enums.OutboxEventType;
import com.tripify.auth.service.domain.model.OutboxEvent;

public interface OutboxEventRepository {

    void saveEvent(OutboxEvent outboxEvent);

    void markPendingOtpEventsAsSkipped(String phone, OutboxEventType eventType, Instant updatedAt);

    List<OutboxEvent> findPendingForPublish(int limit, int maxAttempts);

    int markAsPublished(UUID id, Instant publishedAt);

    int markAsFailedAttempt(UUID id, String errorMessage, Instant updatedAt, int maxAttempts);
}
