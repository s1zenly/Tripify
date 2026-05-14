package com.tripify.pack.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PackSearchContext
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class PackSearchContext {

  private String originCountry;

  private String originCity;

  private String destinationCountry;

  private String destinationCity;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateFrom;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateTo;

  private String currency;

  private Integer adults;

  private @Nullable Integer children;

  private @Nullable Long budget;

  @Valid
  private List<String> filters = new ArrayList<>();

  public PackSearchContext() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PackSearchContext(String originCountry, String originCity, String destinationCountry, String destinationCity, LocalDate dateFrom, LocalDate dateTo, String currency, Integer adults) {
    this.originCountry = originCountry;
    this.originCity = originCity;
    this.destinationCountry = destinationCountry;
    this.destinationCity = destinationCity;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.currency = currency;
    this.adults = adults;
  }

  public PackSearchContext originCountry(String originCountry) {
    this.originCountry = originCountry;
    return this;
  }

  /**
   * Get originCountry
   * @return originCountry
   */
  @NotNull @Size(min = 2, max = 2) 
  @Schema(name = "origin_country", example = "RU", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("origin_country")
  public String getOriginCountry() {
    return originCountry;
  }

  public void setOriginCountry(String originCountry) {
    this.originCountry = originCountry;
  }

  public PackSearchContext originCity(String originCity) {
    this.originCity = originCity;
    return this;
  }

  /**
   * Get originCity
   * @return originCity
   */
  @NotNull @Size(min = 3, max = 3) 
  @Schema(name = "origin_city", example = "MOW", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("origin_city")
  public String getOriginCity() {
    return originCity;
  }

  public void setOriginCity(String originCity) {
    this.originCity = originCity;
  }

  public PackSearchContext destinationCountry(String destinationCountry) {
    this.destinationCountry = destinationCountry;
    return this;
  }

  /**
   * Get destinationCountry
   * @return destinationCountry
   */
  @NotNull @Size(min = 2, max = 2) 
  @Schema(name = "destination_country", example = "TH", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("destination_country")
  public String getDestinationCountry() {
    return destinationCountry;
  }

  public void setDestinationCountry(String destinationCountry) {
    this.destinationCountry = destinationCountry;
  }

  public PackSearchContext destinationCity(String destinationCity) {
    this.destinationCity = destinationCity;
    return this;
  }

  /**
   * Get destinationCity
   * @return destinationCity
   */
  @NotNull @Size(min = 3, max = 3) 
  @Schema(name = "destination_city", example = "KBV", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("destination_city")
  public String getDestinationCity() {
    return destinationCity;
  }

  public void setDestinationCity(String destinationCity) {
    this.destinationCity = destinationCity;
  }

  public PackSearchContext dateFrom(LocalDate dateFrom) {
    this.dateFrom = dateFrom;
    return this;
  }

  /**
   * Get dateFrom
   * @return dateFrom
   */
  @NotNull @Valid 
  @Schema(name = "date_from", example = "2026-06-04", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("date_from")
  public LocalDate getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(LocalDate dateFrom) {
    this.dateFrom = dateFrom;
  }

  public PackSearchContext dateTo(LocalDate dateTo) {
    this.dateTo = dateTo;
    return this;
  }

  /**
   * Get dateTo
   * @return dateTo
   */
  @NotNull @Valid 
  @Schema(name = "date_to", example = "2026-06-26", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("date_to")
  public LocalDate getDateTo() {
    return dateTo;
  }

  public void setDateTo(LocalDate dateTo) {
    this.dateTo = dateTo;
  }

  public PackSearchContext currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Get currency
   * @return currency
   */
  @NotNull 
  @Schema(name = "currency", example = "RUB", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public PackSearchContext adults(Integer adults) {
    this.adults = adults;
    return this;
  }

  /**
   * Get adults
   * minimum: 1
   * maximum: 9
   * @return adults
   */
  @NotNull @Min(1) @Max(9) 
  @Schema(name = "adults", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("adults")
  public Integer getAdults() {
    return adults;
  }

  public void setAdults(Integer adults) {
    this.adults = adults;
  }

  public PackSearchContext children(@Nullable Integer children) {
    this.children = children;
    return this;
  }

  /**
   * Get children
   * minimum: 0
   * maximum: 9
   * @return children
   */
  @Min(0) @Max(9) 
  @Schema(name = "children", example = "0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("children")
  public @Nullable Integer getChildren() {
    return children;
  }

  public void setChildren(@Nullable Integer children) {
    this.children = children;
  }

  public PackSearchContext budget(@Nullable Long budget) {
    this.budget = budget;
    return this;
  }

  /**
   * Get budget
   * @return budget
   */
  
  @Schema(name = "budget", example = "1233333", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("budget")
  public @Nullable Long getBudget() {
    return budget;
  }

  public void setBudget(@Nullable Long budget) {
    this.budget = budget;
  }

  public PackSearchContext filters(List<String> filters) {
    this.filters = filters;
    return this;
  }

  public PackSearchContext addFiltersItem(String filtersItem) {
    if (this.filters == null) {
      this.filters = new ArrayList<>();
    }
    this.filters.add(filtersItem);
    return this;
  }

  /**
   * Get filters
   * @return filters
   */
  
  @Schema(name = "filters", example = "[]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("filters")
  public List<String> getFilters() {
    return filters;
  }

  public void setFilters(List<String> filters) {
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
    PackSearchContext packSearchContext = (PackSearchContext) o;
    return Objects.equals(this.originCountry, packSearchContext.originCountry) &&
        Objects.equals(this.originCity, packSearchContext.originCity) &&
        Objects.equals(this.destinationCountry, packSearchContext.destinationCountry) &&
        Objects.equals(this.destinationCity, packSearchContext.destinationCity) &&
        Objects.equals(this.dateFrom, packSearchContext.dateFrom) &&
        Objects.equals(this.dateTo, packSearchContext.dateTo) &&
        Objects.equals(this.currency, packSearchContext.currency) &&
        Objects.equals(this.adults, packSearchContext.adults) &&
        Objects.equals(this.children, packSearchContext.children) &&
        Objects.equals(this.budget, packSearchContext.budget) &&
        Objects.equals(this.filters, packSearchContext.filters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(originCountry, originCity, destinationCountry, destinationCity, dateFrom, dateTo, currency, adults, children, budget, filters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PackSearchContext {\n");
    sb.append("    originCountry: ").append(toIndentedString(originCountry)).append("\n");
    sb.append("    originCity: ").append(toIndentedString(originCity)).append("\n");
    sb.append("    destinationCountry: ").append(toIndentedString(destinationCountry)).append("\n");
    sb.append("    destinationCity: ").append(toIndentedString(destinationCity)).append("\n");
    sb.append("    dateFrom: ").append(toIndentedString(dateFrom)).append("\n");
    sb.append("    dateTo: ").append(toIndentedString(dateTo)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    adults: ").append(toIndentedString(adults)).append("\n");
    sb.append("    children: ").append(toIndentedString(children)).append("\n");
    sb.append("    budget: ").append(toIndentedString(budget)).append("\n");
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

