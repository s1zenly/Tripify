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
 * CancelPenalty
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T01:20:13.365045+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class CancelPenalty {

  private @Nullable String type;

  private @Nullable Double amount;

  private @Nullable Integer percent;

  public CancelPenalty type(@Nullable String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  
  @Schema(name = "type", example = "FIRST_NIGHT", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public @Nullable String getType() {
    return type;
  }

  public void setType(@Nullable String type) {
    this.type = type;
  }

  public CancelPenalty amount(@Nullable Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   */
  
  @Schema(name = "amount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("amount")
  public @Nullable Double getAmount() {
    return amount;
  }

  public void setAmount(@Nullable Double amount) {
    this.amount = amount;
  }

  public CancelPenalty percent(@Nullable Integer percent) {
    this.percent = percent;
    return this;
  }

  /**
   * Get percent
   * @return percent
   */
  
  @Schema(name = "percent", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("percent")
  public @Nullable Integer getPercent() {
    return percent;
  }

  public void setPercent(@Nullable Integer percent) {
    this.percent = percent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CancelPenalty cancelPenalty = (CancelPenalty) o;
    return Objects.equals(this.type, cancelPenalty.type) &&
        Objects.equals(this.amount, cancelPenalty.amount) &&
        Objects.equals(this.percent, cancelPenalty.percent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, amount, percent);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CancelPenalty {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    percent: ").append(toIndentedString(percent)).append("\n");
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

