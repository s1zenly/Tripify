package com.tripify.auth.service.repository;

import java.time.Instant;

import com.tripify.auth.service.domain.enums.OtpStatus;
import com.tripify.auth.service.domain.enums.OutboxEventStatus;
import com.tripify.auth.service.domain.enums.OutboxEventType;
import com.tripify.auth.service.domain.model.OutboxEvent;
import com.tripify.auth.service.infra.SqlParams;
import com.tripify.auth.service.repository.contracts.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
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
    public int markPendingOtpEventsAsSkipped(String phone, OutboxEventType eventType, Instant updatedAt) {
        return namedParameterJdbcTemplate.update(
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
}
