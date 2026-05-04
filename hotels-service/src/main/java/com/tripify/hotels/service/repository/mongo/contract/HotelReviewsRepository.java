package com.tripify.hotels.service.repository.mongo.contract;

import com.tripify.hotels.service.model.documents.HotelReviewsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotelReviewsRepository extends MongoRepository<HotelReviewsDocument, String> {
}
