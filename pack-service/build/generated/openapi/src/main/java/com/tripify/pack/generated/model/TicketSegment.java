package com.tripify.pack.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tripify.pack.generated.model.AirlineSummary;
import com.tripify.pack.generated.model.CabinClass;
import com.tripify.pack.generated.model.Duration;
import com.tripify.pack.generated.model.LocationDetail;
import com.tripify.pack.generated.model.SchedulePoint;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TicketSegment
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class TicketSegment {

  private String segmentId;

  private LocationDetail from;

  private LocationDetail to;

  private SchedulePoint departure;

  private SchedulePoint arrival;

  private Duration duration;

  private AirlineSummary airline;

  private AirlineSummary operatedBy;

  private String flightNumber;

  private String aircraft;

  private CabinClass cabinClass;

  public TicketSegment() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TicketSegment(String segmentId, LocationDetail from, LocationDetail to, SchedulePoint departure, SchedulePoint arrival, Duration duration, AirlineSummary airline, AirlineSummary operatedBy, String flightNumber, String aircraft, CabinClass cabinClass) {
    this.segmentId = segmentId;
    this.from = from;
    this.to = to;
    this.departure = departure;
    this.arrival = arrival;
    this.duration = duration;
    this.airline = airline;
    this.operatedBy = operatedBy;
    this.flightNumber = flightNumber;
    this.aircraft = aircraft;
    this.cabinClass = cabinClass;
  }

  public TicketSegment segmentId(String segmentId) {
    this.segmentId = segmentId;
    return this;
  }

  /**
   * Get segmentId
   * @return segmentId
   */
  @NotNull 
  @Schema(name = "segment_id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("segment_id")
  public String getSegmentId() {
    return segmentId;
  }

  public void setSegmentId(String segmentId) {
    this.segmentId = segmentId;
  }

  public TicketSegment from(LocationDetail from) {
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
  public LocationDetail getFrom() {
    return from;
  }

  public void setFrom(LocationDetail from) {
    this.from = from;
  }

  public TicketSegment to(LocationDetail to) {
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
  public LocationDetail getTo() {
    return to;
  }

  public void setTo(LocationDetail to) {
    this.to = to;
  }

  public TicketSegment departure(SchedulePoint departure) {
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
  public SchedulePoint getDeparture() {
    return departure;
  }

  public void setDeparture(SchedulePoint departure) {
    this.departure = departure;
  }

  public TicketSegment arrival(SchedulePoint arrival) {
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
  public SchedulePoint getArrival() {
    return arrival;
  }

  public void setArrival(SchedulePoint arrival) {
    this.arrival = arrival;
  }

  public TicketSegment duration(Duration duration) {
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

  public TicketSegment airline(AirlineSummary airline) {
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

  public TicketSegment operatedBy(AirlineSummary operatedBy) {
    this.operatedBy = operatedBy;
    return this;
  }

  /**
   * Get operatedBy
   * @return operatedBy
   */
  @NotNull @Valid 
  @Schema(name = "operated_by", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("operated_by")
  public AirlineSummary getOperatedBy() {
    return operatedBy;
  }

  public void setOperatedBy(AirlineSummary operatedBy) {
    this.operatedBy = operatedBy;
  }

  public TicketSegment flightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
    return this;
  }

  /**
   * Get flightNumber
   * @return flightNumber
   */
  @NotNull 
  @Schema(name = "flight_number", example = "EK132", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("flight_number")
  public String getFlightNumber() {
    return flightNumber;
  }

  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  public TicketSegment aircraft(String aircraft) {
    this.aircraft = aircraft;
    return this;
  }

  /**
   * Get aircraft
   * @return aircraft
   */
  @NotNull 
  @Schema(name = "aircraft", example = "Boeing 777", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("aircraft")
  public String getAircraft() {
    return aircraft;
  }

  public void setAircraft(String aircraft) {
    this.aircraft = aircraft;
  }

  public TicketSegment cabinClass(CabinClass cabinClass) {
    this.cabinClass = cabinClass;
    return this;
  }

  /**
   * Get cabinClass
   * @return cabinClass
   */
  @NotNull @Valid 
  @Schema(name = "cabin_class", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("cabin_class")
  public CabinClass getCabinClass() {
    return cabinClass;
  }

  public void setCabinClass(CabinClass cabinClass) {
    this.cabinClass = cabinClass;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketSegment ticketSegment = (TicketSegment) o;
    return Objects.equals(this.segmentId, ticketSegment.segmentId) &&
        Objects.equals(this.from, ticketSegment.from) &&
        Objects.equals(this.to, ticketSegment.to) &&
        Objects.equals(this.departure, ticketSegment.departure) &&
        Objects.equals(this.arrival, ticketSegment.arrival) &&
        Objects.equals(this.duration, ticketSegment.duration) &&
        Objects.equals(this.airline, ticketSegment.airline) &&
        Objects.equals(this.operatedBy, ticketSegment.operatedBy) &&
        Objects.equals(this.flightNumber, ticketSegment.flightNumber) &&
        Objects.equals(this.aircraft, ticketSegment.aircraft) &&
        Objects.equals(this.cabinClass, ticketSegment.cabinClass);
  }

  @Override
  public int hashCode() {
    return Objects.hash(segmentId, from, to, departure, arrival, duration, airline, operatedBy, flightNumber, aircraft, cabinClass);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketSegment {\n");
    sb.append("    segmentId: ").append(toIndentedString(segmentId)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    departure: ").append(toIndentedString(departure)).append("\n");
    sb.append("    arrival: ").append(toIndentedString(arrival)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    airline: ").append(toIndentedString(airline)).append("\n");
    sb.append("    operatedBy: ").append(toIndentedString(operatedBy)).append("\n");
    sb.append("    flightNumber: ").append(toIndentedString(flightNumber)).append("\n");
    sb.append("    aircraft: ").append(toIndentedString(aircraft)).append("\n");
    sb.append("    cabinClass: ").append(toIndentedString(cabinClass)).append("\n");
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

