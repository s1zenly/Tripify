package com.tripify.hotels.parser.models.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardsInfo {
    private boolean isCard;
    private List<String> cardTypes;
}
