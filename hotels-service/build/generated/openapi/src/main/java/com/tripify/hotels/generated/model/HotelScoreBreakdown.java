package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * HotelScoreBreakdown
 */

@JsonTypeName("HotelScore_breakdown")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-25T20:44:55.035359+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class HotelScoreBreakdown {

  private @Nullable Double price;

  private @Nullable Double rating;

  private @Nullable Double location;

  private @Nullable Double facilities;

  public HotelScoreBreakdown price(@Nullable Double price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
   */
  
  @Schema(name = "price", example = "0.8", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("price")
  public @Nullable Double getPrice() {
    return price;
  }

  public void setPrice(@Nullable Double price) {
    this.price = price;
  }

  public HotelScoreBreakdown rating(@Nullable Double rating) {
    this.rating = rating;
    return this;
  }

  /**
   * Get rating
   * @return rating
   */
  
  @Schema(name = "rating", example = "0.9", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("rating")
  public @Nullable Double getRating() {
    return rating;
  }

  public void setRating(@Nullable Double rating) {
    this.rating = rating;
  }

  public HotelScoreBreakdown location(@Nullable Double location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   * @return location
   */
  
  @Schema(name = "location", example = "0.85", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("location")
  public @Nullable Double getLocation() {
    return location;
  }

  public void setLocation(@Nullable Double location) {
    this.location = location;
  }

  public HotelScoreBreakdown facilities(@Nullable Double facilities) {
    this.facilities = facilities;
    return this;
  }

  /**
   * Get facilities
   * @return facilities
   */
  
  @Schema(name = "facilities", example = "0.75", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("facilities")
  public @Nullable Double getFacilities() {
    return facilities;
  }

  public void setFacilities(@Nullable Double facilities) {
    this.facilities = facilities;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HotelScoreBreakdown hotelScoreBreakdown = (HotelScoreBreakdown) o;
    return Objects.equals(this.price, hotelScoreBreakdown.price) &&
        Objects.equals(this.rating, hotelScoreBreakdown.rating) &&
        Objects.equals(this.location, hotelScoreBreakdown.location) &&
        Objects.equals(this.facilities, hotelScoreBreakdown.facilities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price, rating, location, facilities);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HotelScoreBreakdown {\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    facilities: ").append(toIndentedString(facilities)).append("\n");
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

