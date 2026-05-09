package com.tripify.auth.service.repository.contracts;

import com.tripify.auth.service.domain.model.OutboxEvent;

public interface OutboxEventRepository {

    void saveEvent(OutboxEvent outboxEvent);
}
