package com.tripify.hotels.service.model.documents;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hotel_rooms")
public record HotelRoomsDocument(
        @Id
        String id,
        List<RoomDocument> rooms,
        Instant createdAt,
        Instant updatedAt
) {
}
