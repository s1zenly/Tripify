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
 * LocationDetail
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class LocationDetail {

  private String city;

  private String airportCode;

  private String airportName;

  private @Nullable String terminal;

  public LocationDetail() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public LocationDetail(String city, String airportCode, String airportName) {
    this.city = city;
    this.airportCode = airportCode;
    this.airportName = airportName;
  }

  public LocationDetail city(String city) {
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

  public LocationDetail airportCode(String airportCode) {
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

  public LocationDetail airportName(String airportName) {
    this.airportName = airportName;
    return this;
  }

  /**
   * Get airportName
   * @return airportName
   */
  @NotNull 
  @Schema(name = "airport_name", example = "Sheremetyevo", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("airport_name")
  public String getAirportName() {
    return airportName;
  }

  public void setAirportName(String airportName) {
    this.airportName = airportName;
  }

  public LocationDetail terminal(@Nullable String terminal) {
    this.terminal = terminal;
    return this;
  }

  /**
   * Get terminal
   * @return terminal
   */
  
  @Schema(name = "terminal", example = "C", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("terminal")
  public @Nullable String getTerminal() {
    return terminal;
  }

  public void setTerminal(@Nullable String terminal) {
    this.terminal = terminal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocationDetail locationDetail = (LocationDetail) o;
    return Objects.equals(this.city, locationDetail.city) &&
        Objects.equals(this.airportCode, locationDetail.airportCode) &&
        Objects.equals(this.airportName, locationDetail.airportName) &&
        Objects.equals(this.terminal, locationDetail.terminal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(city, airportCode, airportName, terminal);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocationDetail {\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    airportCode: ").append(toIndentedString(airportCode)).append("\n");
    sb.append("    airportName: ").append(toIndentedString(airportName)).append("\n");
    sb.append("    terminal: ").append(toIndentedString(terminal)).append("\n");
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

