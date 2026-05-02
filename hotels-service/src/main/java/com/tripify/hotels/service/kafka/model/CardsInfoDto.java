package com.tripify.hotels.service.kafka.model;

import java.util.List;

public record CardsInfoDto(
        List<String> cardTypes,
        Boolean card
) {
}
