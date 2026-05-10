package com.tripify.auth.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.tripify.auth.service.domain.enums.OtpPurpose;
import com.tripify.auth.service.domain.enums.OtpStatus;
import com.tripify.auth.service.domain.model.OtpRequest;
import com.tripify.auth.service.infra.SqlParams;
import com.tripify.auth.service.repository.contracts.OtpRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.tripify.auth.service.utils.ResultSetUtils.getTimestamp;

@Repository
@RequiredArgsConstructor
public class OtpRequestRepositoryImpl implements OtpRequestRepository {

    private static final String OTP_REQUEST_SAVE_QUERY =
            """
            insert into otp_requests(id,phone,otp_hash,purpose,status,attempts,expires_at,created_at,updated_at)
            values(:id,:phone,:otpHash,:purpose,:status,:attempts,:expiresAt,:createdAt,:updatedAt)
            """;

    private static final String MARK_PENDING_AS_SUPERSEDED_QUERY =
            """
            update otp_requests
            set status = :newStatus,
                updated_at = :updatedAt
            where phone = :phone
              and status = :currentStatus
            """;

    private static final String FIND_LATEST_PENDING_BY_PHONE_QUERY =
                """
                select id,
                       phone,
                       otp_hash,
                       purpose,
                       status,
                       attempts,
                       expires_at,
                       created_at,
                       updated_at
                from otp_requests
                where phone = :phone
                  and status = :status
                order by created_at desc
                limit 1
                """;

    private static final String CHANGE_OTP_STATUS_QUERY =
            """
            update otp_requests
            set status = :status,
                updated_at = :updatedAt
            where id = :id
            """;

    private static final String INCREMENT_ATTEMPTS_QUERY =
            """
            update otp_requests
            set attempts = attempts + 1,
                updated_at = :updatedAt
            where id = :id
            returning attempts
            """;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void save(OtpRequest otpRequest) {
        namedParameterJdbcTemplate.update(
                OTP_REQUEST_SAVE_QUERY,
                new SqlParams()
                        .addValue("id", otpRequest.id())
                        .addValue("phone", otpRequest.phone())
                        .addValue("otpHash", otpRequest.otpHash())
                        .addValue("purpose", otpRequest.purpose().name())
                        .addValue("status", otpRequest.status().name())
                        .addValue("attempts", otpRequest.attempts())
                        .addTimestamp("expiresAt", otpRequest.expiresAt())
                        .addTimestamp("createdAt", otpRequest.createdAt())
                        .addTimestamp("updatedAt", otpRequest.updatedAt())
        );
    }

    @Override
    public void markPendingAsSuperseded(String phone, Instant updatedAt) {
        namedParameterJdbcTemplate.update(
                MARK_PENDING_AS_SUPERSEDED_QUERY,
                new SqlParams()
                        .addValue("phone", phone)
                        .addValue("currentStatus", OtpStatus.PENDING.name())
                        .addValue("newStatus", OtpStatus.SUPERSEDED.name())
                        .addTimestamp("updatedAt", updatedAt)
        );
    }

    @Override
    public Optional<OtpRequest> findLatestPendingByPhone(String phone) {
        return namedParameterJdbcTemplate.query(
                FIND_LATEST_PENDING_BY_PHONE_QUERY,
                new SqlParams()
                        .addValue("phone", phone)
                        .addValue("status", OtpStatus.PENDING.name()),
                this::mapOtpRequest
        ).stream().findFirst();
    }

    @Override
    public void changeOtpStatus(UUID otpRequestId, OtpStatus newStatus, Instant now) {
        namedParameterJdbcTemplate.update(
                CHANGE_OTP_STATUS_QUERY,
                new SqlParams()
                        .addValue("id", otpRequestId)
                        .addValue("status", newStatus.name())
                        .addTimestamp("updatedAt", now)
        );
    }

    @Override
    public int incrementAttempts(UUID otpRequestId, Instant now) {
        return namedParameterJdbcTemplate.queryForObject(
                INCREMENT_ATTEMPTS_QUERY,
                new SqlParams()
                        .addValue("id", otpRequestId)
                        .addTimestamp("updatedAt", now),
                Integer.class
        ).intValue();
    }

    private OtpRequest mapOtpRequest(ResultSet rs, int rowNum) throws SQLException {
        return new OtpRequest(
                UUID.fromString(rs.getString("id")),
                rs.getString("phone"),
                rs.getString("otp_hash"),
                OtpPurpose.valueOf(rs.getString("purpose")),
                OtpStatus.valueOf(rs.getString("status")),
                rs.getInt("attempts"),
                getTimestamp("expires_at", rs),
                getTimestamp("created_at", rs),
                getTimestamp("updated_at", rs)
        );
    }
}
