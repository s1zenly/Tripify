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
 * CheckInOut
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-30T21:24:42.207813975Z[Etc/UTC]", comments = "Generator version: 7.16.0")
public class CheckInOut {

  private @Nullable String afterTime;

  private @Nullable String beforeTime;

  private @Nullable String timezone;

  public CheckInOut afterTime(@Nullable String afterTime) {
    this.afterTime = afterTime;
    return this;
  }

  /**
   * Get afterTime
   * @return afterTime
   */
  
  @Schema(name = "afterTime", example = "14:00", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("afterTime")
  public @Nullable String getAfterTime() {
    return afterTime;
  }

  public void setAfterTime(@Nullable String afterTime) {
    this.afterTime = afterTime;
  }

  public CheckInOut beforeTime(@Nullable String beforeTime) {
    this.beforeTime = beforeTime;
    return this;
  }

  /**
   * Get beforeTime
   * @return beforeTime
   */
  
  @Schema(name = "beforeTime", example = "00:00", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("beforeTime")
  public @Nullable String getBeforeTime() {
    return beforeTime;
  }

  public void setBeforeTime(@Nullable String beforeTime) {
    this.beforeTime = beforeTime;
  }

  public CheckInOut timezone(@Nullable String timezone) {
    this.timezone = timezone;
    return this;
  }

  /**
   * Get timezone
   * @return timezone
   */
  
  @Schema(name = "timezone", example = "UTC", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("timezone")
  public @Nullable String getTimezone() {
    return timezone;
  }

  public void setTimezone(@Nullable String timezone) {
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
    CheckInOut checkInOut = (CheckInOut) o;
    return Objects.equals(this.afterTime, checkInOut.afterTime) &&
        Objects.equals(this.beforeTime, checkInOut.beforeTime) &&
        Objects.equals(this.timezone, checkInOut.timezone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(afterTime, beforeTime, timezone);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CheckInOut {\n");
    sb.append("    afterTime: ").append(toIndentedString(afterTime)).append("\n");
    sb.append("    beforeTime: ").append(toIndentedString(beforeTime)).append("\n");
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

