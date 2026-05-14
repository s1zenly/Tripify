package com.tripify.pack.kafka.mapper;

import com.tripify.pack.domain.AspectType;
import com.tripify.pack.domain.KafkaEventMetadata;
import com.tripify.pack.domain.PackAspectEvent;
import com.tripify.pack.domain.SearchContext;
import com.tripify.pack.kafka.model.PackViewEvent;
import com.tripify.pack.kafka.model.SearchContextEvent;

import java.util.List;

public final class PackAspectEventMapper {

    private PackAspectEventMapper() {
    }

    public static PackAspectEvent toPackAspectEvent(
            PackViewEvent event,
            AspectType aspectType,
            KafkaEventMetadata kafkaMetadata
    ) {
        var headers = event.headers();

        return new PackAspectEvent(
                resolveEventId(headers.requestId(), kafkaMetadata),
                headers.userType(),
                headers.userId(),
                headers.anonymousId(),
                headers.generationId(),
                headers.generationMode(),
                headers.packRevision(),
                aspectType,
                requireServiceRevision(headers.serviceRevision()),
                toSearchContext(event.searchContext()),
                event.entity(),
                event.occurredAt(),
                kafkaMetadata
        );
    }

    private static String resolveEventId(String requestId, KafkaEventMetadata kafkaMetadata) {
        if (requestId != null && !requestId.isBlank()) {
            return requestId;
        }
        if (kafkaMetadata.eventId() != null && !kafkaMetadata.eventId().isBlank()) {
            return kafkaMetadata.eventId();
        }
        return kafkaMetadata.topic() + ":" + kafkaMetadata.kafkaPartition() + ":" + kafkaMetadata.kafkaOffset();
    }

    private static int requireServiceRevision(Integer serviceRevision) {
        if (serviceRevision == null) {
            throw new IllegalArgumentException("service_revision is required");
        }
        return serviceRevision;
    }

    private static SearchContext toSearchContext(SearchContextEvent searchContext) {
        return new SearchContext(
                searchContext.originCountry(),
                searchContext.originCity(),
                searchContext.destinationCountry(),
                searchContext.destinationCity(),
                searchContext.dateFrom(),
                searchContext.dateTo(),
                searchContext.currency(),
                searchContext.adults(),
                searchContext.children(),
                searchContext.budget(),
                searchContext.filters() == null ? List.of() : List.copyOf(searchContext.filters())
        );
    }
}
