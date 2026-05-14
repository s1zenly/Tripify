package com.tripify.pack.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.pack.generated.model.PackCardDate;
import com.tripify.pack.generated.model.PackCardHotel;
import com.tripify.pack.generated.model.PackCardPrice;
import java.net.URI;
import java.util.UUID;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PackCard
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class PackCard {

  private UUID id;

  private String country;

  private String city;

  private PackCardHotel hotel;

  private @Nullable URI imagePackUrl;

  private PackCardDate date;

  private Integer durationDays;

  private PackCardPrice price;

  public PackCard() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PackCard(UUID id, String country, String city, PackCardHotel hotel, PackCardDate date, Integer durationDays, PackCardPrice price) {
    this.id = id;
    this.country = country;
    this.city = city;
    this.hotel = hotel;
    this.date = date;
    this.durationDays = durationDays;
    this.price = price;
  }

  public PackCard id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Pack revision identifier from PostgreSQL
   * @return id
   */
  @NotNull @Valid 
  @Schema(name = "id", example = "01977f2e-29a2-7df4-bdb7-89f54a1d9d2c", description = "Pack revision identifier from PostgreSQL", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public PackCard country(String country) {
    this.country = country;
    return this;
  }

  /**
   * Destination country ISO Alpha-2
   * @return country
   */
  @NotNull @Size(min = 2, max = 2) 
  @Schema(name = "country", example = "RU", description = "Destination country ISO Alpha-2", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("country")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public PackCard city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Destination city IATA code
   * @return city
   */
  @NotNull @Size(min = 3, max = 3) 
  @Schema(name = "city", example = "MOW", description = "Destination city IATA code", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("city")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public PackCard hotel(PackCardHotel hotel) {
    this.hotel = hotel;
    return this;
  }

  /**
   * Get hotel
   * @return hotel
   */
  @NotNull @Valid 
  @Schema(name = "hotel", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("hotel")
  public PackCardHotel getHotel() {
    return hotel;
  }

  public void setHotel(PackCardHotel hotel) {
    this.hotel = hotel;
  }

  public PackCard imagePackUrl(@Nullable URI imagePackUrl) {
    this.imagePackUrl = imagePackUrl;
    return this;
  }

  /**
   * Get imagePackUrl
   * @return imagePackUrl
   */
  @Valid 
  @Schema(name = "image_pack_url", example = "https://cdn.example.com/packs/preview.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("image_pack_url")
  public @Nullable URI getImagePackUrl() {
    return imagePackUrl;
  }

  public void setImagePackUrl(@Nullable URI imagePackUrl) {
    this.imagePackUrl = imagePackUrl;
  }

  public PackCard date(PackCardDate date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
   */
  @NotNull @Valid 
  @Schema(name = "date", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("date")
  public PackCardDate getDate() {
    return date;
  }

  public void setDate(PackCardDate date) {
    this.date = date;
  }

  public PackCard durationDays(Integer durationDays) {
    this.durationDays = durationDays;
    return this;
  }

  /**
   * Get durationDays
   * minimum: 1
   * @return durationDays
   */
  @NotNull @Min(1) 
  @Schema(name = "duration_days", example = "16", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("duration_days")
  public Integer getDurationDays() {
    return durationDays;
  }

  public void setDurationDays(Integer durationDays) {
    this.durationDays = durationDays;
  }

  public PackCard price(PackCardPrice price) {
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
  public PackCardPrice getPrice() {
    return price;
  }

  public void setPrice(PackCardPrice price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PackCard packCard = (PackCard) o;
    return Objects.equals(this.id, packCard.id) &&
        Objects.equals(this.country, packCard.country) &&
        Objects.equals(this.city, packCard.city) &&
        Objects.equals(this.hotel, packCard.hotel) &&
        Objects.equals(this.imagePackUrl, packCard.imagePackUrl) &&
        Objects.equals(this.date, packCard.date) &&
        Objects.equals(this.durationDays, packCard.durationDays) &&
        Objects.equals(this.price, packCard.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, country, city, hotel, imagePackUrl, date, durationDays, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PackCard {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    hotel: ").append(toIndentedString(hotel)).append("\n");
    sb.append("    imagePackUrl: ").append(toIndentedString(imagePackUrl)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    durationDays: ").append(toIndentedString(durationDays)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
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

