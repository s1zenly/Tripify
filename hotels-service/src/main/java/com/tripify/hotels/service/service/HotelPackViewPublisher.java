package com.tripify.hotels.service.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripify.hotels.generated.model.HotelDetails;
import com.tripify.hotels.service.config.property.KafkaTopicsProperties;
import com.tripify.hotels.service.exception.HotelPackViewPublishException;
import com.tripify.hotels.service.kafka.model.GenerationMode;
import com.tripify.hotels.service.kafka.model.PackHeadersEvent;
import com.tripify.hotels.service.kafka.model.PackViewEvent;
import com.tripify.hotels.service.kafka.model.SearchContextEvent;
import com.tripify.hotels.service.kafka.model.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelPackViewPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTopicsProperties kafkaTopics;
    private final ObjectMapper objectMapper;

    public void publishHotelDetailView(
            PackHeadersEvent headers,
            SearchContextEvent searchContext,
            HotelDetails hotel
    ) {
        JsonNode entity = objectMapper.valueToTree(hotel);
        PackViewEvent event = new PackViewEvent(
                Instant.now(),
                headers,
                searchContext,
                entity
        );

        String payload = serialize(event);
        String key = headers.generationId();

        try {
            kafkaTemplate.send(kafkaTopics.hotelsPackViewed(), key, payload).get();
            log.debug(
                    "Hotel pack view event published: generationId={}, hotelId={}",
                    key,
                    hotel.getHotelId()
            );
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new HotelPackViewPublishException(
                    "Interrupted while publishing hotel pack view event for generationId=" + key,
                    exception
            );
        } catch (ExecutionException exception) {
            throw new HotelPackViewPublishException(
                    "Failed to publish hotel pack view event for generationId=" + key,
                    exception.getCause() != null ? exception.getCause() : exception
            );
        }
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
        return new PackHeadersEvent(
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
            throw new HotelPackViewPublishException(
                    "Failed to serialize hotel pack view event for generationId="
                            + event.headers().generationId(),
                    exception
            );
        }
    }
}
