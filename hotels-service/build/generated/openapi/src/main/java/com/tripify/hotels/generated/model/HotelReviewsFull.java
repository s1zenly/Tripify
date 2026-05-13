package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.hotels.generated.model.ReviewComment;
import com.tripify.hotels.generated.model.ReviewsClasses;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * HotelReviewsFull
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-30T21:24:42.207813975Z[Etc/UTC]", comments = "Generator version: 7.16.0")
public class HotelReviewsFull {

  private @Nullable Integer total;

  private @Nullable Double rating;

  private @Nullable ReviewsClasses reviewsClasses;

  @Valid
  private Map<String, Integer> reviewsHistogram = new HashMap<>();

  @Valid
  private List<@Valid ReviewComment> comments = new ArrayList<>();

  private @Nullable Integer totalComments;

  public HotelReviewsFull total(@Nullable Integer total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   * @return total
   */
  
  @Schema(name = "total", example = "928", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("total")
  public @Nullable Integer getTotal() {
    return total;
  }

  public void setTotal(@Nullable Integer total) {
    this.total = total;
  }

  public HotelReviewsFull rating(@Nullable Double rating) {
    this.rating = rating;
    return this;
  }

  /**
   * Get rating
   * @return rating
   */
  
  @Schema(name = "rating", example = "3.7", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("rating")
  public @Nullable Double getRating() {
    return rating;
  }

  public void setRating(@Nullable Double rating) {
    this.rating = rating;
  }

  public HotelReviewsFull reviewsClasses(@Nullable ReviewsClasses reviewsClasses) {
    this.reviewsClasses = reviewsClasses;
    return this;
  }

  /**
   * Get reviewsClasses
   * @return reviewsClasses
   */
  @Valid 
  @Schema(name = "reviewsClasses", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reviewsClasses")
  public @Nullable ReviewsClasses getReviewsClasses() {
    return reviewsClasses;
  }

  public void setReviewsClasses(@Nullable ReviewsClasses reviewsClasses) {
    this.reviewsClasses = reviewsClasses;
  }

  public HotelReviewsFull reviewsHistogram(Map<String, Integer> reviewsHistogram) {
    this.reviewsHistogram = reviewsHistogram;
    return this;
  }

  public HotelReviewsFull putReviewsHistogramItem(String key, Integer reviewsHistogramItem) {
    if (this.reviewsHistogram == null) {
      this.reviewsHistogram = new HashMap<>();
    }
    this.reviewsHistogram.put(key, reviewsHistogramItem);
    return this;
  }

  /**
   * Get reviewsHistogram
   * @return reviewsHistogram
   */
  
  @Schema(name = "reviewsHistogram", example = "{10=120, 9=80, 8=45, 7=20}", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reviewsHistogram")
  public Map<String, Integer> getReviewsHistogram() {
    return reviewsHistogram;
  }

  public void setReviewsHistogram(Map<String, Integer> reviewsHistogram) {
    this.reviewsHistogram = reviewsHistogram;
  }

  public HotelReviewsFull comments(List<@Valid ReviewComment> comments) {
    this.comments = comments;
    return this;
  }

  public HotelReviewsFull addCommentsItem(ReviewComment commentsItem) {
    if (this.comments == null) {
      this.comments = new ArrayList<>();
    }
    this.comments.add(commentsItem);
    return this;
  }

  /**
   * Get comments
   * @return comments
   */
  @Valid 
  @Schema(name = "comments", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("comments")
  public List<@Valid ReviewComment> getComments() {
    return comments;
  }

  public void setComments(List<@Valid ReviewComment> comments) {
    this.comments = comments;
  }

  public HotelReviewsFull totalComments(@Nullable Integer totalComments) {
    this.totalComments = totalComments;
    return this;
  }

  /**
   * Get totalComments
   * @return totalComments
   */
  
  @Schema(name = "totalComments", example = "300", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalComments")
  public @Nullable Integer getTotalComments() {
    return totalComments;
  }

  public void setTotalComments(@Nullable Integer totalComments) {
    this.totalComments = totalComments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HotelReviewsFull hotelReviewsFull = (HotelReviewsFull) o;
    return Objects.equals(this.total, hotelReviewsFull.total) &&
        Objects.equals(this.rating, hotelReviewsFull.rating) &&
        Objects.equals(this.reviewsClasses, hotelReviewsFull.reviewsClasses) &&
        Objects.equals(this.reviewsHistogram, hotelReviewsFull.reviewsHistogram) &&
        Objects.equals(this.comments, hotelReviewsFull.comments) &&
        Objects.equals(this.totalComments, hotelReviewsFull.totalComments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(total, rating, reviewsClasses, reviewsHistogram, comments, totalComments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HotelReviewsFull {\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
    sb.append("    reviewsClasses: ").append(toIndentedString(reviewsClasses)).append("\n");
    sb.append("    reviewsHistogram: ").append(toIndentedString(reviewsHistogram)).append("\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
    sb.append("    totalComments: ").append(toIndentedString(totalComments)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

