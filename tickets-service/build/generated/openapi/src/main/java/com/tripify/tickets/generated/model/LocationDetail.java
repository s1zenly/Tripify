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
 * LocationDetail
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-29T20:25:02.204846+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class LocationDetail {

  private String cityCode;

  private String cityName;

  private String airportCode;

  private String airportName;

  private @Nullable String terminal;

  public LocationDetail() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public LocationDetail(String cityCode, String cityName, String airportCode, String airportName) {
    this.cityCode = cityCode;
    this.cityName = cityName;
    this.airportCode = airportCode;
    this.airportName = airportName;
  }

  public LocationDetail cityCode(String cityCode) {
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

  public LocationDetail cityName(String cityName) {
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

  public LocationDetail airportCode(String airportCode) {
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
    return Objects.equals(this.cityCode, locationDetail.cityCode) &&
        Objects.equals(this.cityName, locationDetail.cityName) &&
        Objects.equals(this.airportCode, locationDetail.airportCode) &&
        Objects.equals(this.airportName, locationDetail.airportName) &&
        Objects.equals(this.terminal, locationDetail.terminal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cityCode, cityName, airportCode, airportName, terminal);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocationDetail {\n");
    sb.append("    cityCode: ").append(toIndentedString(cityCode)).append("\n");
    sb.append("    cityName: ").append(toIndentedString(cityName)).append("\n");
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

