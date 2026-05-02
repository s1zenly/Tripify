package com.tripify.hotels.service.kafka.model;

public record PaymentMethodsDto(
        CashInfoDto cashInfo,
        CardsInfoDto cardsInfo
) {
}
