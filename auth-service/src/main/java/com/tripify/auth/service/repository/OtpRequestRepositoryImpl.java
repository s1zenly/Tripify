package com.tripify.auth.service.repository;

import com.tripify.auth.service.domain.model.OtpRequest;
import com.tripify.auth.service.repository.contracts.OtpRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void save(OtpRequest otpRequest) {
        namedParameterJdbcTemplate.update(
                OTP_REQUEST_SAVE_QUERY,
                new MapSqlParameterSource()
                        .addValue("id", otpRequest.id())
                        .addValue("phone", otpRequest.phone())
                        .addValue("otpHash", otpRequest.otpHash())
                        .addValue("purpose", otpRequest.purpose().name())
                        .addValue("status", otpRequest.status().name())
                        .addValue("attempts", otpRequest.attempts())
                        .addValue("expiresAt", otpRequest.expiresAt())
                        .addValue("createdAt", otpRequest.createdAt())
                        .addValue("updatedAt", otpRequest.updatedAt())
        );
    }
}
