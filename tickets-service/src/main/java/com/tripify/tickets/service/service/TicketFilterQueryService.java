package com.tripify.tickets.service.service;

import com.tripify.tickets.generated.model.TicketFilterOption;
import com.tripify.tickets.generated.model.TicketFiltersResponse;
import com.tripify.tickets.service.model.filter.TicketFilterCatalog;
import com.tripify.tickets.service.model.filter.TicketFilterDefinition;
import com.tripify.tickets.service.model.filter.TicketFilterType;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TicketFilterQueryService {

    public TicketFiltersResponse getFiltersCatalog() {
        List<TicketFilterOption> options = TicketFilterCatalog.all().stream()
                .map(TicketFilterQueryService::toOption)
                .sorted(Comparator.comparing(TicketFilterOption::getLabel))
                .toList();

        return new TicketFiltersResponse().filters(options);
    }

    private static TicketFilterOption toOption(TicketFilterDefinition definition) {
        return new TicketFilterOption()
                .id(definition.id())
                .type(toApiType(definition.type()))
                .label(definition.label());
    }

    private static TicketFilterOption.TypeEnum toApiType(TicketFilterType type) {
        return switch (type) {
            case FACET -> TicketFilterOption.TypeEnum.FACET;
            case TERMS -> TicketFilterOption.TypeEnum.TERMS;
        };
    }
}
