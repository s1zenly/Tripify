package com.tripify.hotels.parser.models.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reviews {
    private int total;
    private double rating;

    private Map<String, Integer> reviewsHistogram;
    private Map<String, Double> reviewsClasses;
    private List<Comment> comments;

    private int totalComments;
}
