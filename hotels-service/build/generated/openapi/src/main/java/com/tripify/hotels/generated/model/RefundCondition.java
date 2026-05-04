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
 * RefundCondition
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-24T20:52:08.294927+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class RefundCondition {

  private @Nullable Integer quantityPercent;

  private @Nullable String condition;

  public RefundCondition quantityPercent(@Nullable Integer quantityPercent) {
    this.quantityPercent = quantityPercent;
    return this;
  }

  /**
   * Get quantityPercent
   * @return quantityPercent
   */
  
  @Schema(name = "quantityPercent", example = "100", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("quantityPercent")
  public @Nullable Integer getQuantityPercent() {
    return quantityPercent;
  }

  public void setQuantityPercent(@Nullable Integer quantityPercent) {
    this.quantityPercent = quantityPercent;
  }

  public RefundCondition condition(@Nullable String condition) {
    this.condition = condition;
    return this;
  }

  /**
   * Get condition
   * @return condition
   */
  
  @Schema(name = "condition", example = "Free cancellation 24h before", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("condition")
  public @Nullable String getCondition() {
    return condition;
  }

  public void setCondition(@Nullable String condition) {
    this.condition = condition;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RefundCondition refundCondition = (RefundCondition) o;
    return Objects.equals(this.quantityPercent, refundCondition.quantityPercent) &&
        Objects.equals(this.condition, refundCondition.condition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(quantityPercent, condition);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RefundCondition {\n");
    sb.append("    quantityPercent: ").append(toIndentedString(quantityPercent)).append("\n");
    sb.append("    condition: ").append(toIndentedString(condition)).append("\n");
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

