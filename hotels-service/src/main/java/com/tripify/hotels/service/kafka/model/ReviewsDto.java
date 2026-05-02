package com.tripify.hotels.service.kafka.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record ReviewsDto(
        Integer total,
        BigDecimal rating,
        Map<Integer, Integer> reviewsHistogram,
        Map<String, Object> reviewsClasses,
        List<ReviewCommentDto> comments,
        Integer totalComments
) {
}
