package com.tripify.hotels.service.kafka.model;

import java.util.List;

public record RoomDto(
        String roomId,
        String providerRoomId,
        String name,
        String roomType,
        String description,
        RoomAreaDto area,
        Integer floor,
        Boolean smokingAllowed,
        List<String> views,
        List<RoomPhotoDto> photos,
        List<BedDto> beds,
        BathroomsDto bathrooms,
        OccupancyDto occupancy,
        List<String> amenities,
        List<String> accessibility,
        List<RateDto> rates
) {
}
