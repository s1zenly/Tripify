package com.tripify.pack.persistence.mongo.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.tripify.pack.domain.PackAspectEvent;
import com.tripify.pack.domain.PackSnapshot;
import com.tripify.pack.domain.SearchContext;
import com.tripify.pack.persistence.mongo.document.HotelSnapshotDocument;
import com.tripify.pack.persistence.mongo.document.PackSnapshotDocument;
import com.tripify.pack.persistence.mongo.document.SearchContextDocument;
import com.tripify.pack.persistence.mongo.document.TicketSnapshotDocument;
import org.bson.Document;

import java.time.Instant;
import java.util.List;

public final class SnapshotDocumentMapper {

    private SnapshotDocumentMapper() {
    }

    public static HotelSnapshotDocument toHotelSnapshotDocument(PackAspectEvent event, Instant createdAt) {
        return new HotelSnapshotDocument(
                null,
                event.userType(),
                event.subjectId(),
                event.userId(),
                event.anonymousId(),
                event.generationId(),
                event.packRevisionId(),
                event.aspectRevisionId(),
                toDocument(event.payload()),
                toSearchContextDocument(event.searchContext()),
                createdAt
        );
    }

    public static TicketSnapshotDocument toTicketSnapshotDocument(PackAspectEvent event, Instant createdAt) {
        return new TicketSnapshotDocument(
                null,
                event.userType(),
                event.subjectId(),
                event.userId(),
                event.anonymousId(),
                event.generationId(),
                event.packRevisionId(),
                event.aspectRevisionId(),
                toDocument(event.payload()),
                toSearchContextDocument(event.searchContext()),
                createdAt
        );
    }

    public static PackSnapshotDocument toPackSnapshotDocument(PackSnapshot snapshot, Instant createdAt) {
        return new PackSnapshotDocument(
                null,
                snapshot.userType(),
                snapshot.subjectId(),
                snapshot.userId(),
                snapshot.anonymousId(),
                snapshot.generationId(),
                snapshot.packRevisionId(),
                snapshot.generationMode(),
                snapshot.hotelRevisionId(),
                snapshot.ticketRevisionId(),
                toDocument(snapshot.hotel()),
                toDocument(snapshot.ticket()),
                toSearchContextDocument(snapshot.searchContext()),
                createdAt
        );
    }

    public static SearchContextDocument toSearchContextDocument(SearchContext searchContext) {
        if (searchContext == null) {
            throw new IllegalArgumentException("searchContext is required");
        }
        return new SearchContextDocument(
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

    public static SearchContext toSearchContext(SearchContextDocument searchContext) {
        if (searchContext == null) {
            throw new IllegalStateException("searchContext is missing in snapshot");
        }
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

    public static Document toDocument(JsonNode payload) {
        if (payload == null || payload.isNull()) {
            return new Document();
        }
        return Document.parse(payload.toString());
    }
}
