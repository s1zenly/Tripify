package com.tripify.hotels.service.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BathroomsDto(
        Integer count,
        @JsonProperty("private") Boolean isPrivate
) {
}
