package com.tripify.tickets.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.tickets.generated.model.BaggageAllowance;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TicketBaggage
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-30T21:33:13.369032554Z[Etc/UTC]", comments = "Generator version: 7.16.0")
public class TicketBaggage {

  private BaggageAllowance checked;

  private BaggageAllowance handLuggage;

  public TicketBaggage() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TicketBaggage(BaggageAllowance checked, BaggageAllowance handLuggage) {
    this.checked = checked;
    this.handLuggage = handLuggage;
  }

  public TicketBaggage checked(BaggageAllowance checked) {
    this.checked = checked;
    return this;
  }

  /**
   * Get checked
   * @return checked
   */
  @NotNull @Valid 
  @Schema(name = "checked", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("checked")
  public BaggageAllowance getChecked() {
    return checked;
  }

  public void setChecked(BaggageAllowance checked) {
    this.checked = checked;
  }

  public TicketBaggage handLuggage(BaggageAllowance handLuggage) {
    this.handLuggage = handLuggage;
    return this;
  }

  /**
   * Get handLuggage
   * @return handLuggage
   */
  @NotNull @Valid 
  @Schema(name = "hand_luggage", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("hand_luggage")
  public BaggageAllowance getHandLuggage() {
    return handLuggage;
  }

  public void setHandLuggage(BaggageAllowance handLuggage) {
    this.handLuggage = handLuggage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketBaggage ticketBaggage = (TicketBaggage) o;
    return Objects.equals(this.checked, ticketBaggage.checked) &&
        Objects.equals(this.handLuggage, ticketBaggage.handLuggage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(checked, handLuggage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketBaggage {\n");
    sb.append("    checked: ").append(toIndentedString(checked)).append("\n");
    sb.append("    handLuggage: ").append(toIndentedString(handLuggage)).append("\n");
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

