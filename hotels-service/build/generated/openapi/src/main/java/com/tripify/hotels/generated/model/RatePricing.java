package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.hotels.generated.model.Discount;
import com.tripify.hotels.generated.model.Money;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * RatePricing
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T01:20:13.365045+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class RatePricing {

  private @Nullable Money basePrice;

  private @Nullable Money taxesAndFees;

  private @Nullable Money totalPrice;

  private @Nullable Money pricePerNight;

  private @Nullable Discount discount;

  public RatePricing basePrice(@Nullable Money basePrice) {
    this.basePrice = basePrice;
    return this;
  }

  /**
   * Get basePrice
   * @return basePrice
   */
  @Valid 
  @Schema(name = "basePrice", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("basePrice")
  public @Nullable Money getBasePrice() {
    return basePrice;
  }

  public void setBasePrice(@Nullable Money basePrice) {
    this.basePrice = basePrice;
  }

  public RatePricing taxesAndFees(@Nullable Money taxesAndFees) {
    this.taxesAndFees = taxesAndFees;
    return this;
  }

  /**
   * Get taxesAndFees
   * @return taxesAndFees
   */
  @Valid 
  @Schema(name = "taxesAndFees", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("taxesAndFees")
  public @Nullable Money getTaxesAndFees() {
    return taxesAndFees;
  }

  public void setTaxesAndFees(@Nullable Money taxesAndFees) {
    this.taxesAndFees = taxesAndFees;
  }

  public RatePricing totalPrice(@Nullable Money totalPrice) {
    this.totalPrice = totalPrice;
    return this;
  }

  /**
   * Get totalPrice
   * @return totalPrice
   */
  @Valid 
  @Schema(name = "totalPrice", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalPrice")
  public @Nullable Money getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(@Nullable Money totalPrice) {
    this.totalPrice = totalPrice;
  }

  public RatePricing pricePerNight(@Nullable Money pricePerNight) {
    this.pricePerNight = pricePerNight;
    return this;
  }

  /**
   * Get pricePerNight
   * @return pricePerNight
   */
  @Valid 
  @Schema(name = "pricePerNight", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pricePerNight")
  public @Nullable Money getPricePerNight() {
    return pricePerNight;
  }

  public void setPricePerNight(@Nullable Money pricePerNight) {
    this.pricePerNight = pricePerNight;
  }

  public RatePricing discount(@Nullable Discount discount) {
    this.discount = discount;
    return this;
  }

  /**
   * Get discount
   * @return discount
   */
  @Valid 
  @Schema(name = "discount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("discount")
  public @Nullable Discount getDiscount() {
    return discount;
  }

  public void setDiscount(@Nullable Discount discount) {
    this.discount = discount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RatePricing ratePricing = (RatePricing) o;
    return Objects.equals(this.basePrice, ratePricing.basePrice) &&
        Objects.equals(this.taxesAndFees, ratePricing.taxesAndFees) &&
        Objects.equals(this.totalPrice, ratePricing.totalPrice) &&
        Objects.equals(this.pricePerNight, ratePricing.pricePerNight) &&
        Objects.equals(this.discount, ratePricing.discount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(basePrice, taxesAndFees, totalPrice, pricePerNight, discount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RatePricing {\n");
    sb.append("    basePrice: ").append(toIndentedString(basePrice)).append("\n");
    sb.append("    taxesAndFees: ").append(toIndentedString(taxesAndFees)).append("\n");
    sb.append("    totalPrice: ").append(toIndentedString(totalPrice)).append("\n");
    sb.append("    pricePerNight: ").append(toIndentedString(pricePerNight)).append("\n");
    sb.append("    discount: ").append(toIndentedString(discount)).append("\n");
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

