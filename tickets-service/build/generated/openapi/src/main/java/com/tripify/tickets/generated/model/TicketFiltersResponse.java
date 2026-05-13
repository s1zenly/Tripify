package com.tripify.tickets.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.tickets.generated.model.TicketFilterOption;
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
 * TicketFiltersResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-30T21:33:13.369032554Z[Etc/UTC]", comments = "Generator version: 7.16.0")
public class TicketFiltersResponse {

  @Valid
  private List<@Valid TicketFilterOption> filters = new ArrayList<>();

  public TicketFiltersResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TicketFiltersResponse(List<@Valid TicketFilterOption> filters) {
    this.filters = filters;
  }

  public TicketFiltersResponse filters(List<@Valid TicketFilterOption> filters) {
    this.filters = filters;
    return this;
  }

  public TicketFiltersResponse addFiltersItem(TicketFilterOption filtersItem) {
    if (this.filters == null) {
      this.filters = new ArrayList<>();
    }
    this.filters.add(filtersItem);
    return this;
  }

  /**
   * Общий каталог фильтров для поиска билетов (не зависит от маршрута)
   * @return filters
   */
  @NotNull @Valid 
  @Schema(name = "filters", description = "Общий каталог фильтров для поиска билетов (не зависит от маршрута)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("filters")
  public List<@Valid TicketFilterOption> getFilters() {
    return filters;
  }

  public void setFilters(List<@Valid TicketFilterOption> filters) {
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
    TicketFiltersResponse ticketFiltersResponse = (TicketFiltersResponse) o;
    return Objects.equals(this.filters, ticketFiltersResponse.filters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketFiltersResponse {\n");
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

