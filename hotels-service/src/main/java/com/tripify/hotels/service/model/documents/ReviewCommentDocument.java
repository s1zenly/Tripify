package com.tripify.hotels.service.model.documents;

import java.time.LocalDate;
import java.util.List;

public record ReviewCommentDocument(
        String author,
        String country,
        String vacationType,
        Double rating,
        String goodPart,
        String badPart,
        String commonText,
        LocalDate reviewDate,
        List<String> photoS3Keys
) {
}
