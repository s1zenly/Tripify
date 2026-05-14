package com.tripify.pack.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.pack.generated.model.Passengers;
import com.tripify.pack.generated.model.TicketBaggage;
import com.tripify.pack.generated.model.TicketFare;
import com.tripify.pack.generated.model.TicketJourney;
import com.tripify.pack.generated.model.TicketPrice;
import java.net.URI;
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
 * TicketCard
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class TicketCard {

  private String tid;

  private URI deeplink;

  private TicketPrice price;

  private Passengers passengers;

  @Valid
  private List<@Valid TicketJourney> journeys = new ArrayList<>();

  private TicketBaggage baggage;

  private TicketFare fare;

  private Integer seatsLeft;

  public TicketCard() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TicketCard(String tid, URI deeplink, TicketPrice price, Passengers passengers, List<@Valid TicketJourney> journeys, TicketBaggage baggage, TicketFare fare, Integer seatsLeft) {
    this.tid = tid;
    this.deeplink = deeplink;
    this.price = price;
    this.passengers = passengers;
    this.journeys = journeys;
    this.baggage = baggage;
    this.fare = fare;
    this.seatsLeft = seatsLeft;
  }

  public TicketCard tid(String tid) {
    this.tid = tid;
    return this;
  }

  /**
   * Tripify ticket identifier
   * @return tid
   */
  @NotNull 
  @Schema(name = "tid", example = "ticket_8f3a1c2d", description = "Tripify ticket identifier", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tid")
  public String getTid() {
    return tid;
  }

  public void setTid(String tid) {
    this.tid = tid;
  }

  public TicketCard deeplink(URI deeplink) {
    this.deeplink = deeplink;
    return this;
  }

  /**
   * Get deeplink
   * @return deeplink
   */
  @NotNull @Valid 
  @Schema(name = "deeplink", example = "https://example.com/buy", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("deeplink")
  public URI getDeeplink() {
    return deeplink;
  }

  public void setDeeplink(URI deeplink) {
    this.deeplink = deeplink;
  }

  public TicketCard price(TicketPrice price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
   */
  @NotNull @Valid 
  @Schema(name = "price", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("price")
  public TicketPrice getPrice() {
    return price;
  }

  public void setPrice(TicketPrice price) {
    this.price = price;
  }

  public TicketCard passengers(Passengers passengers) {
    this.passengers = passengers;
    return this;
  }

  /**
   * Get passengers
   * @return passengers
   */
  @NotNull @Valid 
  @Schema(name = "passengers", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("passengers")
  public Passengers getPassengers() {
    return passengers;
  }

  public void setPassengers(Passengers passengers) {
    this.passengers = passengers;
  }

  public TicketCard journeys(List<@Valid TicketJourney> journeys) {
    this.journeys = journeys;
    return this;
  }

  public TicketCard addJourneysItem(TicketJourney journeysItem) {
    if (this.journeys == null) {
      this.journeys = new ArrayList<>();
    }
    this.journeys.add(journeysItem);
    return this;
  }

  /**
   * Get journeys
   * @return journeys
   */
  @NotNull @Valid @Size(min = 1) 
  @Schema(name = "journeys", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("journeys")
  public List<@Valid TicketJourney> getJourneys() {
    return journeys;
  }

  public void setJourneys(List<@Valid TicketJourney> journeys) {
    this.journeys = journeys;
  }

  public TicketCard baggage(TicketBaggage baggage) {
    this.baggage = baggage;
    return this;
  }

  /**
   * Get baggage
   * @return baggage
   */
  @NotNull @Valid 
  @Schema(name = "baggage", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("baggage")
  public TicketBaggage getBaggage() {
    return baggage;
  }

  public void setBaggage(TicketBaggage baggage) {
    this.baggage = baggage;
  }

  public TicketCard fare(TicketFare fare) {
    this.fare = fare;
    return this;
  }

  /**
   * Get fare
   * @return fare
   */
  @NotNull @Valid 
  @Schema(name = "fare", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("fare")
  public TicketFare getFare() {
    return fare;
  }

  public void setFare(TicketFare fare) {
    this.fare = fare;
  }

  public TicketCard seatsLeft(Integer seatsLeft) {
    this.seatsLeft = seatsLeft;
    return this;
  }

  /**
   * Get seatsLeft
   * minimum: 0
   * @return seatsLeft
   */
  @NotNull @Min(0) 
  @Schema(name = "seats_left", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("seats_left")
  public Integer getSeatsLeft() {
    return seatsLeft;
  }

  public void setSeatsLeft(Integer seatsLeft) {
    this.seatsLeft = seatsLeft;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketCard ticketCard = (TicketCard) o;
    return Objects.equals(this.tid, ticketCard.tid) &&
        Objects.equals(this.deeplink, ticketCard.deeplink) &&
        Objects.equals(this.price, ticketCard.price) &&
        Objects.equals(this.passengers, ticketCard.passengers) &&
        Objects.equals(this.journeys, ticketCard.journeys) &&
        Objects.equals(this.baggage, ticketCard.baggage) &&
        Objects.equals(this.fare, ticketCard.fare) &&
        Objects.equals(this.seatsLeft, ticketCard.seatsLeft);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tid, deeplink, price, passengers, journeys, baggage, fare, seatsLeft);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketCard {\n");
    sb.append("    tid: ").append(toIndentedString(tid)).append("\n");
    sb.append("    deeplink: ").append(toIndentedString(deeplink)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    passengers: ").append(toIndentedString(passengers)).append("\n");
    sb.append("    journeys: ").append(toIndentedString(journeys)).append("\n");
    sb.append("    baggage: ").append(toIndentedString(baggage)).append("\n");
    sb.append("    fare: ").append(toIndentedString(fare)).append("\n");
    sb.append("    seatsLeft: ").append(toIndentedString(seatsLeft)).append("\n");
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

