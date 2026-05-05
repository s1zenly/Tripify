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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-25T20:44:55.035359+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class HotelsResponse {

  @Valid
  private List<@Valid HotelCard> hotels = new ArrayList<>();

  private @Nullable java.util.UUID nextCursor = null;

  public HotelsResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public HotelsResponse(List<@Valid HotelCard> hotels) {
    this.hotels = hotels;
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

  public HotelsResponse nextCursor(@Nullable java.util.UUID nextCursor) {
    this.nextCursor = nextCursor;
    return this;
  }

  /**
   * hotelId последнего отеля на странице (id > nextCursor). Есть только если отелей больше, чем limit. 
   * @return nextCursor
   */
  @Valid 
  @Schema(name = "nextCursor", example = "8f4e3c2d-1b0a-5f6e-7c8d-9e0f1a2b3c4d", description = "hotelId последнего отеля на странице (id > nextCursor). Есть только если отелей больше, чем limit. ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nextCursor")
  public @Nullable java.util.UUID getNextCursor() {
    return nextCursor;
  }

  public void setNextCursor(@Nullable java.util.UUID nextCursor) {
    this.nextCursor = nextCursor;
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
        Objects.equals(this.nextCursor, hotelsResponse.nextCursor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hotels, nextCursor);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HotelsResponse {\n");
    sb.append("    hotels: ").append(toIndentedString(hotels)).append("\n");
    sb.append("    nextCursor: ").append(toIndentedString(nextCursor)).append("\n");
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

