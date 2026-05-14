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
 * Passengers
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T01:47:04.875924+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class Passengers {

  private Integer adults;

  private Integer children;

  public Passengers() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Passengers(Integer adults, Integer children) {
    this.adults = adults;
    this.children = children;
  }

  public Passengers adults(Integer adults) {
    this.adults = adults;
    return this;
  }

  /**
   * Get adults
   * minimum: 0
   * @return adults
   */
  @NotNull @Min(0) 
  @Schema(name = "adults", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("adults")
  public Integer getAdults() {
    return adults;
  }

  public void setAdults(Integer adults) {
    this.adults = adults;
  }

  public Passengers children(Integer children) {
    this.children = children;
    return this;
  }

  /**
   * Get children
   * minimum: 0
   * @return children
   */
  @NotNull @Min(0) 
  @Schema(name = "children", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("children")
  public Integer getChildren() {
    return children;
  }

  public void setChildren(Integer children) {
    this.children = children;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Passengers passengers = (Passengers) o;
    return Objects.equals(this.adults, passengers.adults) &&
        Objects.equals(this.children, passengers.children);
  }

  @Override
  public int hashCode() {
    return Objects.hash(adults, children);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Passengers {\n");
    sb.append("    adults: ").append(toIndentedString(adults)).append("\n");
    sb.append("    children: ").append(toIndentedString(children)).append("\n");
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

