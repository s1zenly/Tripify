package com.tripify.tickets.generated.model;

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
 * BaggageAllowance
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-29T20:25:02.204846+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class BaggageAllowance {

  private Boolean included;

  private @Nullable Integer pieces;

  private @Nullable Integer weightKg;

  public BaggageAllowance() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public BaggageAllowance(Boolean included) {
    this.included = included;
  }

  public BaggageAllowance included(Boolean included) {
    this.included = included;
    return this;
  }

  /**
   * Get included
   * @return included
   */
  @NotNull 
  @Schema(name = "included", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("included")
  public Boolean getIncluded() {
    return included;
  }

  public void setIncluded(Boolean included) {
    this.included = included;
  }

  public BaggageAllowance pieces(@Nullable Integer pieces) {
    this.pieces = pieces;
    return this;
  }

  /**
   * Get pieces
   * minimum: 0
   * @return pieces
   */
  @Min(0) 
  @Schema(name = "pieces", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pieces")
  public @Nullable Integer getPieces() {
    return pieces;
  }

  public void setPieces(@Nullable Integer pieces) {
    this.pieces = pieces;
  }

  public BaggageAllowance weightKg(@Nullable Integer weightKg) {
    this.weightKg = weightKg;
    return this;
  }

  /**
   * Get weightKg
   * minimum: 0
   * @return weightKg
   */
  @Min(0) 
  @Schema(name = "weight_kg", example = "23", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("weight_kg")
  public @Nullable Integer getWeightKg() {
    return weightKg;
  }

  public void setWeightKg(@Nullable Integer weightKg) {
    this.weightKg = weightKg;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaggageAllowance baggageAllowance = (BaggageAllowance) o;
    return Objects.equals(this.included, baggageAllowance.included) &&
        Objects.equals(this.pieces, baggageAllowance.pieces) &&
        Objects.equals(this.weightKg, baggageAllowance.weightKg);
  }

  @Override
  public int hashCode() {
    return Objects.hash(included, pieces, weightKg);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BaggageAllowance {\n");
    sb.append("    included: ").append(toIndentedString(included)).append("\n");
    sb.append("    pieces: ").append(toIndentedString(pieces)).append("\n");
    sb.append("    weightKg: ").append(toIndentedString(weightKg)).append("\n");
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

