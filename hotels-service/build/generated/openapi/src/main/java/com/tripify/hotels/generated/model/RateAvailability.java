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
 * RateAvailability
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T01:20:13.365045+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class RateAvailability {

  private @Nullable Integer roomsLeft;

  private @Nullable Boolean soldOut;

  public RateAvailability roomsLeft(@Nullable Integer roomsLeft) {
    this.roomsLeft = roomsLeft;
    return this;
  }

  /**
   * Get roomsLeft
   * @return roomsLeft
   */
  
  @Schema(name = "roomsLeft", example = "5", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("roomsLeft")
  public @Nullable Integer getRoomsLeft() {
    return roomsLeft;
  }

  public void setRoomsLeft(@Nullable Integer roomsLeft) {
    this.roomsLeft = roomsLeft;
  }

  public RateAvailability soldOut(@Nullable Boolean soldOut) {
    this.soldOut = soldOut;
    return this;
  }

  /**
   * Get soldOut
   * @return soldOut
   */
  
  @Schema(name = "soldOut", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("soldOut")
  public @Nullable Boolean getSoldOut() {
    return soldOut;
  }

  public void setSoldOut(@Nullable Boolean soldOut) {
    this.soldOut = soldOut;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RateAvailability rateAvailability = (RateAvailability) o;
    return Objects.equals(this.roomsLeft, rateAvailability.roomsLeft) &&
        Objects.equals(this.soldOut, rateAvailability.soldOut);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomsLeft, soldOut);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RateAvailability {\n");
    sb.append("    roomsLeft: ").append(toIndentedString(roomsLeft)).append("\n");
    sb.append("    soldOut: ").append(toIndentedString(soldOut)).append("\n");
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

