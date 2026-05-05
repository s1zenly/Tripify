package com.tripify.hotels.parser.models.common;

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
    private boolean petFriendly;
    private boolean partyFriendly;
    private String ageRestriction;
    private String additionalInfo;
}
