package com.tripify.tickets.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tripify.tickets.generated.model.AirlineSummary;
import com.tripify.tickets.generated.model.DateTimePoint;
import com.tripify.tickets.generated.model.Duration;
import com.tripify.tickets.generated.model.JourneyType;
import com.tripify.tickets.generated.model.LocationSummary;
import com.tripify.tickets.generated.model.TransfersSummary;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TicketJourney
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-29T20:25:02.204846+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class TicketJourney {

  private JourneyType type;

  private LocationSummary from;

  private LocationSummary to;

  private DateTimePoint departure;

  private DateTimePoint arrival;

  private Duration duration;

  private TransfersSummary transfers;

  private AirlineSummary airline;

  public TicketJourney() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TicketJourney(JourneyType type, LocationSummary from, LocationSummary to, DateTimePoint departure, DateTimePoint arrival, Duration duration, TransfersSummary transfers, AirlineSummary airline) {
    this.type = type;
    this.from = from;
    this.to = to;
    this.departure = departure;
    this.arrival = arrival;
    this.duration = duration;
    this.transfers = transfers;
    this.airline = airline;
  }

  public TicketJourney type(JourneyType type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  @NotNull @Valid 
  @Schema(name = "type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public JourneyType getType() {
    return type;
  }

  public void setType(JourneyType type) {
    this.type = type;
  }

  public TicketJourney from(LocationSummary from) {
    this.from = from;
    return this;
  }

  /**
   * Get from
   * @return from
   */
  @NotNull @Valid 
  @Schema(name = "from", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("from")
  public LocationSummary getFrom() {
    return from;
  }

  public void setFrom(LocationSummary from) {
    this.from = from;
  }

  public TicketJourney to(LocationSummary to) {
    this.to = to;
    return this;
  }

  /**
   * Get to
   * @return to
   */
  @NotNull @Valid 
  @Schema(name = "to", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("to")
  public LocationSummary getTo() {
    return to;
  }

  public void setTo(LocationSummary to) {
    this.to = to;
  }

  public TicketJourney departure(DateTimePoint departure) {
    this.departure = departure;
    return this;
  }

  /**
   * Get departure
   * @return departure
   */
  @NotNull @Valid 
  @Schema(name = "departure", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("departure")
  public DateTimePoint getDeparture() {
    return departure;
  }

  public void setDeparture(DateTimePoint departure) {
    this.departure = departure;
  }

  public TicketJourney arrival(DateTimePoint arrival) {
    this.arrival = arrival;
    return this;
  }

  /**
   * Get arrival
   * @return arrival
   */
  @NotNull @Valid 
  @Schema(name = "arrival", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("arrival")
  public DateTimePoint getArrival() {
    return arrival;
  }

  public void setArrival(DateTimePoint arrival) {
    this.arrival = arrival;
  }

  public TicketJourney duration(Duration duration) {
    this.duration = duration;
    return this;
  }

  /**
   * Get duration
   * @return duration
   */
  @NotNull @Valid 
  @Schema(name = "duration", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("duration")
  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public TicketJourney transfers(TransfersSummary transfers) {
    this.transfers = transfers;
    return this;
  }

  /**
   * Get transfers
   * @return transfers
   */
  @NotNull @Valid 
  @Schema(name = "transfers", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("transfers")
  public TransfersSummary getTransfers() {
    return transfers;
  }

  public void setTransfers(TransfersSummary transfers) {
    this.transfers = transfers;
  }

  public TicketJourney airline(AirlineSummary airline) {
    this.airline = airline;
    return this;
  }

  /**
   * Get airline
   * @return airline
   */
  @NotNull @Valid 
  @Schema(name = "airline", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("airline")
  public AirlineSummary getAirline() {
    return airline;
  }

  public void setAirline(AirlineSummary airline) {
    this.airline = airline;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketJourney ticketJourney = (TicketJourney) o;
    return Objects.equals(this.type, ticketJourney.type) &&
        Objects.equals(this.from, ticketJourney.from) &&
        Objects.equals(this.to, ticketJourney.to) &&
        Objects.equals(this.departure, ticketJourney.departure) &&
        Objects.equals(this.arrival, ticketJourney.arrival) &&
        Objects.equals(this.duration, ticketJourney.duration) &&
        Objects.equals(this.transfers, ticketJourney.transfers) &&
        Objects.equals(this.airline, ticketJourney.airline);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, from, to, departure, arrival, duration, transfers, airline);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketJourney {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    departure: ").append(toIndentedString(departure)).append("\n");
    sb.append("    arrival: ").append(toIndentedString(arrival)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    transfers: ").append(toIndentedString(transfers)).append("\n");
    sb.append("    airline: ").append(toIndentedString(airline)).append("\n");
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

