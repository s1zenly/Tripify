package com.tripify.hotels.parser.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethods {
    private CashInfo cashInfo;
    private CardsInfo cardsInfo;
}