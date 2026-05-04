package com.tripify.hotels.service.service.filter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.tripify.hotels.service.exception.BadRequestException;
import com.tripify.hotels.service.model.filter.HotelFilterCatalog;
import com.tripify.hotels.service.model.filter.HotelFilterDefinition;
import com.tripify.hotels.service.model.filter.HotelFilterType;
import com.tripify.hotels.service.model.filter.ResolvedSearchFilters;
import org.springframework.stereotype.Component;

@Component
public class HotelFilterResolver {

    public ResolvedSearchFilters resolve(List<String> filters) {
        if (filters == null || filters.isEmpty()) {
            return ResolvedSearchFilters.empty();
        }

        Set<String> requested = new LinkedHashSet<>();
        filters.stream()
                .filter(value -> value != null && !value.isBlank())
                .map(HotelFilterCatalog::normalizeId)
                .forEach(requested::add);

        if (requested.isEmpty()) {
            return ResolvedSearchFilters.empty();
        }

        List<String> facets = new ArrayList<>();
        List<String> termsFilterIds = new ArrayList<>();
        List<String> attributeFilterIds = new ArrayList<>();

        for (String filterId : requested) {
            HotelFilterDefinition definition = HotelFilterCatalog.find(filterId)
                    .orElseThrow(() -> new BadRequestException("Unknown filter: " + filterId));

            switch (definition.type()) {
                case FACET -> facets.add(definition.id());
                case TERMS -> termsFilterIds.add(definition.id());
                case ATTRIBUTE -> attributeFilterIds.add(definition.id());
            }
        }

        return new ResolvedSearchFilters(
                List.copyOf(facets),
                List.copyOf(termsFilterIds),
                List.copyOf(attributeFilterIds)
        );
    }
}
