package com.tripify.notification.service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripify.notification.service.exception.InvalidNotificationException;
import com.tripify.notification.service.handler.OtpSmsHandler;
import com.tripify.notification.service.model.OtpRequestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import static com.tripify.notification.service.utils.SecureUtils.maskPhone;

@Slf4j
@Component
@RequiredArgsConstructor
public class OtpRequestedKafkaListener {

    private final ObjectMapper objectMapper;
    private final OtpSmsHandler otpSmsHandler;

    @KafkaListener(
            topics = "${tripify.kafka.topics.otp-requested}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        try {
            OtpRequestedEvent event = objectMapper.readValue(record.value(), OtpRequestedEvent.class);

            otpSmsHandler.handle(event);

            acknowledgment.acknowledge();

            log.info(
                    "OTP processed. topic={}, partition={}, offset={}, requestId={}, phone={}",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    event.requestId(),
                    maskPhone(event.phone())
            );

        } catch (JsonProcessingException exception) {
            log.warn(
                    "Invalid OTP json skipped. topic={}, partition={}, offset={}",
                    record.topic(),
                    record.partition(),
                    record.offset()
            );

            acknowledgment.acknowledge();

        } catch (InvalidNotificationException exception) {
            log.warn(
                    "Invalid OTP skipped. topic={}, partition={}, offset={}, reason={}",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    exception.getMessage()
            );

            acknowledgment.acknowledge();

        } catch (Exception exception) {
            log.error(
                    "Failed to process OTP. topic={}, partition={}, offset={}",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    exception
            );

            throw new RuntimeException("Failed to process OTP requested event", exception);
        }
    }
}
