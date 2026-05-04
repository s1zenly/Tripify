package com.tripify.hotels.service.model.filter;

import java.util.List;

public record ResolvedSearchFilters(
        List<String> facets,
        List<String> termsFilterIds,
        List<String> attributeFilterIds
) {

    public static ResolvedSearchFilters empty() {
        return new ResolvedSearchFilters(List.of(), List.of(), List.of());
    }

    public boolean hasFacets() {
        return !facets.isEmpty();
    }

    public boolean hasTermsFilters() {
        return !termsFilterIds.isEmpty();
    }

    public boolean hasAttributeFilters() {
        return !attributeFilterIds.isEmpty();
    }
}
