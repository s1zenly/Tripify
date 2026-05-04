package com.tripify.hotels.service.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.kafka.model.ReviewCommentDto;
import com.tripify.hotels.service.kafka.model.ReviewsDto;
import com.tripify.hotels.service.model.documents.HotelReviewsDocument;
import com.tripify.hotels.service.model.documents.ReviewCommentDocument;
import com.tripify.hotels.service.repository.mongo.contract.HotelReviewsRepository;
import com.tripify.hotels.service.service.mapper.ReviewsDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelReviewsStorageService {

    private final HotelReviewsRepository repository;
    private final ReviewsDocumentMapper mapper;
    private final HotelPhotoStorageService photoStorageService;

    public UUID upsertReviews(UUID hotelId, ReviewsDto reviews, Instant now) {
        if (reviews == null) {
            return null;
        }

        Instant createdAt = repository.findById(hotelId.toString())
                .map(HotelReviewsDocument::createdAt)
                .orElse(now);

        List<ReviewCommentDocument> comments = mapComments(hotelId, reviews.comments());

        HotelReviewsDocument document = mapper.toDocument(
                hotelId,
                reviews,
                comments,
                createdAt,
                now
        );

        repository.save(document);

        return hotelId;
    }

    public HotelReviewsDocument findByHotelId(UUID hotelId) {
        return repository.findById(hotelId.toString())
                .orElseThrow(() -> new IllegalStateException(
                        "Hotel reviews not found for hotelId=" + hotelId
                ));
    }

    private List<ReviewCommentDocument> mapComments(
            UUID hotelId,
            List<ReviewCommentDto> comments
    ) {
        if (comments == null || comments.isEmpty()) {
            return List.of();
        }

        List<ReviewCommentDocument> result = new ArrayList<>(comments.size());

        for (int index = 0; index < comments.size(); index++) {
            ReviewCommentDto comment = comments.get(index);
            List<String> photoS3Keys = photoStorageService.uploadReviewPhotosFromUrls(
                    hotelId,
                    index,
                    comment.photos()
            );

            result.add(mapper.toComment(comment, photoS3Keys));
        }

        return List.copyOf(result);
    }
}
