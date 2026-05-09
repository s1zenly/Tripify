package com.tripify.auth.service.repository.contracts;

import java.time.Instant;

import com.tripify.auth.service.domain.model.OtpRequest;

public interface OtpRequestRepository {

    void save(OtpRequest otpRequest);

    int markPendingAsSuperseded(String phone, Instant updatedAt);
}
