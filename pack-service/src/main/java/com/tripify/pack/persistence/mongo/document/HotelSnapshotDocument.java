package com.tripify.pack.persistence.mongo.document;

import com.tripify.pack.domain.UserType;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hotel_snapshots")
@CompoundIndex(
        name = "idx_hotel_snapshots_lookup",
        def = "{'subjectId': 1, 'generationId': 1, 'packRevisionId': 1, 'hotelRevisionId': 1}"
)
public record HotelSnapshotDocument(
        @Id
        String id,
        UserType userType,
        String subjectId,
        String userId,
        String anonymousId,
        String generationId,
        int packRevisionId,
        int hotelRevisionId,
        org.bson.Document payload,
        SearchContextDocument searchContext,
        Instant createdAt
) {
}
