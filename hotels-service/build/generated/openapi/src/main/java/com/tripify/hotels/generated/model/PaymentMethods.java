package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.hotels.generated.model.PaymentMethodsCardsInfo;
import com.tripify.hotels.generated.model.PaymentMethodsCashInfo;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PaymentMethods
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-24T20:52:08.294927+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class PaymentMethods {

  private @Nullable PaymentMethodsCashInfo cashInfo;

  private @Nullable PaymentMethodsCardsInfo cardsInfo;

  public PaymentMethods cashInfo(@Nullable PaymentMethodsCashInfo cashInfo) {
    this.cashInfo = cashInfo;
    return this;
  }

  /**
   * Get cashInfo
   * @return cashInfo
   */
  @Valid 
  @Schema(name = "cashInfo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cashInfo")
  public @Nullable PaymentMethodsCashInfo getCashInfo() {
    return cashInfo;
  }

  public void setCashInfo(@Nullable PaymentMethodsCashInfo cashInfo) {
    this.cashInfo = cashInfo;
  }

  public PaymentMethods cardsInfo(@Nullable PaymentMethodsCardsInfo cardsInfo) {
    this.cardsInfo = cardsInfo;
    return this;
  }

  /**
   * Get cardsInfo
   * @return cardsInfo
   */
  @Valid 
  @Schema(name = "cardsInfo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cardsInfo")
  public @Nullable PaymentMethodsCardsInfo getCardsInfo() {
    return cardsInfo;
  }

  public void setCardsInfo(@Nullable PaymentMethodsCardsInfo cardsInfo) {
    this.cardsInfo = cardsInfo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentMethods paymentMethods = (PaymentMethods) o;
    return Objects.equals(this.cashInfo, paymentMethods.cashInfo) &&
        Objects.equals(this.cardsInfo, paymentMethods.cardsInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cashInfo, cardsInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentMethods {\n");
    sb.append("    cashInfo: ").append(toIndentedString(cashInfo)).append("\n");
    sb.append("    cardsInfo: ").append(toIndentedString(cardsInfo)).append("\n");
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

