package com.tripify.hotels.service.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.tripify.hotels.generated.model.HotelFilterOption;
import com.tripify.hotels.generated.model.HotelFiltersResponse;
import com.tripify.hotels.service.model.filter.HotelFilterCatalog;
import com.tripify.hotels.service.model.filter.HotelFilterDefinition;
import com.tripify.hotels.service.model.filter.HotelFilterType;
import com.tripify.hotels.service.repository.contract.HotelFilterStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelFilterQueryService {

    private final HotelFilterStatsRepository filterStatsRepository;

    public HotelFiltersResponse getAvailableFilters(String country, String city) {
        Map<String, Integer> facetCounts = filterStatsRepository.countFacetUsage(country, city);

        List<HotelFilterOption> options = new ArrayList<>();

        for (HotelFilterDefinition definition : HotelFilterCatalog.all()) {
            int count = resolveCount(definition, facetCounts, country, city);
            if (count <= 0) {
                continue;
            }

            options.add(new HotelFilterOption()
                    .id(definition.id())
                    .type(toApiType(definition.type()))
                    .label(definition.label())
                    .count(count));
        }

        options.sort(Comparator.comparing(HotelFilterOption::getLabel));

        return new HotelFiltersResponse()
                .country(country)
                .city(city)
                .filters(options);
    }

    private int resolveCount(
            HotelFilterDefinition definition,
            Map<String, Integer> facetCounts,
            String country,
            String city
    ) {
        return switch (definition.type()) {
            case FACET -> facetCounts.getOrDefault(definition.id(), 0);
            case TERMS -> definition.id().equals(HotelFilterCatalog.FREE_CANCELLATION)
                    ? filterStatsRepository.countFreeCancellation(country, city)
                    : 0;
            case ATTRIBUTE -> filterStatsRepository.countAttributeFilter(country, city, definition);
        };
    }

    private static HotelFilterOption.TypeEnum toApiType(HotelFilterType type) {
        return switch (type) {
            case FACET -> HotelFilterOption.TypeEnum.FACET;
            case TERMS -> HotelFilterOption.TypeEnum.TERMS;
            case ATTRIBUTE -> HotelFilterOption.TypeEnum.ATTRIBUTE;
        };
    }
}
