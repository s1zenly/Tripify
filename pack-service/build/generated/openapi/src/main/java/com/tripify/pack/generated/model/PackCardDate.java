package com.tripify.pack.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
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
 * PackCardDate
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class PackCardDate {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate from;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate to;

  public PackCardDate() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PackCardDate(LocalDate from, LocalDate to) {
    this.from = from;
    this.to = to;
  }

  public PackCardDate from(LocalDate from) {
    this.from = from;
    return this;
  }

  /**
   * Get from
   * @return from
   */
  @NotNull @Valid 
  @Schema(name = "from", example = "2026-12-12", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("from")
  public LocalDate getFrom() {
    return from;
  }

  public void setFrom(LocalDate from) {
    this.from = from;
  }

  public PackCardDate to(LocalDate to) {
    this.to = to;
    return this;
  }

  /**
   * Get to
   * @return to
   */
  @NotNull @Valid 
  @Schema(name = "to", example = "2026-12-31", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("to")
  public LocalDate getTo() {
    return to;
  }

  public void setTo(LocalDate to) {
    this.to = to;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PackCardDate packCardDate = (PackCardDate) o;
    return Objects.equals(this.from, packCardDate.from) &&
        Objects.equals(this.to, packCardDate.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PackCardDate {\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
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

