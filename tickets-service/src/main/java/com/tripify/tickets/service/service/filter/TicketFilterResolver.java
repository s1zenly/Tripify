package com.tripify.tickets.service.service.filter;

import com.tripify.tickets.service.exception.BadRequestException;
import com.tripify.tickets.service.model.filter.TicketFilterCatalog;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
public class TicketFilterResolver {

    public List<String> resolve(List<String> filters) {
        if (filters == null || filters.isEmpty()) {
            return List.of();
        }

        Set<String> resolved = new LinkedHashSet<>();
        for (String filter : filters) {
            if (filter == null || filter.isBlank()) {
                continue;
            }
            String normalized = TicketFilterCatalog.normalizeId(filter);
            TicketFilterCatalog.find(normalized)
                    .orElseThrow(() -> new BadRequestException("Unknown filter: " + filter));
            resolved.add(normalized);
        }

        return List.copyOf(resolved);
    }
}
