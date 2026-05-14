package com.tripify.pack.support;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tripify.pack.domain.AspectType;
import com.tripify.pack.domain.GenerationMode;
import com.tripify.pack.domain.KafkaEventMetadata;
import com.tripify.pack.domain.PackAspectEvent;
import com.tripify.pack.domain.SearchContext;
import com.tripify.pack.domain.UserType;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public final class PackAspectEventFactory {

    private static final AtomicLong OFFSET = new AtomicLong(1L);

    private PackAspectEventFactory() {
    }

    public static PackAspectEvent hotelFull(
            ObjectMapper objectMapper,
            String subjectId,
            String generationId,
            int packRevisionId,
            int hotelRevisionId,
            String hotelKey
    ) {
        return event(
                objectMapper,
                subjectId,
                generationId,
                GenerationMode.FULL,
                AspectType.HOTEL,
                packRevisionId,
                hotelRevisionId,
                payload(objectMapper, "hotel", hotelKey),
                "hotels.pack.viewed"
        );
    }

    public static PackAspectEvent ticketFull(
            ObjectMapper objectMapper,
            String subjectId,
            String generationId,
            int packRevisionId,
            int ticketRevisionId,
            String ticketKey
    ) {
        return event(
                objectMapper,
                subjectId,
                generationId,
                GenerationMode.FULL,
                AspectType.TICKET,
                packRevisionId,
                ticketRevisionId,
                payload(objectMapper, "ticket", ticketKey),
                "tickets.pack.viewed"
        );
    }

    public static PackAspectEvent hotelsOnly(
            ObjectMapper objectMapper,
            String subjectId,
            String generationId,
            int packRevisionId,
            int hotelRevisionId,
            String hotelKey
    ) {
        return event(
                objectMapper,
                subjectId,
                generationId,
                GenerationMode.HOTELS,
                AspectType.HOTEL,
                packRevisionId,
                hotelRevisionId,
                payload(objectMapper, "hotel", hotelKey),
                "hotels.pack.viewed"
        );
    }

    public static PackAspectEvent ticketsOnly(
            ObjectMapper objectMapper,
            String subjectId,
            String generationId,
            int packRevisionId,
            int ticketRevisionId,
            String ticketKey
    ) {
        return event(
                objectMapper,
                subjectId,
                generationId,
                GenerationMode.TICKETS,
                AspectType.TICKET,
                packRevisionId,
                ticketRevisionId,
                payload(objectMapper, "ticket", ticketKey),
                "tickets.pack.viewed"
        );
    }

    public static PackAspectEvent withKafkaMetadata(PackAspectEvent event, KafkaEventMetadata kafkaMetadata) {
        return new PackAspectEvent(
                event.eventId(),
                event.userType(),
                event.userId(),
                event.anonymousId(),
                event.generationId(),
                event.generationMode(),
                event.packRevisionId(),
                event.aspectType(),
                event.aspectRevisionId(),
                event.searchContext(),
                event.payload(),
                event.occurredAt(),
                kafkaMetadata
        );
    }

    private static PackAspectEvent event(
            ObjectMapper objectMapper,
            String subjectId,
            String generationId,
            GenerationMode generationMode,
            AspectType aspectType,
            int packRevisionId,
            int aspectRevisionId,
            JsonNode payload,
            String topic
    ) {
        long offset = OFFSET.getAndIncrement();
        return new PackAspectEvent(
                "req-" + packRevisionId + "-" + aspectType + "-" + offset,
                UserType.ANONYMOUS,
                null,
                subjectId,
                generationId,
                generationMode,
                packRevisionId,
                aspectType,
                aspectRevisionId,
                searchContext(),
                payload,
                Instant.parse("2026-05-31T20:20:25.090722476Z"),
                new KafkaEventMetadata(topic, 0, offset, null)
        );
    }

    private static JsonNode payload(ObjectMapper objectMapper, String entityType, String key) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put(entityType + "Key", key);
        return node;
    }

    private static SearchContext searchContext() {
        return new SearchContext(
                "RU",
                "MOW",
                "TH",
                "KBV",
                LocalDate.of(2026, 6, 4),
                LocalDate.of(2026, 6, 26),
                "RUB",
                1,
                1,
                1_233_333L,
                List.of()
        );
    }
}
