package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.hotels.generated.model.CancellationPolicy;
import com.tripify.hotels.generated.model.MealPlan;
import com.tripify.hotels.generated.model.RateAvailability;
import com.tripify.hotels.generated.model.RatePayment;
import com.tripify.hotels.generated.model.RatePricing;
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
 * Rate
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-30T21:24:42.207813975Z[Etc/UTC]", comments = "Generator version: 7.16.0")
public class Rate {

  private String rateId;

  private String title;

  @Valid
  private List<String> tags = new ArrayList<>();

  private RatePricing pricing;

  private @Nullable RatePayment payment;

  private @Nullable MealPlan mealPlan;

  private @Nullable CancellationPolicy cancellationPolicy;

  private @Nullable RateAvailability availability;

  private @Nullable Boolean instantConfirmation;

  @Valid
  private List<String> perks = new ArrayList<>();

  public Rate() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Rate(String rateId, String title, RatePricing pricing) {
    this.rateId = rateId;
    this.title = title;
    this.pricing = pricing;
  }

  public Rate rateId(String rateId) {
    this.rateId = rateId;
    return this;
  }

  /**
   * Get rateId
   * @return rateId
   */
  @NotNull 
  @Schema(name = "rateId", example = "f2089925-333e-4e6b-a6cb-d6d61363ba43", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("rateId")
  public String getRateId() {
    return rateId;
  }

  public void setRateId(String rateId) {
    this.rateId = rateId;
  }

  public Rate title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
   */
  @NotNull 
  @Schema(name = "title", example = "Flexible Rate", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Rate tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public Rate addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * Get tags
   * @return tags
   */
  
  @Schema(name = "tags", example = "[FREE_CANCELLATION, BESTSELLER]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tags")
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public Rate pricing(RatePricing pricing) {
    this.pricing = pricing;
    return this;
  }

  /**
   * Get pricing
   * @return pricing
   */
  @NotNull @Valid 
  @Schema(name = "pricing", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pricing")
  public RatePricing getPricing() {
    return pricing;
  }

  public void setPricing(RatePricing pricing) {
    this.pricing = pricing;
  }

  public Rate payment(@Nullable RatePayment payment) {
    this.payment = payment;
    return this;
  }

  /**
   * Get payment
   * @return payment
   */
  @Valid 
  @Schema(name = "payment", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("payment")
  public @Nullable RatePayment getPayment() {
    return payment;
  }

  public void setPayment(@Nullable RatePayment payment) {
    this.payment = payment;
  }

  public Rate mealPlan(@Nullable MealPlan mealPlan) {
    this.mealPlan = mealPlan;
    return this;
  }

  /**
   * Get mealPlan
   * @return mealPlan
   */
  @Valid 
  @Schema(name = "mealPlan", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mealPlan")
  public @Nullable MealPlan getMealPlan() {
    return mealPlan;
  }

  public void setMealPlan(@Nullable MealPlan mealPlan) {
    this.mealPlan = mealPlan;
  }

  public Rate cancellationPolicy(@Nullable CancellationPolicy cancellationPolicy) {
    this.cancellationPolicy = cancellationPolicy;
    return this;
  }

  /**
   * Get cancellationPolicy
   * @return cancellationPolicy
   */
  @Valid 
  @Schema(name = "cancellationPolicy", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cancellationPolicy")
  public @Nullable CancellationPolicy getCancellationPolicy() {
    return cancellationPolicy;
  }

  public void setCancellationPolicy(@Nullable CancellationPolicy cancellationPolicy) {
    this.cancellationPolicy = cancellationPolicy;
  }

  public Rate availability(@Nullable RateAvailability availability) {
    this.availability = availability;
    return this;
  }

  /**
   * Get availability
   * @return availability
   */
  @Valid 
  @Schema(name = "availability", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("availability")
  public @Nullable RateAvailability getAvailability() {
    return availability;
  }

  public void setAvailability(@Nullable RateAvailability availability) {
    this.availability = availability;
  }

  public Rate instantConfirmation(@Nullable Boolean instantConfirmation) {
    this.instantConfirmation = instantConfirmation;
    return this;
  }

  /**
   * Get instantConfirmation
   * @return instantConfirmation
   */
  
  @Schema(name = "instantConfirmation", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("instantConfirmation")
  public @Nullable Boolean getInstantConfirmation() {
    return instantConfirmation;
  }

  public void setInstantConfirmation(@Nullable Boolean instantConfirmation) {
    this.instantConfirmation = instantConfirmation;
  }

  public Rate perks(List<String> perks) {
    this.perks = perks;
    return this;
  }

  public Rate addPerksItem(String perksItem) {
    if (this.perks == null) {
      this.perks = new ArrayList<>();
    }
    this.perks.add(perksItem);
    return this;
  }

  /**
   * Get perks
   * @return perks
   */
  
  @Schema(name = "perks", example = "[SPA_ACCESS, FREE_WIFI]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("perks")
  public List<String> getPerks() {
    return perks;
  }

  public void setPerks(List<String> perks) {
    this.perks = perks;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Rate rate = (Rate) o;
    return Objects.equals(this.rateId, rate.rateId) &&
        Objects.equals(this.title, rate.title) &&
        Objects.equals(this.tags, rate.tags) &&
        Objects.equals(this.pricing, rate.pricing) &&
        Objects.equals(this.payment, rate.payment) &&
        Objects.equals(this.mealPlan, rate.mealPlan) &&
        Objects.equals(this.cancellationPolicy, rate.cancellationPolicy) &&
        Objects.equals(this.availability, rate.availability) &&
        Objects.equals(this.instantConfirmation, rate.instantConfirmation) &&
        Objects.equals(this.perks, rate.perks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rateId, title, tags, pricing, payment, mealPlan, cancellationPolicy, availability, instantConfirmation, perks);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Rate {\n");
    sb.append("    rateId: ").append(toIndentedString(rateId)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    pricing: ").append(toIndentedString(pricing)).append("\n");
    sb.append("    payment: ").append(toIndentedString(payment)).append("\n");
    sb.append("    mealPlan: ").append(toIndentedString(mealPlan)).append("\n");
    sb.append("    cancellationPolicy: ").append(toIndentedString(cancellationPolicy)).append("\n");
    sb.append("    availability: ").append(toIndentedString(availability)).append("\n");
    sb.append("    instantConfirmation: ").append(toIndentedString(instantConfirmation)).append("\n");
    sb.append("    perks: ").append(toIndentedString(perks)).append("\n");
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

