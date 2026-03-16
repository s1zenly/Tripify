package com.tripify.hotels.parser.models.provider.mock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockReviews {
    private int count;
    private double score;
    private Map<String, Integer> distribution;
    private List<MockComment> items;
}
