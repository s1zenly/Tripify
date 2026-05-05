package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Occupancy
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-25T20:44:55.035359+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class Occupancy {

  private @Nullable Integer minAdults;

  private @Nullable Integer maxAdults;

  private @Nullable Integer maxChildren;

  private @Nullable Integer maxGuests;

  public Occupancy minAdults(@Nullable Integer minAdults) {
    this.minAdults = minAdults;
    return this;
  }

  /**
   * Get minAdults
   * @return minAdults
   */
  
  @Schema(name = "minAdults", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("minAdults")
  public @Nullable Integer getMinAdults() {
    return minAdults;
  }

  public void setMinAdults(@Nullable Integer minAdults) {
    this.minAdults = minAdults;
  }

  public Occupancy maxAdults(@Nullable Integer maxAdults) {
    this.maxAdults = maxAdults;
    return this;
  }

  /**
   * Get maxAdults
   * @return maxAdults
   */
  
  @Schema(name = "maxAdults", example = "2", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("maxAdults")
  public @Nullable Integer getMaxAdults() {
    return maxAdults;
  }

  public void setMaxAdults(@Nullable Integer maxAdults) {
    this.maxAdults = maxAdults;
  }

  public Occupancy maxChildren(@Nullable Integer maxChildren) {
    this.maxChildren = maxChildren;
    return this;
  }

  /**
   * Get maxChildren
   * @return maxChildren
   */
  
  @Schema(name = "maxChildren", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("maxChildren")
  public @Nullable Integer getMaxChildren() {
    return maxChildren;
  }

  public void setMaxChildren(@Nullable Integer maxChildren) {
    this.maxChildren = maxChildren;
  }

  public Occupancy maxGuests(@Nullable Integer maxGuests) {
    this.maxGuests = maxGuests;
    return this;
  }

  /**
   * Get maxGuests
   * @return maxGuests
   */
  
  @Schema(name = "maxGuests", example = "3", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("maxGuests")
  public @Nullable Integer getMaxGuests() {
    return maxGuests;
  }

  public void setMaxGuests(@Nullable Integer maxGuests) {
    this.maxGuests = maxGuests;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Occupancy occupancy = (Occupancy) o;
    return Objects.equals(this.minAdults, occupancy.minAdults) &&
        Objects.equals(this.maxAdults, occupancy.maxAdults) &&
        Objects.equals(this.maxChildren, occupancy.maxChildren) &&
        Objects.equals(this.maxGuests, occupancy.maxGuests);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minAdults, maxAdults, maxChildren, maxGuests);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Occupancy {\n");
    sb.append("    minAdults: ").append(toIndentedString(minAdults)).append("\n");
    sb.append("    maxAdults: ").append(toIndentedString(maxAdults)).append("\n");
    sb.append("    maxChildren: ").append(toIndentedString(maxChildren)).append("\n");
    sb.append("    maxGuests: ").append(toIndentedString(maxGuests)).append("\n");
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

