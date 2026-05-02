package com.tripify.hotels.service.repository.mongo.contract;

import java.util.Optional;

import com.tripify.hotels.service.model.documents.HotelReviewsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotelReviewsRepository extends MongoRepository<HotelReviewsDocument, String> {

    Optional<HotelReviewsDocument> findById(String hotelInternalId);

    HotelReviewsDocument save(HotelReviewsDocument document);

    boolean existsById(String hotelInternalId);
}
