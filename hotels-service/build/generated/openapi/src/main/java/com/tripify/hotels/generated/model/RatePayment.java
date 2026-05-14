package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * RatePayment
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T01:20:13.365045+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class RatePayment {

  private @Nullable String type;

  private @Nullable Boolean prepaymentRequired;

  @Valid
  private List<String> cards = new ArrayList<>();

  public RatePayment type(@Nullable String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  
  @Schema(name = "type", example = "PAY_NOW", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public @Nullable String getType() {
    return type;
  }

  public void setType(@Nullable String type) {
    this.type = type;
  }

  public RatePayment prepaymentRequired(@Nullable Boolean prepaymentRequired) {
    this.prepaymentRequired = prepaymentRequired;
    return this;
  }

  /**
   * Get prepaymentRequired
   * @return prepaymentRequired
   */
  
  @Schema(name = "prepaymentRequired", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("prepaymentRequired")
  public @Nullable Boolean getPrepaymentRequired() {
    return prepaymentRequired;
  }

  public void setPrepaymentRequired(@Nullable Boolean prepaymentRequired) {
    this.prepaymentRequired = prepaymentRequired;
  }

  public RatePayment cards(List<String> cards) {
    this.cards = cards;
    return this;
  }

  public RatePayment addCardsItem(String cardsItem) {
    if (this.cards == null) {
      this.cards = new ArrayList<>();
    }
    this.cards.add(cardsItem);
    return this;
  }

  /**
   * Get cards
   * @return cards
   */
  
  @Schema(name = "cards", example = "[VISA, MASTERCARD]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cards")
  public List<String> getCards() {
    return cards;
  }

  public void setCards(List<String> cards) {
    this.cards = cards;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RatePayment ratePayment = (RatePayment) o;
    return Objects.equals(this.type, ratePayment.type) &&
        Objects.equals(this.prepaymentRequired, ratePayment.prepaymentRequired) &&
        Objects.equals(this.cards, ratePayment.cards);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, prepaymentRequired, cards);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RatePayment {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    prepaymentRequired: ").append(toIndentedString(prepaymentRequired)).append("\n");
    sb.append("    cards: ").append(toIndentedString(cards)).append("\n");
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

