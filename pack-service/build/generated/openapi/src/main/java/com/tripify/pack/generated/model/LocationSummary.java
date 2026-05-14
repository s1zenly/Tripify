package com.tripify.pack.generated.model;

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
 * LocationSummary
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class LocationSummary {

  private String city;

  private String airportCode;

  public LocationSummary() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public LocationSummary(String city, String airportCode) {
    this.city = city;
    this.airportCode = airportCode;
  }

  public LocationSummary city(String city) {
    this.city = city;
    return this;
  }

  /**
   * City IATA code
   * @return city
   */
  @NotNull @Size(min = 3, max = 3) 
  @Schema(name = "city", example = "MOW", description = "City IATA code", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("city")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public LocationSummary airportCode(String airportCode) {
    this.airportCode = airportCode;
    return this;
  }

  /**
   * Airport IATA code
   * @return airportCode
   */
  @NotNull @Size(min = 3, max = 3) 
  @Schema(name = "airport_code", example = "SVO", description = "Airport IATA code", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("airport_code")
  public String getAirportCode() {
    return airportCode;
  }

  public void setAirportCode(String airportCode) {
    this.airportCode = airportCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocationSummary locationSummary = (LocationSummary) o;
    return Objects.equals(this.city, locationSummary.city) &&
        Objects.equals(this.airportCode, locationSummary.airportCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(city, airportCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocationSummary {\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    airportCode: ").append(toIndentedString(airportCode)).append("\n");
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

