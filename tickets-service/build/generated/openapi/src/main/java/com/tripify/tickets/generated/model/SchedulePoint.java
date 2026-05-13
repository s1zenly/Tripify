package com.tripify.tickets.generated.model;

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
 * SchedulePoint
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-30T21:33:13.369032554Z[Etc/UTC]", comments = "Generator version: 7.16.0")
public class SchedulePoint {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime datetime;

  private String timezone;

  public SchedulePoint() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SchedulePoint(OffsetDateTime datetime, String timezone) {
    this.datetime = datetime;
    this.timezone = timezone;
  }

  public SchedulePoint datetime(OffsetDateTime datetime) {
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

  public SchedulePoint timezone(String timezone) {
    this.timezone = timezone;
    return this;
  }

  /**
   * Get timezone
   * @return timezone
   */
  @NotNull 
  @Schema(name = "timezone", example = "Europe/Moscow", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("timezone")
  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchedulePoint schedulePoint = (SchedulePoint) o;
    return Objects.equals(this.datetime, schedulePoint.datetime) &&
        Objects.equals(this.timezone, schedulePoint.timezone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datetime, timezone);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchedulePoint {\n");
    sb.append("    datetime: ").append(toIndentedString(datetime)).append("\n");
    sb.append("    timezone: ").append(toIndentedString(timezone)).append("\n");
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

