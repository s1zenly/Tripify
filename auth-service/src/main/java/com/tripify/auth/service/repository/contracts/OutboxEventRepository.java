package com.tripify.auth.service.repository.contracts;

import java.time.Instant;

import com.tripify.auth.service.domain.enums.OutboxEventType;
import com.tripify.auth.service.domain.model.OutboxEvent;

public interface OutboxEventRepository {

    void saveEvent(OutboxEvent outboxEvent);

    int markPendingOtpEventsAsSkipped(String phone, OutboxEventType eventType, Instant updatedAt);
}
