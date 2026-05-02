package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
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
 * PaymentMethodsCardsInfo
 */

@JsonTypeName("PaymentMethods_cardsInfo")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-20T23:06:41.504250+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class PaymentMethodsCardsInfo {

  private @Nullable Boolean isCard;

  @Valid
  private List<String> cardTypes = new ArrayList<>();

  public PaymentMethodsCardsInfo isCard(@Nullable Boolean isCard) {
    this.isCard = isCard;
    return this;
  }

  /**
   * Get isCard
   * @return isCard
   */
  
  @Schema(name = "isCard", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isCard")
  public @Nullable Boolean getIsCard() {
    return isCard;
  }

  public void setIsCard(@Nullable Boolean isCard) {
    this.isCard = isCard;
  }

  public PaymentMethodsCardsInfo cardTypes(List<String> cardTypes) {
    this.cardTypes = cardTypes;
    return this;
  }

  public PaymentMethodsCardsInfo addCardTypesItem(String cardTypesItem) {
    if (this.cardTypes == null) {
      this.cardTypes = new ArrayList<>();
    }
    this.cardTypes.add(cardTypesItem);
    return this;
  }

  /**
   * Get cardTypes
   * @return cardTypes
   */
  
  @Schema(name = "cardTypes", example = "[VISA, MASTERCARD]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cardTypes")
  public List<String> getCardTypes() {
    return cardTypes;
  }

  public void setCardTypes(List<String> cardTypes) {
    this.cardTypes = cardTypes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentMethodsCardsInfo paymentMethodsCardsInfo = (PaymentMethodsCardsInfo) o;
    return Objects.equals(this.isCard, paymentMethodsCardsInfo.isCard) &&
        Objects.equals(this.cardTypes, paymentMethodsCardsInfo.cardTypes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isCard, cardTypes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentMethodsCardsInfo {\n");
    sb.append("    isCard: ").append(toIndentedString(isCard)).append("\n");
    sb.append("    cardTypes: ").append(toIndentedString(cardTypes)).append("\n");
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

