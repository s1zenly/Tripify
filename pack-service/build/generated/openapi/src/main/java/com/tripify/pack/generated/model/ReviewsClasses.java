package com.tripify.pack.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ReviewsClasses
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class ReviewsClasses {

  private @Nullable Double cleanliness;

  private @Nullable Double service;

  private @Nullable Double priceQuality;

  private @Nullable Double room;

  private @Nullable Double location;

  public ReviewsClasses cleanliness(@Nullable Double cleanliness) {
    this.cleanliness = cleanliness;
    return this;
  }

  /**
   * Get cleanliness
   * @return cleanliness
   */
  
  @Schema(name = "cleanliness", example = "7.8", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cleanliness")
  public @Nullable Double getCleanliness() {
    return cleanliness;
  }

  public void setCleanliness(@Nullable Double cleanliness) {
    this.cleanliness = cleanliness;
  }

  public ReviewsClasses service(@Nullable Double service) {
    this.service = service;
    return this;
  }

  /**
   * Get service
   * @return service
   */
  
  @Schema(name = "service", example = "9.0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("service")
  public @Nullable Double getService() {
    return service;
  }

  public void setService(@Nullable Double service) {
    this.service = service;
  }

  public ReviewsClasses priceQuality(@Nullable Double priceQuality) {
    this.priceQuality = priceQuality;
    return this;
  }

  /**
   * Get priceQuality
   * @return priceQuality
   */
  
  @Schema(name = "priceQuality", example = "9.5", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("priceQuality")
  public @Nullable Double getPriceQuality() {
    return priceQuality;
  }

  public void setPriceQuality(@Nullable Double priceQuality) {
    this.priceQuality = priceQuality;
  }

  public ReviewsClasses room(@Nullable Double room) {
    this.room = room;
    return this;
  }

  /**
   * Get room
   * @return room
   */
  
  @Schema(name = "room", example = "10.0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("room")
  public @Nullable Double getRoom() {
    return room;
  }

  public void setRoom(@Nullable Double room) {
    this.room = room;
  }

  public ReviewsClasses location(@Nullable Double location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   * @return location
   */
  
  @Schema(name = "location", example = "8.3", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("location")
  public @Nullable Double getLocation() {
    return location;
  }

  public void setLocation(@Nullable Double location) {
    this.location = location;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReviewsClasses reviewsClasses = (ReviewsClasses) o;
    return Objects.equals(this.cleanliness, reviewsClasses.cleanliness) &&
        Objects.equals(this.service, reviewsClasses.service) &&
        Objects.equals(this.priceQuality, reviewsClasses.priceQuality) &&
        Objects.equals(this.room, reviewsClasses.room) &&
        Objects.equals(this.location, reviewsClasses.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cleanliness, service, priceQuality, room, location);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReviewsClasses {\n");
    sb.append("    cleanliness: ").append(toIndentedString(cleanliness)).append("\n");
    sb.append("    service: ").append(toIndentedString(service)).append("\n");
    sb.append("    priceQuality: ").append(toIndentedString(priceQuality)).append("\n");
    sb.append("    room: ").append(toIndentedString(room)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
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

