package com.tripify.hotels.service.model.documents;

import java.util.List;

public record RoomDocument(
        String roomId,
        String providerRoomId,
        String name,
        String roomType,
        String description,
        AreaDocument area,
        Integer floor,
        Boolean smokingAllowed,
        List<String> views,
        List<RoomPhotoDocument> photos,
        List<BedDocument> beds,
        BathroomsDocument bathrooms,
        OccupancyDocument occupancy,
        List<String> amenities,
        List<String> accessibility,
        List<RateDocument> rates
) {
}
