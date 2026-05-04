package com.tripify.hotels.service.repository.contract;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.model.outbox.OutboxEvent;

public interface OutboxEventRepository {

    void saveEvent(OutboxEvent event);

    List<OutboxEvent> findPendingForPublish(int limit, int maxAttempts);

    int markAsPublished(UUID id, Instant publishedAt);

    int markAsFailedAttempt(UUID id, String errorMessage, Instant updatedAt, int maxAttempts);
}
