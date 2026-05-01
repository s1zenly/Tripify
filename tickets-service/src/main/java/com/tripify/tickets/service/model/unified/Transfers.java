package com.tripify.tickets.service.model.unified;

import lombok.Builder;

import java.util.List;

@Builder
public record Transfers(
        int count,
        boolean isDirect,
        boolean hasOvernight,
        boolean requiresAirportChange,
        boolean requiresSelfTransfer,
        List<TransferPlace> places
) {
}
