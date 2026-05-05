package com.tripify.hotels.parser.models.provider.mock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockRoom {
    private String id;
    private String label;
    private String category;
    private String info;
    private double areaSqm;
    private int floor;
    private boolean smoking;
    private List<String> views;
    private List<MockPhoto> photos;
    private List<MockBed> beds;
    private int bathroomCount;
    private boolean privateBathroom;
    private int maxAdults;
    private int maxChildren;
    private int maxGuests;
    private List<String> amenityCodes;
    private List<String> accessibilityCodes;
    private List<MockRoomRate> rates;
}
