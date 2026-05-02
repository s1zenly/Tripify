package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.hotels.generated.model.ReviewsClasses;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * HotelReviewsShort
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-20T23:06:41.504250+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class HotelReviewsShort {

  private @Nullable Integer total;

  private @Nullable Double rating;

  private @Nullable ReviewsClasses reviewsClasses;

  public HotelReviewsShort total(@Nullable Integer total) {
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

  public HotelReviewsShort rating(@Nullable Double rating) {
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

  public HotelReviewsShort reviewsClasses(@Nullable ReviewsClasses reviewsClasses) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HotelReviewsShort hotelReviewsShort = (HotelReviewsShort) o;
    return Objects.equals(this.total, hotelReviewsShort.total) &&
        Objects.equals(this.rating, hotelReviewsShort.rating) &&
        Objects.equals(this.reviewsClasses, hotelReviewsShort.reviewsClasses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(total, rating, reviewsClasses);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HotelReviewsShort {\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
    sb.append("    reviewsClasses: ").append(toIndentedString(reviewsClasses)).append("\n");
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

