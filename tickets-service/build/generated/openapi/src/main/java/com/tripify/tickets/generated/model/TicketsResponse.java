package com.tripify.tickets.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.tickets.generated.model.TicketCard;
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
 * TicketsResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T01:47:04.875924+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class TicketsResponse {

  @Valid
  private List<@Valid TicketCard> tickets = new ArrayList<>();

  public TicketsResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TicketsResponse(List<@Valid TicketCard> tickets) {
    this.tickets = tickets;
  }

  public TicketsResponse tickets(List<@Valid TicketCard> tickets) {
    this.tickets = tickets;
    return this;
  }

  public TicketsResponse addTicketsItem(TicketCard ticketsItem) {
    if (this.tickets == null) {
      this.tickets = new ArrayList<>();
    }
    this.tickets.add(ticketsItem);
    return this;
  }

  /**
   * Get tickets
   * @return tickets
   */
  @NotNull @Valid 
  @Schema(name = "tickets", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tickets")
  public List<@Valid TicketCard> getTickets() {
    return tickets;
  }

  public void setTickets(List<@Valid TicketCard> tickets) {
    this.tickets = tickets;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketsResponse ticketsResponse = (TicketsResponse) o;
    return Objects.equals(this.tickets, ticketsResponse.tickets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tickets);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketsResponse {\n");
    sb.append("    tickets: ").append(toIndentedString(tickets)).append("\n");
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

