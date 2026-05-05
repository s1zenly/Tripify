package com.tripify.hotels.parser.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    private String roomId;
    private String providerRoomId;
    private String name;
    private String roomType;
    private String description;
    private RoomArea area;
    private Integer floor;
    private Boolean smokingAllowed;
    private List<String> views;
    private List<Photo> photos;
    private List<Bed> beds;
    private Bathrooms bathrooms;
    private RoomOccupancy occupancy;
    private List<String> amenities;
    private List<String> accessibility;
    private List<RoomRate> rates;
}
