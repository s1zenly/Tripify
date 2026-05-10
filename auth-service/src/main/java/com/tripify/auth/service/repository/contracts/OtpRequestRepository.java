package com.tripify.auth.service.repository.contracts;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.tripify.auth.service.domain.enums.OtpPurpose;
import com.tripify.auth.service.domain.enums.OtpStatus;
import com.tripify.auth.service.domain.model.OtpRequest;

public interface OtpRequestRepository {

    void save(OtpRequest otpRequest);

    void markPendingAsSuperseded(String phone, Instant updatedAt);

    Optional<OtpRequest> findLatestPendingByPhone(String phone);

    void changeOtpStatus(UUID otpRequestId, OtpStatus newStatus, Instant now);

    int incrementAttempts(UUID otpRequestId, Instant now);
}
