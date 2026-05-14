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
 * TransfersSummary
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class TransfersSummary {

  private Integer count;

  private Boolean isDirect;

  public TransfersSummary() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TransfersSummary(Integer count, Boolean isDirect) {
    this.count = count;
    this.isDirect = isDirect;
  }

  public TransfersSummary count(Integer count) {
    this.count = count;
    return this;
  }

  /**
   * Get count
   * minimum: 0
   * @return count
   */
  @NotNull @Min(0) 
  @Schema(name = "count", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("count")
  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public TransfersSummary isDirect(Boolean isDirect) {
    this.isDirect = isDirect;
    return this;
  }

  /**
   * Get isDirect
   * @return isDirect
   */
  @NotNull 
  @Schema(name = "is_direct", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("is_direct")
  public Boolean getIsDirect() {
    return isDirect;
  }

  public void setIsDirect(Boolean isDirect) {
    this.isDirect = isDirect;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransfersSummary transfersSummary = (TransfersSummary) o;
    return Objects.equals(this.count, transfersSummary.count) &&
        Objects.equals(this.isDirect, transfersSummary.isDirect);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, isDirect);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransfersSummary {\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    isDirect: ").append(toIndentedString(isDirect)).append("\n");
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

