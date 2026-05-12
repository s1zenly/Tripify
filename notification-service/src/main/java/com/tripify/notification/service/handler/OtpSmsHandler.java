package com.tripify.notification.service.handler;

import java.time.Instant;

import com.tripify.notification.service.exception.InvalidNotificationException;
import com.tripify.notification.service.handler.contract.DefaultNotificationHandler;
import com.tripify.notification.service.model.InvalidMessagePolicy;
import com.tripify.notification.service.model.NotificationChannel;
import com.tripify.notification.service.model.OtpRequestedEvent;
import com.tripify.notification.service.sender.NotificationSender;
import com.tripify.notification.service.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OtpSmsHandler extends DefaultNotificationHandler<OtpRequestedEvent> {

    private final NotificationSender notificationSender;

    @Override
    public InvalidMessagePolicy invalidMessagePolicy() {
        return InvalidMessagePolicy.SKIP;
    }

    @Override
    public void handle(OtpRequestedEvent event) {
        validate(event);

        notificationSender.send(
                NotificationChannel.SMS,
                event.phone(),
                Constants.OTP_SEND_MESSAGE + event.code()
        );
    }

    @Override
    protected void validate(OtpRequestedEvent event) {
        if (event.phone() == null || event.phone().isBlank()) {
            throw new InvalidNotificationException("Phone is empty");
        }

        if (event.code() == null || !event.code().matches("\\d{4}")) {
            throw new InvalidNotificationException("OTP code must contain 4 digits");
        }

        if (event.expiresAt() == null) {
            throw new InvalidNotificationException("OTP expiration date is empty");
        }

        if (Instant.now().isAfter(event.expiresAt())) {
            throw new InvalidNotificationException("OTP code expired");
        }
    }
}