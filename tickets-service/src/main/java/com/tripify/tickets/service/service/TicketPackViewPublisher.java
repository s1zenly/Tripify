package com.tripify.tickets.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripify.tickets.generated.model.TicketDetail;
import com.tripify.tickets.service.config.property.KafkaTopicsProperties;
import com.tripify.tickets.service.exception.TicketKafkaPublishException;
import com.tripify.tickets.service.kafka.model.GenerationMode;
import com.tripify.tickets.service.kafka.model.PackHeadersEvent;
import com.tripify.tickets.service.kafka.model.PackViewEvent;
import com.tripify.tickets.service.kafka.model.SearchContextEvent;
import com.tripify.tickets.service.kafka.model.UserType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketPackViewPublisher {

    private static final Logger logger = LoggerFactory.getLogger(TicketPackViewPublisher.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTopicsProperties kafkaTopics;
    private final ObjectMapper objectMapper;

    public void publishTicketDetailView(
            PackHeadersEvent headers,
            SearchContextEvent searchContext,
            TicketDetail ticketDetail
    ) {
        JsonNode entity = objectMapper.valueToTree(ticketDetail);
        PackViewEvent event = new PackViewEvent(
                Instant.now(),
                headers,
                searchContext,
                entity
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
            GenerationMode generationMode,
            String requestId,
            Integer serviceRevision
    ) {
        return TicketKafkaEventFactory.toHeaders(
                userType,
                userId,
                anonymousId,
                generationId,
                packRevision,
                generationMode,
                requestId,
                serviceRevision
        );
    }

    public static SearchContextEvent toSearchContext(
            String originCountry,
            String originCity,
            String destinationCountry,
            String destinationCity,
            LocalDate dateFrom,
            LocalDate dateTo,
            String currency,
            int adults,
            Integer children,
            Long budget,
            List<String> filters
    ) {
        return SearchContextEvent.of(
                originCountry,
                originCity,
                destinationCountry,
                destinationCity,
                dateFrom,
                dateTo,
                currency,
                adults,
                children,
                budget,
                filters
        );
    }

    private String serialize(PackViewEvent event) {
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
