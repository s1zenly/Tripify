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
 * Duration
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class Duration {

  private Integer minutes;

  public Duration() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Duration(Integer minutes) {
    this.minutes = minutes;
  }

  public Duration minutes(Integer minutes) {
    this.minutes = minutes;
    return this;
  }

  /**
   * Get minutes
   * minimum: 0
   * @return minutes
   */
  @NotNull @Min(0) 
  @Schema(name = "minutes", example = "360", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("minutes")
  public Integer getMinutes() {
    return minutes;
  }

  public void setMinutes(Integer minutes) {
    this.minutes = minutes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Duration duration = (Duration) o;
    return Objects.equals(this.minutes, duration.minutes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minutes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Duration {\n");
    sb.append("    minutes: ").append(toIndentedString(minutes)).append("\n");
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

