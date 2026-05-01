package com.tripify.tickets.service.model.unified;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tripify.tickets.service.provider.TicketProvider;
import lombok.Builder;

@Builder
public record ProviderInfo(
        TicketProvider code,
        String offerId,
        String proposalId,
        String agencyId
) {

    @JsonProperty(value = "name", access = JsonProperty.Access.READ_ONLY)
    public String name() {
        return code.getDisplayName();
    }
}
