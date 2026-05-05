package com.tripify.hotels.service.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripify.hotels.generated.model.HotelDetails;
import com.tripify.hotels.service.config.property.KafkaTopicsProperties;
import com.tripify.hotels.service.exception.HotelPackViewPublishException;
import com.tripify.hotels.service.kafka.model.HotelPackViewEvent;
import com.tripify.hotels.service.kafka.model.HotelSearchContextEvent;
import com.tripify.hotels.service.kafka.model.PackHeadersEvent;
import com.tripify.hotels.service.kafka.model.UserType;
import com.tripify.hotels.service.model.outbox.AggregateType;
import com.tripify.hotels.service.model.outbox.OutboxEvent;
import com.tripify.hotels.service.model.outbox.OutboxEventStatus;
import com.tripify.hotels.service.model.outbox.OutboxEventType;
import com.tripify.hotels.service.repository.contract.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelPackViewPublisher {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTopicsProperties kafkaTopics;
    private final ObjectMapper objectMapper;

    public void publishHotelDetailView(
            PackHeadersEvent headers,
            HotelSearchContextEvent search,
            HotelDetails hotel
    ) {
        HotelPackViewEvent event = new HotelPackViewEvent(
                Instant.now(),
                headers,
                search,
                null,
                hotel
        );

        String payload = serialize(event);
        Instant now = Instant.now();

        outboxEventRepository.saveEvent(new OutboxEvent(
                UUID.randomUUID(),
                AggregateType.HOTEL_PACK_VIEW,
                headers.generationId(),
                OutboxEventType.HOTEL_PACK_VIEWED,
                kafkaTopics.hotelsPackViewed(),
                payload,
                OutboxEventStatus.PENDING,
                0,
                null,
                now,
                now,
                null
        ));
    }

    public static PackHeadersEvent toHeaders(
            UserType userType,
            String userId,
            String anonymousId,
            String generationId,
            int packRevision,
            String generationMode,
            String requestId,
            Integer hotelsRevision
    ) {
        return new PackHeadersEvent(
                userType,
                userId,
                anonymousId,
                generationId,
                packRevision,
                generationMode,
                requestId,
                hotelsRevision
        );
    }

    public static HotelSearchContextEvent toSearch(
            String country,
            String city,
            LocalDate checkIn,
            LocalDate checkOut,
            String currency,
            int guests,
            Long budget,
            List<String> filters
    ) {
        return new HotelSearchContextEvent(
                country,
                city,
                checkIn,
                checkOut,
                currency,
                guests,
                budget,
                filters == null ? List.of() : List.copyOf(filters)
        );
    }

    private String serialize(HotelPackViewEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException exception) {
            throw new HotelPackViewPublishException(
                    "Failed to serialize hotel pack view event for generationId="
                            + event.headers().generationId(),
                    exception
            );
        }
    }
}
