package com.tripify.pack.persistence.mongo.repository;

import com.tripify.pack.persistence.mongo.document.HotelSnapshotDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotelSnapshotRepository extends MongoRepository<HotelSnapshotDocument, String> {
}
