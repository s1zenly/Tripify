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
public class MockProviderHotel {
    private String externalId;
    private String name;
    private String url;
    private String summary;
    private String street;
    private String locality;
    private String countryCode;
    private int stars;

    private double lat;
    private double lng;

    private List<MockPhoto> photos;
    private List<MockFacility> amenities;
    private MockReviews reviewSummary;
    private List<MockRoom> rooms;
}
