package com.tripify.hotels.service.kafka.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ReviewCommentDto(
        String author,
        String country,
        String vacationType,
        BigDecimal rating,
        String goodPart,
        String badPart,
        String commonText,
        LocalDate reviewDate,
        List<PhotoDto> photos
) {
}
