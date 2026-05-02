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

    public UUID upsertReviews(
            UUID hotelInternalId,
            Long externalHotelId,
            String providerName,
            ReviewsDto reviews,
            Instant now
    ) {
        if (reviews == null) {
            return null;
        }

        Instant createdAt = repository.findById(hotelInternalId.toString())
                .map(HotelReviewsDocument::createdAt)
                .orElse(now);

        List<ReviewCommentDocument> comments = mapComments(hotelInternalId, reviews.comments());

        HotelReviewsDocument document = mapper.toDocument(
                hotelInternalId,
                externalHotelId,
                providerName,
                reviews,
                comments,
                createdAt,
                now
        );

        repository.save(document);

        return hotelInternalId;
    }

    public HotelReviewsDocument findByHotelInternalId(UUID hotelInternalId) {
        return repository.findById(hotelInternalId.toString())
                .orElseThrow(() -> new IllegalStateException(
                        "Hotel reviews not found for hotelInternalId=" + hotelInternalId
                ));
    }

    private List<ReviewCommentDocument> mapComments(
            UUID hotelInternalId,
            List<ReviewCommentDto> comments
    ) {
        if (comments == null || comments.isEmpty()) {
            return List.of();
        }

        List<ReviewCommentDocument> result = new ArrayList<>(comments.size());

        for (int index = 0; index < comments.size(); index++) {
            ReviewCommentDto comment = comments.get(index);
            List<String> photoS3Keys = photoStorageService.uploadReviewPhotosFromUrls(
                    hotelInternalId,
                    index,
                    comment.photos()
            );

            result.add(mapper.toComment(comment, photoS3Keys));
        }

        return List.copyOf(result);
    }
}
