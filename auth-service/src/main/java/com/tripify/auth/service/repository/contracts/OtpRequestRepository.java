package com.tripify.auth.service.repository.contracts;

import java.util.Optional;
import java.util.UUID;

import com.tripify.auth.service.domain.enums.OtpPurpose;
import com.tripify.auth.service.domain.model.OtpRequest;

public interface OtpRequestRepository {

    void save(OtpRequest otpRequest);

    Optional<OtpRequest> findById(UUID id);

    Optional<OtpRequest> findLatestPendingByPhoneAndPurpose(
            String phone,
            OtpPurpose purpose
    );

    void update(OtpRequest otpRequest);

    void markVerified(UUID id);

    void markFailed(UUID id);

    void incrementAttempts(UUID id);
}
