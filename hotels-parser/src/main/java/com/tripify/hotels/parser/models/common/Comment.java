package com.tripify.hotels.parser.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    private String author;
    private String country;
    private String vacationType;
    private double rating;
    private String goodPart;
    private String badPart;
    private String commonText;
    private LocalDate reviewDate;

    private List<Photo> photos;
}
