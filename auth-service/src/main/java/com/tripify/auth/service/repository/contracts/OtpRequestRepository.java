package com.tripify.auth.service.repository.contracts;

import com.tripify.auth.service.domain.model.OtpRequest;

public interface OtpRequestRepository {

    void save(OtpRequest otpRequest);
}
