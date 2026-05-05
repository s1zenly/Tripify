package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.hotels.generated.model.HotelFilterOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * HotelFiltersResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-25T20:44:55.035359+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class HotelFiltersResponse {

  private String country;

  private String city;

  @Valid
  private List<@Valid HotelFilterOption> filters = new ArrayList<>();

  public HotelFiltersResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public HotelFiltersResponse(String country, String city, List<@Valid HotelFilterOption> filters) {
    this.country = country;
    this.city = city;
    this.filters = filters;
  }

  public HotelFiltersResponse country(String country) {
    this.country = country;
    return this;
  }

  /**
   * Get country
   * @return country
   */
  @NotNull @Size(min = 2, max = 2) 
  @Schema(name = "country", example = "AE", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("country")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public HotelFiltersResponse city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Get city
   * @return city
   */
  @NotNull @Size(min = 1) 
  @Schema(name = "city", example = "Dubai", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("city")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public HotelFiltersResponse filters(List<@Valid HotelFilterOption> filters) {
    this.filters = filters;
    return this;
  }

  public HotelFiltersResponse addFiltersItem(HotelFilterOption filtersItem) {
    if (this.filters == null) {
      this.filters = new ArrayList<>();
    }
    this.filters.add(filtersItem);
    return this;
  }

  /**
   * Доступные фильтры с количеством отелей (только с count > 0)
   * @return filters
   */
  @NotNull @Valid 
  @Schema(name = "filters", description = "Доступные фильтры с количеством отелей (только с count > 0)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("filters")
  public List<@Valid HotelFilterOption> getFilters() {
    return filters;
  }

  public void setFilters(List<@Valid HotelFilterOption> filters) {
    this.filters = filters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HotelFiltersResponse hotelFiltersResponse = (HotelFiltersResponse) o;
    return Objects.equals(this.country, hotelFiltersResponse.country) &&
        Objects.equals(this.city, hotelFiltersResponse.city) &&
        Objects.equals(this.filters, hotelFiltersResponse.filters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(country, city, filters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HotelFiltersResponse {\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    filters: ").append(toIndentedString(filters)).append("\n");
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

