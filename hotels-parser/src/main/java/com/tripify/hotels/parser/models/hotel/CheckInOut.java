package com.tripify.hotels.parser.models.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInOut {
    private String afterTime;
    private String beforeTime;
    private String timezone;
}
