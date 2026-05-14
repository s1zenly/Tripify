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
 * Discount
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T01:20:13.365045+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class Discount {

  private @Nullable Integer percent;

  private @Nullable Double amount;

  public Discount percent(@Nullable Integer percent) {
    this.percent = percent;
    return this;
  }

  /**
   * Get percent
   * @return percent
   */
  
  @Schema(name = "percent", example = "25", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("percent")
  public @Nullable Integer getPercent() {
    return percent;
  }

  public void setPercent(@Nullable Integer percent) {
    this.percent = percent;
  }

  public Discount amount(@Nullable Double amount) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Discount discount = (Discount) o;
    return Objects.equals(this.percent, discount.percent) &&
        Objects.equals(this.amount, discount.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(percent, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Discount {\n");
    sb.append("    percent: ").append(toIndentedString(percent)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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

