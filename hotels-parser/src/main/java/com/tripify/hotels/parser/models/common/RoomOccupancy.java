package com.tripify.hotels.parser.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomOccupancy {
    private Integer minAdults;
    private int maxAdults;
    private Integer maxChildren;
    private int maxGuests;
}
