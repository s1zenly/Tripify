package com.tripify.hotels.service.repository.mongo.contract;

import com.tripify.hotels.service.model.documents.HotelRoomsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotelRoomsRepository extends MongoRepository<HotelRoomsDocument, String> {
}
