package com.tripify.tickets.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripify.tickets.service.config.property.KafkaTopicsProperties;
import com.tripify.tickets.service.exception.TicketKafkaPublishException;
import com.tripify.tickets.service.kafka.model.TicketSearchCompletedEvent;
import com.tripify.tickets.service.kafka.model.TicketSearchContextEvent;
import com.tripify.tickets.service.model.search.ProviderSearchResult;
import com.tripify.tickets.service.model.unified.UnifiedOffer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketSearchAnalyticsPublisher {

    private static final Logger logger = LoggerFactory.getLogger(TicketSearchAnalyticsPublisher.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTopicsProperties kafkaTopics;
    private final ObjectMapper objectMapper;

    public void publishSearchCompleted(
            TicketSearchContextEvent search,
            List<UnifiedOffer> offers,
            List<ProviderSearchResult> providerResults
    ) {
        TicketSearchCompletedEvent event = new TicketSearchCompletedEvent(
                Instant.now(),
                search,
                TicketKafkaEventFactory.toOfferSnapshots(offers),
                TicketKafkaEventFactory.toProviderAnalytics(providerResults)
        );

        String payload = serialize(event);
        String key = searchKey(search);

        kafkaTemplate.send(kafkaTopics.ticketsSearchCompleted(), key, payload)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        logger.error(
                                "Failed to publish ticket search analytics event: key={}, topic={}",
                                key,
                                kafkaTopics.ticketsSearchCompleted(),
                                exception
                        );
                        return;
                    }
                    logger.debug(
                            "Ticket search analytics event published: key={}, offers={}, providers={}",
                            key,
                            offers.size(),
                            providerResults.size()
                    );
                });
    }

    private String searchKey(TicketSearchContextEvent search) {
        return String.join(
                ":",
                search.originCityCode(),
                search.destinationCityCode(),
                search.departureDate().toString(),
                search.returnDate().toString(),
                String.valueOf(search.adults()),
                String.valueOf(search.children())
        );
    }

    private String serialize(TicketSearchCompletedEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException exception) {
            throw new TicketKafkaPublishException(
                    "Failed to serialize ticket search completed event for key="
                            + searchKey(event.search()),
                    exception
            );
        }
    }
}
