package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.hotels.generated.model.RefundCondition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * RefundRule
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-24T20:52:08.294927+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class RefundRule {

  private @Nullable Boolean refundPrepayment;

  @Valid
  private List<@Valid RefundCondition> conditions = new ArrayList<>();

  public RefundRule refundPrepayment(@Nullable Boolean refundPrepayment) {
    this.refundPrepayment = refundPrepayment;
    return this;
  }

  /**
   * Get refundPrepayment
   * @return refundPrepayment
   */
  
  @Schema(name = "refundPrepayment", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("refundPrepayment")
  public @Nullable Boolean getRefundPrepayment() {
    return refundPrepayment;
  }

  public void setRefundPrepayment(@Nullable Boolean refundPrepayment) {
    this.refundPrepayment = refundPrepayment;
  }

  public RefundRule conditions(List<@Valid RefundCondition> conditions) {
    this.conditions = conditions;
    return this;
  }

  public RefundRule addConditionsItem(RefundCondition conditionsItem) {
    if (this.conditions == null) {
      this.conditions = new ArrayList<>();
    }
    this.conditions.add(conditionsItem);
    return this;
  }

  /**
   * Get conditions
   * @return conditions
   */
  @Valid 
  @Schema(name = "conditions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("conditions")
  public List<@Valid RefundCondition> getConditions() {
    return conditions;
  }

  public void setConditions(List<@Valid RefundCondition> conditions) {
    this.conditions = conditions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RefundRule refundRule = (RefundRule) o;
    return Objects.equals(this.refundPrepayment, refundRule.refundPrepayment) &&
        Objects.equals(this.conditions, refundRule.conditions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(refundPrepayment, conditions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RefundRule {\n");
    sb.append("    refundPrepayment: ").append(toIndentedString(refundPrepayment)).append("\n");
    sb.append("    conditions: ").append(toIndentedString(conditions)).append("\n");
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

