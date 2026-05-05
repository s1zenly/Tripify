package com.tripify.hotels.service.model.documents;

public record RoomPhotoDocument(
        String s3Key,
        Integer order
) {
}
