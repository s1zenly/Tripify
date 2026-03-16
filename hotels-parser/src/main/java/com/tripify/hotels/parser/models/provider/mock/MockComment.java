package com.tripify.hotels.parser.models.provider.mock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockComment {
    private String userName;
    private String userCountry;
    private String tripKind;
    private double score;
    private String pros;
    private String cons;
    private String text;
    private LocalDate date;
    private List<MockPhoto> images;
}
