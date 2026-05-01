package com.tripify.tickets.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripify.tickets.generated.model.TicketDetail;
import com.tripify.tickets.service.config.property.KafkaTopicsProperties;
import com.tripify.tickets.service.exception.TicketKafkaPublishException;
import com.tripify.tickets.service.kafka.model.PackHeadersEvent;
import com.tripify.tickets.service.kafka.model.TicketPackViewEvent;
import com.tripify.tickets.service.kafka.model.TicketSearchContextEvent;
import com.tripify.tickets.service.kafka.model.UserType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TicketPackViewPublisher {

    private static final Logger logger = LoggerFactory.getLogger(TicketPackViewPublisher.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTopicsProperties kafkaTopics;
    private final ObjectMapper objectMapper;

    public void publishTicketDetailView(
            PackHeadersEvent headers,
            TicketSearchContextEvent search,
            TicketDetail ticketDetail
    ) {
        TicketPackViewEvent event = new TicketPackViewEvent(
                Instant.now(),
                headers,
                search,
                ticketDetail
        );

        String payload = serialize(event);
        String key = headers.generationId();

        kafkaTemplate.send(kafkaTopics.ticketsPackViewed(), key, payload)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        logger.error(
                                "Failed to publish ticket pack view event: generationId={}, tid={}, topic={}",
                                key,
                                ticketDetail.getTid(),
                                kafkaTopics.ticketsPackViewed(),
                                exception
                        );
                        return;
                    }
                    logger.debug(
                            "Ticket pack view event published: generationId={}, tid={}",
                            key,
                            ticketDetail.getTid()
                    );
                });
    }

    public static PackHeadersEvent toHeaders(
            UserType userType,
            String userId,
            String anonymousId,
            String generationId,
            int packRevision,
            String generationMode,
            String requestId,
            Integer ticketsRevision
    ) {
        return TicketKafkaEventFactory.toHeaders(
                userType,
                userId,
                anonymousId,
                generationId,
                packRevision,
                generationMode,
                requestId,
                ticketsRevision
        );
    }

    public static TicketSearchContextEvent toSearch(
            String originCityCode,
            String destinationCityCode,
            LocalDate departureDate,
            LocalDate returnDate,
            String currency,
            int adults,
            int children,
            int infants,
            Long budgetMaxAmount
    ) {
        return TicketKafkaEventFactory.toSearch(
                originCityCode,
                destinationCityCode,
                departureDate,
                returnDate,
                currency,
                adults,
                children,
                infants,
                budgetMaxAmount
        );
    }

    private String serialize(TicketPackViewEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException exception) {
            throw new TicketKafkaPublishException(
                    "Failed to serialize ticket pack view event for generationId="
                            + event.headers().generationId(),
                    exception
            );
        }
    }
}
