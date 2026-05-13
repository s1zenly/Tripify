package com.tripify.tickets.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.tickets.generated.model.TicketDetail;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TicketDetailResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-30T21:33:13.369032554Z[Etc/UTC]", comments = "Generator version: 7.16.0")
public class TicketDetailResponse {

  private TicketDetail ticket;

  public TicketDetailResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TicketDetailResponse(TicketDetail ticket) {
    this.ticket = ticket;
  }

  public TicketDetailResponse ticket(TicketDetail ticket) {
    this.ticket = ticket;
    return this;
  }

  /**
   * Get ticket
   * @return ticket
   */
  @NotNull @Valid 
  @Schema(name = "ticket", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("ticket")
  public TicketDetail getTicket() {
    return ticket;
  }

  public void setTicket(TicketDetail ticket) {
    this.ticket = ticket;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketDetailResponse ticketDetailResponse = (TicketDetailResponse) o;
    return Objects.equals(this.ticket, ticketDetailResponse.ticket);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ticket);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketDetailResponse {\n");
    sb.append("    ticket: ").append(toIndentedString(ticket)).append("\n");
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

