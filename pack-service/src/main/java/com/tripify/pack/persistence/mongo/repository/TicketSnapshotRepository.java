package com.tripify.pack.persistence.mongo.repository;

import com.tripify.pack.persistence.mongo.document.TicketSnapshotDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketSnapshotRepository extends MongoRepository<TicketSnapshotDocument, String> {
}
