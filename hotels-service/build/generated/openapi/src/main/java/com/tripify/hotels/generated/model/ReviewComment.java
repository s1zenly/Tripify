package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.hotels.generated.model.Photo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ReviewComment
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-20T23:06:41.504250+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class ReviewComment {

  private @Nullable String author;

  private @Nullable String country;

  private @Nullable String vacationType;

  private @Nullable Double rating;

  private @Nullable String goodPart = null;

  private @Nullable String badPart = null;

  private @Nullable String commonText = null;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate reviewDate;

  @Valid
  private List<@Valid Photo> photos = new ArrayList<>();

  public ReviewComment author(@Nullable String author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
   */
  
  @Schema(name = "author", example = "Name6", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("author")
  public @Nullable String getAuthor() {
    return author;
  }

  public void setAuthor(@Nullable String author) {
    this.author = author;
  }

  public ReviewComment country(@Nullable String country) {
    this.country = country;
    return this;
  }

  /**
   * Get country
   * @return country
   */
  
  @Schema(name = "country", example = "RU", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("country")
  public @Nullable String getCountry() {
    return country;
  }

  public void setCountry(@Nullable String country) {
    this.country = country;
  }

  public ReviewComment vacationType(@Nullable String vacationType) {
    this.vacationType = vacationType;
    return this;
  }

  /**
   * Get vacationType
   * @return vacationType
   */
  
  @Schema(name = "vacationType", example = "family", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("vacationType")
  public @Nullable String getVacationType() {
    return vacationType;
  }

  public void setVacationType(@Nullable String vacationType) {
    this.vacationType = vacationType;
  }

  public ReviewComment rating(@Nullable Double rating) {
    this.rating = rating;
    return this;
  }

  /**
   * Get rating
   * @return rating
   */
  
  @Schema(name = "rating", example = "9.0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("rating")
  public @Nullable Double getRating() {
    return rating;
  }

  public void setRating(@Nullable Double rating) {
    this.rating = rating;
  }

  public ReviewComment goodPart(@Nullable String goodPart) {
    this.goodPart = goodPart;
    return this;
  }

  /**
   * Get goodPart
   * @return goodPart
   */
  
  @Schema(name = "goodPart", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("goodPart")
  public @Nullable String getGoodPart() {
    return goodPart;
  }

  public void setGoodPart(@Nullable String goodPart) {
    this.goodPart = goodPart;
  }

  public ReviewComment badPart(@Nullable String badPart) {
    this.badPart = badPart;
    return this;
  }

  /**
   * Get badPart
   * @return badPart
   */
  
  @Schema(name = "badPart", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("badPart")
  public @Nullable String getBadPart() {
    return badPart;
  }

  public void setBadPart(@Nullable String badPart) {
    this.badPart = badPart;
  }

  public ReviewComment commonText(@Nullable String commonText) {
    this.commonText = commonText;
    return this;
  }

  /**
   * Get commonText
   * @return commonText
   */
  
  @Schema(name = "commonText", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commonText")
  public @Nullable String getCommonText() {
    return commonText;
  }

  public void setCommonText(@Nullable String commonText) {
    this.commonText = commonText;
  }

  public ReviewComment reviewDate(@Nullable LocalDate reviewDate) {
    this.reviewDate = reviewDate;
    return this;
  }

  /**
   * Get reviewDate
   * @return reviewDate
   */
  @Valid 
  @Schema(name = "reviewDate", example = "2026-05-15", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reviewDate")
  public @Nullable LocalDate getReviewDate() {
    return reviewDate;
  }

  public void setReviewDate(@Nullable LocalDate reviewDate) {
    this.reviewDate = reviewDate;
  }

  public ReviewComment photos(List<@Valid Photo> photos) {
    this.photos = photos;
    return this;
  }

  public ReviewComment addPhotosItem(Photo photosItem) {
    if (this.photos == null) {
      this.photos = new ArrayList<>();
    }
    this.photos.add(photosItem);
    return this;
  }

  /**
   * Get photos
   * @return photos
   */
  @Valid 
  @Schema(name = "photos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("photos")
  public List<@Valid Photo> getPhotos() {
    return photos;
  }

  public void setPhotos(List<@Valid Photo> photos) {
    this.photos = photos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReviewComment reviewComment = (ReviewComment) o;
    return Objects.equals(this.author, reviewComment.author) &&
        Objects.equals(this.country, reviewComment.country) &&
        Objects.equals(this.vacationType, reviewComment.vacationType) &&
        Objects.equals(this.rating, reviewComment.rating) &&
        Objects.equals(this.goodPart, reviewComment.goodPart) &&
        Objects.equals(this.badPart, reviewComment.badPart) &&
        Objects.equals(this.commonText, reviewComment.commonText) &&
        Objects.equals(this.reviewDate, reviewComment.reviewDate) &&
        Objects.equals(this.photos, reviewComment.photos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(author, country, vacationType, rating, goodPart, badPart, commonText, reviewDate, photos);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReviewComment {\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    vacationType: ").append(toIndentedString(vacationType)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
    sb.append("    goodPart: ").append(toIndentedString(goodPart)).append("\n");
    sb.append("    badPart: ").append(toIndentedString(badPart)).append("\n");
    sb.append("    commonText: ").append(toIndentedString(commonText)).append("\n");
    sb.append("    reviewDate: ").append(toIndentedString(reviewDate)).append("\n");
    sb.append("    photos: ").append(toIndentedString(photos)).append("\n");
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

