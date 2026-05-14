package com.tripify.pack.persistence.mongo.document;

import com.tripify.pack.domain.GenerationMode;
import com.tripify.pack.domain.UserType;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pack_snapshots")
@CompoundIndex(
        name = "idx_pack_snapshots_lookup",
        def = "{'subjectId': 1, 'generationId': 1, 'packRevisionId': 1}"
)
public record PackSnapshotDocument(
        @Id
        String id,
        UserType userType,
        String subjectId,
        String userId,
        String anonymousId,
        String generationId,
        int packRevisionId,
        GenerationMode generationMode,
        int hotelRevisionId,
        int ticketRevisionId,
        org.bson.Document hotel,
        org.bson.Document ticket,
        SearchContextDocument searchContext,
        Instant createdAt
) {
}
