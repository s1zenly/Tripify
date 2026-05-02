package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.hotels.generated.model.HotelCard;
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
 * HotelsResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-20T23:06:41.504250+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class HotelsResponse {

  @Valid
  private List<@Valid HotelCard> hotels = new ArrayList<>();

  private Long totalHotels;

  public HotelsResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public HotelsResponse(List<@Valid HotelCard> hotels, Long totalHotels) {
    this.hotels = hotels;
    this.totalHotels = totalHotels;
  }

  public HotelsResponse hotels(List<@Valid HotelCard> hotels) {
    this.hotels = hotels;
    return this;
  }

  public HotelsResponse addHotelsItem(HotelCard hotelsItem) {
    if (this.hotels == null) {
      this.hotels = new ArrayList<>();
    }
    this.hotels.add(hotelsItem);
    return this;
  }

  /**
   * Get hotels
   * @return hotels
   */
  @NotNull @Valid 
  @Schema(name = "hotels", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("hotels")
  public List<@Valid HotelCard> getHotels() {
    return hotels;
  }

  public void setHotels(List<@Valid HotelCard> hotels) {
    this.hotels = hotels;
  }

  public HotelsResponse totalHotels(Long totalHotels) {
    this.totalHotels = totalHotels;
    return this;
  }

  /**
   * Get totalHotels
   * @return totalHotels
   */
  @NotNull 
  @Schema(name = "totalHotels", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalHotels")
  public Long getTotalHotels() {
    return totalHotels;
  }

  public void setTotalHotels(Long totalHotels) {
    this.totalHotels = totalHotels;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HotelsResponse hotelsResponse = (HotelsResponse) o;
    return Objects.equals(this.hotels, hotelsResponse.hotels) &&
        Objects.equals(this.totalHotels, hotelsResponse.totalHotels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hotels, totalHotels);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HotelsResponse {\n");
    sb.append("    hotels: ").append(toIndentedString(hotels)).append("\n");
    sb.append("    totalHotels: ").append(toIndentedString(totalHotels)).append("\n");
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

