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
 * TicketDetailFare
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class TicketDetailFare {

  private Boolean refundable;

  private Boolean exchangeable;

  private String fareFamily;

  public TicketDetailFare() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TicketDetailFare(Boolean refundable, Boolean exchangeable, String fareFamily) {
    this.refundable = refundable;
    this.exchangeable = exchangeable;
    this.fareFamily = fareFamily;
  }

  public TicketDetailFare refundable(Boolean refundable) {
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

  public TicketDetailFare exchangeable(Boolean exchangeable) {
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

  public TicketDetailFare fareFamily(String fareFamily) {
    this.fareFamily = fareFamily;
    return this;
  }

  /**
   * Get fareFamily
   * @return fareFamily
   */
  @NotNull 
  @Schema(name = "fare_family", example = "Economy Saver", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("fare_family")
  public String getFareFamily() {
    return fareFamily;
  }

  public void setFareFamily(String fareFamily) {
    this.fareFamily = fareFamily;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketDetailFare ticketDetailFare = (TicketDetailFare) o;
    return Objects.equals(this.refundable, ticketDetailFare.refundable) &&
        Objects.equals(this.exchangeable, ticketDetailFare.exchangeable) &&
        Objects.equals(this.fareFamily, ticketDetailFare.fareFamily);
  }

  @Override
  public int hashCode() {
    return Objects.hash(refundable, exchangeable, fareFamily);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketDetailFare {\n");
    sb.append("    refundable: ").append(toIndentedString(refundable)).append("\n");
    sb.append("    exchangeable: ").append(toIndentedString(exchangeable)).append("\n");
    sb.append("    fareFamily: ").append(toIndentedString(fareFamily)).append("\n");
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

