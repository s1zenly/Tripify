package com.tripify.auth.service.repository;

import com.tripify.auth.service.domain.model.OutboxEvent;
import com.tripify.auth.service.repository.contracts.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OutboxEventRepositoryImpl implements OutboxEventRepository {

    private static final String SAVE_OUTBOX_EVENT_QUERY =
            """
            insert into outbox_events(id, aggregate_type, aggregate_id, event_type, topic, payload, status,
                                       attempts, error_message, created_at, updated_at, published_at)
            values(:id, :aggregateType, :aggregateId, :eventType, :topic, cast(:payload as jsonb), :status,
                    :attempts,:errorMessage,:createdAt,:updatedAt,:publishedAt)
            """;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void saveEvent(OutboxEvent event) {
        namedParameterJdbcTemplate.update(
                SAVE_OUTBOX_EVENT_QUERY,
                new MapSqlParameterSource()
                        .addValue("id", event.id())
                        .addValue("aggregateType", event.aggregateType())
                        .addValue("aggregateId", event.aggregateId())
                        .addValue("eventType", event.eventType())
                        .addValue("topic", event.topic())
                        .addValue("payload", event.payload())
                        .addValue("status", event.status().name())
                        .addValue("attempts", event.attempts())
                        .addValue("errorMessage", event.errorMessage())
                        .addValue("createdAt", event.createdAt())
                        .addValue("updatedAt", event.updatedAt())
                        .addValue("publishedAt", event.publishedAt())
        );
    }
}
