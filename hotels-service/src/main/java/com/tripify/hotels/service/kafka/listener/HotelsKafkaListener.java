package com.tripify.hotels.service.kafka.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripify.hotels.service.kafka.handler.HotelsParsedEventHandler;
import com.tripify.hotels.service.kafka.model.HotelsParsedEvent;
import com.tripify.hotels.service.service.dto.HotelsPersistenceResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelsKafkaListener {

    private final ObjectMapper objectMapper;
    private final HotelsParsedEventHandler hotelsParsedEventHandler;

    @KafkaListener(
            topics = "${tripify.kafka.topics.hotels-parsed}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        try {
            HotelsParsedEvent event = objectMapper.readValue(record.value(), HotelsParsedEvent.class);
            HotelsPersistenceResult result = hotelsParsedEventHandler.handle(event);

            if (result.hasFailures()) {
                log.warn(
                        "Hotels parsed event processed with partial failures. topic={}, partition={}, offset={}, saved={}, failed={}",
                        record.topic(),
                        record.partition(),
                        record.offset(),
                        result.savedHotels(),
                        result.failedHotels()
                );
            }

            acknowledgment.acknowledge();
        } catch (JsonProcessingException exception) {
            log.error(
                    "Failed to deserialize hotels parsed event. topic={}, partition={}, offset={}, key={}, payload={}",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    record.key(),
                    record.value(),
                    exception
            );

            acknowledgment.acknowledge();
        } catch (IllegalArgumentException exception) {
            log.error(
                    "Invalid hotels parsed event payload. topic={}, partition={}, offset={}, key={}",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    record.key(),
                    exception
            );

            acknowledgment.acknowledge();
        } catch (Exception exception) {
            log.error(
                    "Failed to process hotels parsed event. topic={}, partition={}, offset={}, key={}",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    record.key(),
                    exception
            );

            throw exception;
        }
    }
}
