package com.tripify.auth.service.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import com.tripify.auth.service.config.property.KafkaTopics;
import com.tripify.auth.service.domain.enums.AggregateType;
import com.tripify.auth.service.domain.enums.OtpPurpose;
import com.tripify.auth.service.domain.enums.OtpStatus;
import com.tripify.auth.service.domain.enums.OutboxEventStatus;
import com.tripify.auth.service.domain.enums.OutboxEventType;
import com.tripify.auth.service.domain.model.CreateOtpResult;
import com.tripify.auth.service.domain.model.OtpRequest;
import com.tripify.auth.service.domain.model.OtpRequestedEvent;
import com.tripify.auth.service.domain.model.OutboxEvent;
import com.tripify.auth.service.exception.InternalServerException;
import com.tripify.auth.service.infra.TimeProvider;
import com.tripify.auth.service.helper.OtpCodeGenerator;
import com.tripify.auth.service.helper.Hasher;
import com.tripify.auth.service.repository.contracts.OtpRequestRepository;
import com.tripify.auth.service.repository.contracts.OutboxEventRepository;
import com.tripify.auth.service.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpService {

    private static final Duration OTP_TTL = Duration.ofMinutes(5);

    private final Hasher hasher;
    private final OtpCodeGenerator otpCodeGenerator;
    private final OtpRequestRepository otpRequestRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTopics kafkaTopics;
    private final TimeProvider timeProvider;
    private final TransactionTemplate transactionTemplate;


    public CreateOtpResult createOtpRequest(String phone, OtpPurpose purpose) {
        log.info("Start create otp request for phone - {}", phone);
        String rawOtp = otpCodeGenerator.generate();
        String otpHash = hasher.hashBCrypt(rawOtp);
        UUID otpRequestId = UUID.randomUUID();

        Instant now = timeProvider.nowUtc();
        Instant expiredAt = now.plus(OTP_TTL);

        OtpRequest otpRequest = new OtpRequest(otpRequestId, phone, otpHash, purpose, OtpStatus.PENDING, 0, now.plus(OTP_TTL), now, now);
        OtpRequestedEvent eventPayload = new OtpRequestedEvent(phone, rawOtp, now, expiredAt);
        OutboxEvent outboxEvent = new OutboxEvent(
                UUID.randomUUID(),
                AggregateType.OTP_REQUEST,
                otpRequestId.toString(),
                OutboxEventType.OTP_REQUESTED,
                kafkaTopics.otpRequested(),
                JsonUtils.toJson(eventPayload),
                OutboxEventStatus.PENDING,
                0,
                null,
                now,
                now,
                null
        );

        try {
            transactionTemplate.executeWithoutResult(status -> {
                otpRequestRepository.markPendingAsSuperseded(phone, now);
                outboxEventRepository.markPendingOtpEventsAsSkipped(phone, OutboxEventType.OTP_REQUESTED, now);

                otpRequestRepository.save(otpRequest);
                outboxEventRepository.saveEvent(outboxEvent);
            });
        } catch (DataAccessException exception) {
            log.error("Failed to persist otp request and outbox event for phone = {}", phone, exception);
            throw new InternalServerException("Failed to create otp request");
        }

        return new CreateOtpResult(otpRequest.id(), rawOtp);
    }
}
