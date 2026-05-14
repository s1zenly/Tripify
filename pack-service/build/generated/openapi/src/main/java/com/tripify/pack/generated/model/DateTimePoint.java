package com.tripify.pack.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
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
 * DateTimePoint
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class DateTimePoint {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime datetime;

  public DateTimePoint() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DateTimePoint(OffsetDateTime datetime) {
    this.datetime = datetime;
  }

  public DateTimePoint datetime(OffsetDateTime datetime) {
    this.datetime = datetime;
    return this;
  }

  /**
   * Get datetime
   * @return datetime
   */
  @NotNull @Valid 
  @Schema(name = "datetime", example = "2026-05-12T10:30+03:00", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("datetime")
  public OffsetDateTime getDatetime() {
    return datetime;
  }

  public void setDatetime(OffsetDateTime datetime) {
    this.datetime = datetime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DateTimePoint dateTimePoint = (DateTimePoint) o;
    return Objects.equals(this.datetime, dateTimePoint.datetime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datetime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DateTimePoint {\n");
    sb.append("    datetime: ").append(toIndentedString(datetime)).append("\n");
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

