package com.tripify.hotels.service.model.documents;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hotel_reviews")
public record HotelReviewsDocument(
        @Id
        String id,
        Integer total,
        Double rating,
        Map<String, Integer> reviewsHistogram,
        Map<String, Double> reviewsClasses,
        List<ReviewCommentDocument> comments,
        Integer totalComments,
        Instant createdAt,
        Instant updatedAt
) {
}
