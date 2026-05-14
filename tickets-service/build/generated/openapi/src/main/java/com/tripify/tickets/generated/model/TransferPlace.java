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
 * TransferPlace
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T01:47:04.875924+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class TransferPlace {

  private @Nullable String airportCode;

  private @Nullable String airportName;

  private @Nullable Integer layoverMinutes;

  public TransferPlace airportCode(@Nullable String airportCode) {
    this.airportCode = airportCode;
    return this;
  }

  /**
   * Get airportCode
   * @return airportCode
   */
  
  @Schema(name = "airport_code", example = "IST", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("airport_code")
  public @Nullable String getAirportCode() {
    return airportCode;
  }

  public void setAirportCode(@Nullable String airportCode) {
    this.airportCode = airportCode;
  }

  public TransferPlace airportName(@Nullable String airportName) {
    this.airportName = airportName;
    return this;
  }

  /**
   * Get airportName
   * @return airportName
   */
  
  @Schema(name = "airport_name", example = "Istanbul Airport", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("airport_name")
  public @Nullable String getAirportName() {
    return airportName;
  }

  public void setAirportName(@Nullable String airportName) {
    this.airportName = airportName;
  }

  public TransferPlace layoverMinutes(@Nullable Integer layoverMinutes) {
    this.layoverMinutes = layoverMinutes;
    return this;
  }

  /**
   * Get layoverMinutes
   * minimum: 0
   * @return layoverMinutes
   */
  @Min(0) 
  @Schema(name = "layover_minutes", example = "120", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("layover_minutes")
  public @Nullable Integer getLayoverMinutes() {
    return layoverMinutes;
  }

  public void setLayoverMinutes(@Nullable Integer layoverMinutes) {
    this.layoverMinutes = layoverMinutes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransferPlace transferPlace = (TransferPlace) o;
    return Objects.equals(this.airportCode, transferPlace.airportCode) &&
        Objects.equals(this.airportName, transferPlace.airportName) &&
        Objects.equals(this.layoverMinutes, transferPlace.layoverMinutes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(airportCode, airportName, layoverMinutes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransferPlace {\n");
    sb.append("    airportCode: ").append(toIndentedString(airportCode)).append("\n");
    sb.append("    airportName: ").append(toIndentedString(airportName)).append("\n");
    sb.append("    layoverMinutes: ").append(toIndentedString(layoverMinutes)).append("\n");
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

