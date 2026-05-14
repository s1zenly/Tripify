package com.tripify.pack.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.net.URI;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PackCardHotel
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class PackCardHotel {

  private String name;

  private Integer stars;

  private @Nullable String mealType;

  private @Nullable URI imageUrl;

  public PackCardHotel() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PackCardHotel(String name, Integer stars) {
    this.name = name;
    this.stars = stars;
  }

  public PackCardHotel name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull 
  @Schema(name = "name", example = "Switzerland Palace Hotel", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PackCardHotel stars(Integer stars) {
    this.stars = stars;
    return this;
  }

  /**
   * Get stars
   * minimum: 1
   * maximum: 5
   * @return stars
   */
  @NotNull @Min(1) @Max(5) 
  @Schema(name = "stars", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("stars")
  public Integer getStars() {
    return stars;
  }

  public void setStars(Integer stars) {
    this.stars = stars;
  }

  public PackCardHotel mealType(@Nullable String mealType) {
    this.mealType = mealType;
    return this;
  }

  /**
   * Get mealType
   * @return mealType
   */
  
  @Schema(name = "meal_type", example = "ALL_INCLUSIVE", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("meal_type")
  public @Nullable String getMealType() {
    return mealType;
  }

  public void setMealType(@Nullable String mealType) {
    this.mealType = mealType;
  }

  public PackCardHotel imageUrl(@Nullable URI imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  /**
   * Get imageUrl
   * @return imageUrl
   */
  @Valid 
  @Schema(name = "image_url", example = "https://s3.example.com/hotel.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("image_url")
  public @Nullable URI getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(@Nullable URI imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PackCardHotel packCardHotel = (PackCardHotel) o;
    return Objects.equals(this.name, packCardHotel.name) &&
        Objects.equals(this.stars, packCardHotel.stars) &&
        Objects.equals(this.mealType, packCardHotel.mealType) &&
        Objects.equals(this.imageUrl, packCardHotel.imageUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, stars, mealType, imageUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PackCardHotel {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    stars: ").append(toIndentedString(stars)).append("\n");
    sb.append("    mealType: ").append(toIndentedString(mealType)).append("\n");
    sb.append("    imageUrl: ").append(toIndentedString(imageUrl)).append("\n");
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

