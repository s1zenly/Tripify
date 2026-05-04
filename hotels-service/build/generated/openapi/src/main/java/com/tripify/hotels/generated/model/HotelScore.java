package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.hotels.generated.model.HotelScoreBreakdown;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * HotelScore
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-24T20:52:08.294927+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class HotelScore {

  private @Nullable Double _final;

  private @Nullable HotelScoreBreakdown breakdown;

  public HotelScore _final(@Nullable Double _final) {
    this._final = _final;
    return this;
  }

  /**
   * Get _final
   * @return _final
   */
  
  @Schema(name = "final", example = "0.87", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("final")
  public @Nullable Double getFinal() {
    return _final;
  }

  public void setFinal(@Nullable Double _final) {
    this._final = _final;
  }

  public HotelScore breakdown(@Nullable HotelScoreBreakdown breakdown) {
    this.breakdown = breakdown;
    return this;
  }

  /**
   * Get breakdown
   * @return breakdown
   */
  @Valid 
  @Schema(name = "breakdown", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("breakdown")
  public @Nullable HotelScoreBreakdown getBreakdown() {
    return breakdown;
  }

  public void setBreakdown(@Nullable HotelScoreBreakdown breakdown) {
    this.breakdown = breakdown;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HotelScore hotelScore = (HotelScore) o;
    return Objects.equals(this._final, hotelScore._final) &&
        Objects.equals(this.breakdown, hotelScore.breakdown);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_final, breakdown);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HotelScore {\n");
    sb.append("    _final: ").append(toIndentedString(_final)).append("\n");
    sb.append("    breakdown: ").append(toIndentedString(breakdown)).append("\n");
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

