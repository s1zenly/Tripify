package com.tripify.auth.service.repository;

import java.time.Instant;

import com.tripify.auth.service.domain.enums.OtpStatus;
import com.tripify.auth.service.domain.model.OtpRequest;
import com.tripify.auth.service.infra.SqlParams;
import com.tripify.auth.service.repository.contracts.OtpRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public int markPendingAsSuperseded(String phone, Instant updatedAt) {
        return namedParameterJdbcTemplate.update(
                MARK_PENDING_AS_SUPERSEDED_QUERY,
                new SqlParams()
                        .addValue("phone", phone)
                        .addValue("currentStatus", OtpStatus.PENDING.name())
                        .addValue("newStatus", OtpStatus.SUPERSEDED.name())
                        .addTimestamp("updatedAt", updatedAt)
        );
    }
}
