package com.tripify.hotels.service.service;

import java.util.Comparator;
import java.util.List;

import com.tripify.hotels.generated.model.HotelFilterOption;
import com.tripify.hotels.generated.model.HotelFiltersResponse;
import com.tripify.hotels.service.model.filter.HotelFilterCatalog;
import com.tripify.hotels.service.model.filter.HotelFilterDefinition;
import com.tripify.hotels.service.model.filter.HotelFilterType;
import org.springframework.stereotype.Service;

@Service
public class HotelFilterQueryService {

    public HotelFiltersResponse getFiltersCatalog() {
        List<HotelFilterOption> options = HotelFilterCatalog.all().stream()
                .map(HotelFilterQueryService::toOption)
                .sorted(Comparator.comparing(HotelFilterOption::getLabel))
                .toList();

        return new HotelFiltersResponse().filters(options);
    }

    private static HotelFilterOption toOption(HotelFilterDefinition definition) {
        return new HotelFilterOption()
                .id(definition.id())
                .type(toApiType(definition.type()))
                .label(definition.label());
    }

    private static HotelFilterOption.TypeEnum toApiType(HotelFilterType type) {
        return switch (type) {
            case FACET -> HotelFilterOption.TypeEnum.FACET;
            case TERMS -> HotelFilterOption.TypeEnum.TERMS;
            case ATTRIBUTE -> HotelFilterOption.TypeEnum.ATTRIBUTE;
        };
    }
}
