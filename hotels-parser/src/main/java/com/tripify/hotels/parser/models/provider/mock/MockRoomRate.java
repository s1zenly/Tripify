package com.tripify.hotels.parser.models.provider.mock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockRoomRate {
    private String id;
    private String label;
    private List<String> tags;
    private BigDecimal baseAmount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private String currency;
    private Integer discountPercent;
    private String paymentType;
    private boolean prepay;
    private List<String> acceptedCards;
    private String meal;
    private String mealNote;
    private boolean refundable;
    private Instant freeCancelBefore;
    private String penaltyType;
    private Integer roomsLeft;
    private boolean soldOut;
    private boolean instantConfirm;
    private int loyaltyPoints;
    private List<String> perks;
}
