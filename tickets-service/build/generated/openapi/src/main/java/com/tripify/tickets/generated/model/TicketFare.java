package com.tripify.tickets.generated.model;

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
 * TicketFare
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-29T20:25:02.204846+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class TicketFare {

  private Boolean refundable;

  private Boolean exchangeable;

  public TicketFare() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TicketFare(Boolean refundable, Boolean exchangeable) {
    this.refundable = refundable;
    this.exchangeable = exchangeable;
  }

  public TicketFare refundable(Boolean refundable) {
    this.refundable = refundable;
    return this;
  }

  /**
   * Get refundable
   * @return refundable
   */
  @NotNull 
  @Schema(name = "refundable", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("refundable")
  public Boolean getRefundable() {
    return refundable;
  }

  public void setRefundable(Boolean refundable) {
    this.refundable = refundable;
  }

  public TicketFare exchangeable(Boolean exchangeable) {
    this.exchangeable = exchangeable;
    return this;
  }

  /**
   * Get exchangeable
   * @return exchangeable
   */
  @NotNull 
  @Schema(name = "exchangeable", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("exchangeable")
  public Boolean getExchangeable() {
    return exchangeable;
  }

  public void setExchangeable(Boolean exchangeable) {
    this.exchangeable = exchangeable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketFare ticketFare = (TicketFare) o;
    return Objects.equals(this.refundable, ticketFare.refundable) &&
        Objects.equals(this.exchangeable, ticketFare.exchangeable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(refundable, exchangeable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketFare {\n");
    sb.append("    refundable: ").append(toIndentedString(refundable)).append("\n");
    sb.append("    exchangeable: ").append(toIndentedString(exchangeable)).append("\n");
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

