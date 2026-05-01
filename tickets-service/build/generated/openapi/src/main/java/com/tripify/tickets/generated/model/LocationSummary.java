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
 * LocationSummary
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-29T20:25:02.204846+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class LocationSummary {

  private String cityCode;

  private String cityName;

  private String airportCode;

  public LocationSummary() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public LocationSummary(String cityCode, String cityName, String airportCode) {
    this.cityCode = cityCode;
    this.cityName = cityName;
    this.airportCode = airportCode;
  }

  public LocationSummary cityCode(String cityCode) {
    this.cityCode = cityCode;
    return this;
  }

  /**
   * Get cityCode
   * @return cityCode
   */
  @NotNull @Size(min = 3, max = 3) 
  @Schema(name = "city_code", example = "MOW", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("city_code")
  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  public LocationSummary cityName(String cityName) {
    this.cityName = cityName;
    return this;
  }

  /**
   * Get cityName
   * @return cityName
   */
  @NotNull 
  @Schema(name = "city_name", example = "Moscow", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("city_name")
  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public LocationSummary airportCode(String airportCode) {
    this.airportCode = airportCode;
    return this;
  }

  /**
   * Get airportCode
   * @return airportCode
   */
  @NotNull @Size(min = 3, max = 3) 
  @Schema(name = "airport_code", example = "SVO", requiredMode = Schema.RequiredMode.REQUIRED)
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
    return Objects.equals(this.cityCode, locationSummary.cityCode) &&
        Objects.equals(this.cityName, locationSummary.cityName) &&
        Objects.equals(this.airportCode, locationSummary.airportCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cityCode, cityName, airportCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocationSummary {\n");
    sb.append("    cityCode: ").append(toIndentedString(cityCode)).append("\n");
    sb.append("    cityName: ").append(toIndentedString(cityName)).append("\n");
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

