package com.tripify.users.service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripify.users.service.exception.app.InvalidUserRegisteredEventException;
import com.tripify.users.service.model.UserRegisteredEvent;
import com.tripify.users.service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegisteredKafkaListener {

    private final ObjectMapper objectMapper;
    private final UserProfileService userProfileService;

    @KafkaListener(
            topics = "${app.kafka.topics.user-registered}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        try {
            UserRegisteredEvent event = objectMapper.readValue(record.value(), UserRegisteredEvent.class);

            userProfileService.createProfile(event);

            acknowledgment.acknowledge();

            log.info(
                    "User registered processed. topic={}, partition={}, offset={}, userId={}",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    event.userId()
            );

        } catch (JsonProcessingException exception) {
            log.warn(
                    "Invalid user registered json skipped. topic={}, partition={}, offset={}",
                    record.topic(),
                    record.partition(),
                    record.offset()
            );

            acknowledgment.acknowledge();

        } catch (InvalidUserRegisteredEventException exception) {
            log.warn(
                    "Invalid user registered event skipped. topic={}, partition={}, offset={}, reason={}",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    exception.getMessage()
            );

            acknowledgment.acknowledge();

        } catch (Exception exception) {
            log.error(
                    "Failed to process user registered event. topic={}, partition={}, offset={}",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    exception
            );

            throw new RuntimeException("Failed to process user registered event", exception);
        }
    }
}
