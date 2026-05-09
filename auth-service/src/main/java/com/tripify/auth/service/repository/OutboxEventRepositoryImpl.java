package com.tripify.auth.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tripify.auth.service.domain.enums.AggregateType;
import com.tripify.auth.service.domain.enums.OtpStatus;
import com.tripify.auth.service.domain.enums.OutboxEventStatus;
import com.tripify.auth.service.domain.enums.OutboxEventType;
import com.tripify.auth.service.domain.model.OutboxEvent;
import com.tripify.auth.service.infra.SqlParams;
import com.tripify.auth.service.repository.contracts.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.tripify.auth.service.utils.ResultSetUtils.getTimestamp;

@Repository
@RequiredArgsConstructor
public class OutboxEventRepositoryImpl implements OutboxEventRepository {

    private static final String FIND_PENDING_FOR_PUBLISH_QUERY =
            """
            select id,
                   aggregate_type,
                   aggregate_id,
                   event_type,
                   topic,
                   payload::text as payload,
                   status,
                   attempts,
                   error_message,
                   created_at,
                   updated_at,
                   published_at
            from outbox_events
            where status = :status
              and attempts < :maxAttempts
            order by created_at
            limit :limit
            for update skip locked
            """;

    private static final String MARK_AS_PUBLISHED_QUERY =
            """
            update outbox_events
            set status = :status,
                updated_at = :updatedAt,
                published_at = :publishedAt
            where id = :id
            """;

    private static final String MARK_AS_FAILED_ATTEMPT_QUERY =
            """
            update outbox_events
            set attempts = attempts + 1,
                status = case
                    when attempts + 1 >= :maxAttempts then :failedStatus
                    else :pendingStatus
                end,
                error_message = :errorMessage,
                updated_at = :updatedAt
            where id = :id
            """;

    private static final String SAVE_OUTBOX_EVENT_QUERY =
            """
            insert into outbox_events(id, aggregate_type, aggregate_id, event_type, topic, payload, status,
                                       attempts, error_message, created_at, updated_at, published_at)
            values(:id, :aggregateType, :aggregateId, :eventType, :topic, cast(:payload as jsonb), :status,
                    :attempts,:errorMessage,:createdAt,:updatedAt,:publishedAt)
            """;

    private static final String MARK_PENDING_OTP_EVENTS_AS_SKIPPED_QUERY =
            """
            update outbox_events oe
            set status = :newStatus,
                updated_at = :updatedAt
            where oe.status = :currentStatus
              and oe.event_type = :eventType
              and exists (
                  select 1
                  from otp_requests otp
                  where otp.id = oe.aggregate_id::uuid
                    and otp.phone = :phone
                    and otp.status = :otpStatus
              )
            """;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void saveEvent(OutboxEvent event) {
        namedParameterJdbcTemplate.update(
                SAVE_OUTBOX_EVENT_QUERY,
                new SqlParams()
                        .addValue("id", event.id())
                        .addValue("aggregateType", event.aggregateType().name())
                        .addValue("aggregateId", event.aggregateId())
                        .addValue("eventType", event.eventType().name())
                        .addValue("topic", event.topic())
                        .addValue("payload", event.payload())
                        .addValue("status", event.status().name())
                        .addValue("attempts", event.attempts())
                        .addValue("errorMessage", event.errorMessage())
                        .addTimestamp("createdAt", event.createdAt())
                        .addTimestamp("updatedAt", event.updatedAt())
                        .addTimestamp("publishedAt", event.publishedAt())
        );
    }

    @Override
    public void markPendingOtpEventsAsSkipped(
            String phone,
            OutboxEventType eventType,
            Instant updatedAt
    ) {
        namedParameterJdbcTemplate.update(
                MARK_PENDING_OTP_EVENTS_AS_SKIPPED_QUERY,
                new SqlParams()
                        .addValue("phone", phone)
                        .addValue("currentStatus", OutboxEventStatus.PENDING.name())
                        .addValue("newStatus", OutboxEventStatus.SKIPPED.name())
                        .addValue("eventType", eventType.name())
                        .addValue("otpStatus", OtpStatus.SUPERSEDED.name())
                        .addTimestamp("updatedAt", updatedAt)
        );
    }

    @Override
    public List<OutboxEvent> findPendingForPublish(
            int limit,
            int maxAttempts
    ) {
        return namedParameterJdbcTemplate.query(
                FIND_PENDING_FOR_PUBLISH_QUERY,
                new SqlParams()
                        .addValue("status", OutboxEventStatus.PENDING.name())
                        .addValue("limit", limit)
                        .addValue("maxAttempts", maxAttempts),
                this::mapOutboxEvent
        );
    }

    @Override
    public int markAsPublished(
            UUID id,
            Instant publishedAt
    ) {
        return namedParameterJdbcTemplate.update(
                MARK_AS_PUBLISHED_QUERY,
                new SqlParams()
                        .addValue("id", id)
                        .addValue("status", OutboxEventStatus.PUBLISHED.name())
                        .addTimestamp("updatedAt", publishedAt)
                        .addTimestamp("publishedAt", publishedAt)
        );
    }

    @Override
    public int markAsFailedAttempt(UUID id, String errorMessage, Instant updatedAt, int maxAttempts) {
        return namedParameterJdbcTemplate.update(
                MARK_AS_FAILED_ATTEMPT_QUERY,
                new SqlParams()
                        .addValue("id", id)
                        .addValue("maxAttempts", maxAttempts)
                        .addValue("failedStatus", OutboxEventStatus.FAILED.name())
                        .addValue("pendingStatus", OutboxEventStatus.PENDING.name())
                        .addValue("errorMessage", cutErrorMessage(errorMessage))
                        .addTimestamp("updatedAt", updatedAt)
        );
    }

    private OutboxEvent mapOutboxEvent(ResultSet rs, int rowNum) throws SQLException {
        return new OutboxEvent(
                rs.getObject("id", java.util.UUID.class),
                AggregateType.valueOf(rs.getString("aggregate_type")),
                rs.getString("aggregate_id"),
                OutboxEventType.valueOf(rs.getString("event_type")),
                rs.getString("topic"),
                rs.getString("payload"),
                OutboxEventStatus.valueOf(rs.getString("status")),
                rs.getInt("attempts"),
                rs.getString("error_message"),
                getTimestamp("created_at", rs),
                getTimestamp("updated_at", rs),
                getTimestamp("published_at", rs)
        );
    }

    private static String cutErrorMessage(String errorMessage) {
        return errorMessage == null ? null : errorMessage.substring(0, 1000);
    }
}
