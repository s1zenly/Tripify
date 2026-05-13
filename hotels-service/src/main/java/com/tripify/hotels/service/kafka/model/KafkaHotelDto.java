package com.tripify.hotels.service.kafka.model;

import java.util.List;
import java.util.Map;

public record KafkaHotelDto(
        Long hid,
        String title,
        String link,
        String description,
        String address,
        String city,
        String country,
        Integer hotelClass,
        GpsCoordinatesDto gpsCoordinates,
        Map<String, List<NearbyPlaceDto>> nearbyPlaces,
        ReviewsDto reviews,
        TermsPlacementDto termsPlacement,
        List<FacilityDto> facilities,
        List<PhotoDto> photos,
        List<RoomDto> rooms,
        HotelScoreDto score
) {
}
