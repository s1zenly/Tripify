package com.tripify.pack.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tripify.pack.generated.model.Duration;
import com.tripify.pack.generated.model.JourneyType;
import com.tripify.pack.generated.model.LocationDetail;
import com.tripify.pack.generated.model.SchedulePoint;
import com.tripify.pack.generated.model.TicketSegment;
import com.tripify.pack.generated.model.TransfersDetail;
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
 * TicketJourneyDetail
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class TicketJourneyDetail {

  private JourneyType type;

  private LocationDetail from;

  private LocationDetail to;

  private SchedulePoint departure;

  private SchedulePoint arrival;

  private Duration duration;

  private TransfersDetail transfers;

  @Valid
  private List<@Valid TicketSegment> segments = new ArrayList<>();

  public TicketJourneyDetail() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TicketJourneyDetail(JourneyType type, LocationDetail from, LocationDetail to, SchedulePoint departure, SchedulePoint arrival, Duration duration, TransfersDetail transfers, List<@Valid TicketSegment> segments) {
    this.type = type;
    this.from = from;
    this.to = to;
    this.departure = departure;
    this.arrival = arrival;
    this.duration = duration;
    this.transfers = transfers;
    this.segments = segments;
  }

  public TicketJourneyDetail type(JourneyType type) {
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

  public TicketJourneyDetail from(LocationDetail from) {
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

  public TicketJourneyDetail to(LocationDetail to) {
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

  public TicketJourneyDetail departure(SchedulePoint departure) {
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

  public TicketJourneyDetail arrival(SchedulePoint arrival) {
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

  public TicketJourneyDetail duration(Duration duration) {
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

  public TicketJourneyDetail transfers(TransfersDetail transfers) {
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
  public TransfersDetail getTransfers() {
    return transfers;
  }

  public void setTransfers(TransfersDetail transfers) {
    this.transfers = transfers;
  }

  public TicketJourneyDetail segments(List<@Valid TicketSegment> segments) {
    this.segments = segments;
    return this;
  }

  public TicketJourneyDetail addSegmentsItem(TicketSegment segmentsItem) {
    if (this.segments == null) {
      this.segments = new ArrayList<>();
    }
    this.segments.add(segmentsItem);
    return this;
  }

  /**
   * Get segments
   * @return segments
   */
  @NotNull @Valid @Size(min = 1) 
  @Schema(name = "segments", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("segments")
  public List<@Valid TicketSegment> getSegments() {
    return segments;
  }

  public void setSegments(List<@Valid TicketSegment> segments) {
    this.segments = segments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketJourneyDetail ticketJourneyDetail = (TicketJourneyDetail) o;
    return Objects.equals(this.type, ticketJourneyDetail.type) &&
        Objects.equals(this.from, ticketJourneyDetail.from) &&
        Objects.equals(this.to, ticketJourneyDetail.to) &&
        Objects.equals(this.departure, ticketJourneyDetail.departure) &&
        Objects.equals(this.arrival, ticketJourneyDetail.arrival) &&
        Objects.equals(this.duration, ticketJourneyDetail.duration) &&
        Objects.equals(this.transfers, ticketJourneyDetail.transfers) &&
        Objects.equals(this.segments, ticketJourneyDetail.segments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, from, to, departure, arrival, duration, transfers, segments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketJourneyDetail {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    departure: ").append(toIndentedString(departure)).append("\n");
    sb.append("    arrival: ").append(toIndentedString(arrival)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    transfers: ").append(toIndentedString(transfers)).append("\n");
    sb.append("    segments: ").append(toIndentedString(segments)).append("\n");
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

