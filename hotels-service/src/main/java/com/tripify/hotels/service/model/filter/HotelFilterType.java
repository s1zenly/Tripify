package com.tripify.hotels.service.model.filter;

/**
 * FACET — булевый фасет в {@code hotel_search_facets} (удобства, у моря, pet friendly…).<br>
 * TERMS — условие в {@code hotel_terms_placement}.<br>
 * ATTRIBUTE — предикат по колонкам {@code hotels} (рейтинг, звёзды).
 */
public enum HotelFilterType {
    FACET,
    TERMS,
    ATTRIBUTE
}
