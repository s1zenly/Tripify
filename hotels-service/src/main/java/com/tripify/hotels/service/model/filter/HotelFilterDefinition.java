package com.tripify.hotels.service.model.filter;

public record HotelFilterDefinition(
        String id,
        HotelFilterType type,
        String label,
        HotelAttributeRule attributeRule
) {

    public HotelFilterDefinition(String id, HotelFilterType type, String label) {
        this(id, type, label, null);
    }
}
