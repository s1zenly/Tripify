package com.tripify.hotels.service.service.mapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tripify.hotels.service.kafka.model.ReviewCommentDto;
import com.tripify.hotels.service.kafka.model.ReviewsDto;
import com.tripify.hotels.service.model.documents.HotelReviewsDocument;
import com.tripify.hotels.service.model.documents.ReviewCommentDocument;
import org.springframework.stereotype.Component;

@Component
public class ReviewsDocumentMapper {

    public HotelReviewsDocument toDocument(
            UUID hotelId,
            ReviewsDto reviews,
            List<ReviewCommentDocument> comments,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new HotelReviewsDocument(
                hotelId.toString(),
                reviews.total(),
                toDouble(reviews.rating()),
                toHistogram(reviews.reviewsHistogram()),
                toReviewsClasses(reviews.reviewsClasses()),
                comments,
                resolveTotalComments(reviews, comments.size()),
                createdAt,
                updatedAt
        );
    }

    public ReviewCommentDocument toComment(ReviewCommentDto comment, List<String> photoS3Keys) {
        return new ReviewCommentDocument(
                comment.author(),
                comment.country(),
                comment.vacationType(),
                toDouble(comment.rating()),
                comment.goodPart(),
                comment.badPart(),
                comment.commonText(),
                comment.reviewDate(),
                photoS3Keys != null ? photoS3Keys : List.of()
        );
    }

    public Map<String, Integer> toHistogram(Map<Integer, Integer> histogram) {
        if (histogram == null || histogram.isEmpty()) {
            return Map.of();
        }

        Map<String, Integer> result = new LinkedHashMap<>();
        histogram.forEach((key, value) -> result.put(String.valueOf(key), value));
        return Map.copyOf(result);
    }

    public Map<String, Double> toReviewsClasses(Map<String, Object> reviewsClasses) {
        if (reviewsClasses == null || reviewsClasses.isEmpty()) {
            return Map.of();
        }

        Map<String, Double> result = new LinkedHashMap<>();
        reviewsClasses.forEach((key, value) -> {
            Double parsed = toDouble(value);
            if (parsed != null) {
                result.put(key, parsed);
            }
        });

        return Map.copyOf(result);
    }

    public Double toDouble(BigDecimal value) {
        return value == null ? null : value.doubleValue();
    }

    public int resolveTotalComments(ReviewsDto reviews, int mappedCommentsCount) {
        if (reviews.totalComments() != null) {
            return reviews.totalComments();
        }

        return mappedCommentsCount;
    }

    private static Double toDouble(Object value) {
        return switch (value) {
            case BigDecimal bigDecimal -> bigDecimal.doubleValue();
            case Number number -> number.doubleValue();
            case String string -> Double.parseDouble(string);
            case null, default -> null;
        };

    }
}
