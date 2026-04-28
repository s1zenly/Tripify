package com.tripify.hotels.parser.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {
    private long hid;
    private String title;
    private String link;
    private String description;
    private String address;
    private String city;
    private String country;
    private String currency;
    private BigDecimal price;
    private int hotelClass;

    private GpsCoordinates gpsCoordinates;
    private NearbyPlaces nearbyPlaces;
    private Reviews reviews;
    private TermsPlacement termsPlacement;
    private PaymentMethods paymentMethods;

    private List<Facility> facilities;
}