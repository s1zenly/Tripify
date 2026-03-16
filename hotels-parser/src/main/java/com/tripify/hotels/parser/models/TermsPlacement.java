package com.tripify.hotels.parser.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TermsPlacement {
    private CheckInOut checkIn;
    private CheckInOut checkOut;
    private boolean cancellation;

    private RefundRule refundRule;

    private boolean smoking;
    private boolean petFriendly;
    private boolean partyFriendly;
    private String ageRestriction;
    private String additionalInfo;
}
