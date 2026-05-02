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
 * NearbyPlace
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-20T23:06:41.504250+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class NearbyPlace {

  private String title;

  private Double distance;

  private String unit;

  public NearbyPlace() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public NearbyPlace(String title, Double distance, String unit) {
    this.title = title;
    this.distance = distance;
    this.unit = unit;
  }

  public NearbyPlace title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
   */
  @NotNull 
  @Schema(name = "title", example = "Pool Bar", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public NearbyPlace distance(Double distance) {
    this.distance = distance;
    return this;
  }

  /**
   * Get distance
   * @return distance
   */
  @NotNull 
  @Schema(name = "distance", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("distance")
  public Double getDistance() {
    return distance;
  }

  public void setDistance(Double distance) {
    this.distance = distance;
  }

  public NearbyPlace unit(String unit) {
    this.unit = unit;
    return this;
  }

  /**
   * Get unit
   * @return unit
   */
  @NotNull 
  @Schema(name = "unit", example = "km", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("unit")
  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NearbyPlace nearbyPlace = (NearbyPlace) o;
    return Objects.equals(this.title, nearbyPlace.title) &&
        Objects.equals(this.distance, nearbyPlace.distance) &&
        Objects.equals(this.unit, nearbyPlace.unit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, distance, unit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NearbyPlace {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    distance: ").append(toIndentedString(distance)).append("\n");
    sb.append("    unit: ").append(toIndentedString(unit)).append("\n");
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

