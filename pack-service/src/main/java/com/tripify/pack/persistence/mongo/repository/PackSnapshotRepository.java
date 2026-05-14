package com.tripify.pack.persistence.mongo.repository;

import com.tripify.pack.persistence.mongo.document.PackSnapshotDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PackSnapshotRepository extends MongoRepository<PackSnapshotDocument, String> {
}
