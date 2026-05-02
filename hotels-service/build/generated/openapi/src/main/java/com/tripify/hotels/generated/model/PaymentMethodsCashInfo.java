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
 * PaymentMethodsCashInfo
 */

@JsonTypeName("PaymentMethods_cashInfo")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-20T23:06:41.504250+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class PaymentMethodsCashInfo {

  private @Nullable Boolean isCash;

  @Valid
  private List<String> currency = new ArrayList<>();

  public PaymentMethodsCashInfo isCash(@Nullable Boolean isCash) {
    this.isCash = isCash;
    return this;
  }

  /**
   * Get isCash
   * @return isCash
   */
  
  @Schema(name = "isCash", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isCash")
  public @Nullable Boolean getIsCash() {
    return isCash;
  }

  public void setIsCash(@Nullable Boolean isCash) {
    this.isCash = isCash;
  }

  public PaymentMethodsCashInfo currency(List<String> currency) {
    this.currency = currency;
    return this;
  }

  public PaymentMethodsCashInfo addCurrencyItem(String currencyItem) {
    if (this.currency == null) {
      this.currency = new ArrayList<>();
    }
    this.currency.add(currencyItem);
    return this;
  }

  /**
   * Get currency
   * @return currency
   */
  
  @Schema(name = "currency", example = "[USD, EUR]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("currency")
  public List<String> getCurrency() {
    return currency;
  }

  public void setCurrency(List<String> currency) {
    this.currency = currency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentMethodsCashInfo paymentMethodsCashInfo = (PaymentMethodsCashInfo) o;
    return Objects.equals(this.isCash, paymentMethodsCashInfo.isCash) &&
        Objects.equals(this.currency, paymentMethodsCashInfo.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isCash, currency);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentMethodsCashInfo {\n");
    sb.append("    isCash: ").append(toIndentedString(isCash)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
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

