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
 * Facility
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-24T20:52:08.294927+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class Facility {

  private @Nullable String type;

  private @Nullable Boolean isFree;

  public Facility type(@Nullable String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  
  @Schema(name = "type", example = "wifi", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public @Nullable String getType() {
    return type;
  }

  public void setType(@Nullable String type) {
    this.type = type;
  }

  public Facility isFree(@Nullable Boolean isFree) {
    this.isFree = isFree;
    return this;
  }

  /**
   * Get isFree
   * @return isFree
   */
  
  @Schema(name = "isFree", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isFree")
  public @Nullable Boolean getIsFree() {
    return isFree;
  }

  public void setIsFree(@Nullable Boolean isFree) {
    this.isFree = isFree;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Facility facility = (Facility) o;
    return Objects.equals(this.type, facility.type) &&
        Objects.equals(this.isFree, facility.isFree);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, isFree);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Facility {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    isFree: ").append(toIndentedString(isFree)).append("\n");
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

