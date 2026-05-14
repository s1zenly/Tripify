package com.tripify.pack.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripify.pack.domain.AspectType;
import com.tripify.pack.domain.KafkaEventMetadata;
import com.tripify.pack.domain.PackAspectEvent;
import com.tripify.pack.kafka.mapper.PackAspectEventMapper;
import com.tripify.pack.kafka.model.PackViewEvent;
import com.tripify.pack.service.PackRevisionService;
import com.tripify.pack.service.exception.InvalidPackEventException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PackViewEventConsumerSupport {

    private final ObjectMapper objectMapper;
    private final PackRevisionService packRevisionService;

    public void consume(ConsumerRecord<String, String> record, AspectType aspectType, Acknowledgment acknowledgment) {
        try {
            PackViewEvent event = objectMapper.readValue(record.value(), PackViewEvent.class);
            PackAspectEvent aspectEvent = PackAspectEventMapper.toPackAspectEvent(
                    event,
                    aspectType,
                    new KafkaEventMetadata(
                            record.topic(),
                            record.partition(),
                            record.offset(),
                            null
                    )
            );

            packRevisionService.processAspectEvent(aspectEvent);
            acknowledgment.acknowledge();

            log.info(
                    "Pack view event processed. topic={}, partition={}, offset={}, generationId={}, packRevisionId={}, mode={}, aspect={}",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    aspectEvent.generationId(),
                    aspectEvent.packRevisionId(),
                    aspectEvent.generationMode(),
                    aspectType
            );
        } catch (JsonProcessingException exception) {
            log.error(
                    "Failed to deserialize pack view event. topic={}, partition={}, offset={}, key={}, payload={}",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    record.key(),
                    record.value(),
                    exception
            );
            acknowledgment.acknowledge();
        } catch (InvalidPackEventException exception) {
            log.warn(
                    "Invalid pack view event skipped. topic={}, partition={}, offset={}, key={}, reason={}",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    record.key(),
                    exception.getMessage()
            );
            acknowledgment.acknowledge();
        } catch (Exception exception) {
            log.error(
                    "Failed to process pack view event. topic={}, partition={}, offset={}, key={}",
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
