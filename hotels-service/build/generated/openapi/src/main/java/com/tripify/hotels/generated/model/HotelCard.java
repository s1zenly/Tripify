package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.hotels.generated.model.Facility;
import com.tripify.hotels.generated.model.GpsCoordinates;
import com.tripify.hotels.generated.model.HotelReviewsShort;
import java.net.URI;
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
 * HotelCard
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-20T23:06:41.504250+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class HotelCard {

  private Long hotelId;

  private String title;

  private @Nullable URI link;

  private @Nullable String description;

  private @Nullable String address;

  private @Nullable GpsCoordinates gpsCoordinates;

  private String city;

  private String country;

  private String currency;

  private Long price;

  private Integer hotelClass;

  private HotelReviewsShort reviews;

  @Valid
  private List<@Valid Facility> facilities = new ArrayList<>();

  @Valid
  private List<String> tags = new ArrayList<>();

  public HotelCard() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public HotelCard(Long hotelId, String title, String city, String country, String currency, Long price, Integer hotelClass, HotelReviewsShort reviews, List<@Valid Facility> facilities, List<String> tags) {
    this.hotelId = hotelId;
    this.title = title;
    this.city = city;
    this.country = country;
    this.currency = currency;
    this.price = price;
    this.hotelClass = hotelClass;
    this.reviews = reviews;
    this.facilities = facilities;
    this.tags = tags;
  }

  public HotelCard hotelId(Long hotelId) {
    this.hotelId = hotelId;
    return this;
  }

  /**
   * Get hotelId
   * @return hotelId
   */
  @NotNull 
  @Schema(name = "hotelId", example = "7467355", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("hotelId")
  public Long getHotelId() {
    return hotelId;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }

  public HotelCard title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
   */
  @NotNull 
  @Schema(name = "title", example = "Florida Radison 5*", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public HotelCard link(@Nullable URI link) {
    this.link = link;
    return this;
  }

  /**
   * Get link
   * @return link
   */
  @Valid 
  @Schema(name = "link", example = "https://example.com/hotels/7467355", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("link")
  public @Nullable URI getLink() {
    return link;
  }

  public void setLink(@Nullable URI link) {
    this.link = link;
  }

  public HotelCard description(@Nullable String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  
  @Schema(name = "description", example = "Contemporary hotel with a restaurant, business center and fitness room.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public @Nullable String getDescription() {
    return description;
  }

  public void setDescription(@Nullable String description) {
    this.description = description;
  }

  public HotelCard address(@Nullable String address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
   */
  
  @Schema(name = "address", example = "Fool Street, New York City", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("address")
  public @Nullable String getAddress() {
    return address;
  }

  public void setAddress(@Nullable String address) {
    this.address = address;
  }

  public HotelCard gpsCoordinates(@Nullable GpsCoordinates gpsCoordinates) {
    this.gpsCoordinates = gpsCoordinates;
    return this;
  }

  /**
   * Get gpsCoordinates
   * @return gpsCoordinates
   */
  @Valid 
  @Schema(name = "gpsCoordinates", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("gpsCoordinates")
  public @Nullable GpsCoordinates getGpsCoordinates() {
    return gpsCoordinates;
  }

  public void setGpsCoordinates(@Nullable GpsCoordinates gpsCoordinates) {
    this.gpsCoordinates = gpsCoordinates;
  }

  public HotelCard city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Get city
   * @return city
   */
  @NotNull 
  @Schema(name = "city", example = "New York City", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("city")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public HotelCard country(String country) {
    this.country = country;
    return this;
  }

  /**
   * Get country
   * @return country
   */
  @NotNull 
  @Schema(name = "country", example = "US", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("country")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public HotelCard currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Get currency
   * @return currency
   */
  @NotNull 
  @Schema(name = "currency", example = "USD", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public HotelCard price(Long price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
   */
  @NotNull 
  @Schema(name = "price", example = "176", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("price")
  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public HotelCard hotelClass(Integer hotelClass) {
    this.hotelClass = hotelClass;
    return this;
  }

  /**
   * Get hotelClass
   * minimum: 1
   * maximum: 5
   * @return hotelClass
   */
  @NotNull @Min(1) @Max(5) 
  @Schema(name = "hotelClass", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("hotelClass")
  public Integer getHotelClass() {
    return hotelClass;
  }

  public void setHotelClass(Integer hotelClass) {
    this.hotelClass = hotelClass;
  }

  public HotelCard reviews(HotelReviewsShort reviews) {
    this.reviews = reviews;
    return this;
  }

  /**
   * Get reviews
   * @return reviews
   */
  @NotNull @Valid 
  @Schema(name = "reviews", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("reviews")
  public HotelReviewsShort getReviews() {
    return reviews;
  }

  public void setReviews(HotelReviewsShort reviews) {
    this.reviews = reviews;
  }

  public HotelCard facilities(List<@Valid Facility> facilities) {
    this.facilities = facilities;
    return this;
  }

  public HotelCard addFacilitiesItem(Facility facilitiesItem) {
    if (this.facilities == null) {
      this.facilities = new ArrayList<>();
    }
    this.facilities.add(facilitiesItem);
    return this;
  }

  /**
   * Get facilities
   * @return facilities
   */
  @NotNull @Valid 
  @Schema(name = "facilities", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("facilities")
  public List<@Valid Facility> getFacilities() {
    return facilities;
  }

  public void setFacilities(List<@Valid Facility> facilities) {
    this.facilities = facilities;
  }

  public HotelCard tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public HotelCard addTagsItem(String tagsItem) {
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
  @NotNull 
  @Schema(name = "tags", example = "[family, city-center, breakfast]", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tags")
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HotelCard hotelCard = (HotelCard) o;
    return Objects.equals(this.hotelId, hotelCard.hotelId) &&
        Objects.equals(this.title, hotelCard.title) &&
        Objects.equals(this.link, hotelCard.link) &&
        Objects.equals(this.description, hotelCard.description) &&
        Objects.equals(this.address, hotelCard.address) &&
        Objects.equals(this.gpsCoordinates, hotelCard.gpsCoordinates) &&
        Objects.equals(this.city, hotelCard.city) &&
        Objects.equals(this.country, hotelCard.country) &&
        Objects.equals(this.currency, hotelCard.currency) &&
        Objects.equals(this.price, hotelCard.price) &&
        Objects.equals(this.hotelClass, hotelCard.hotelClass) &&
        Objects.equals(this.reviews, hotelCard.reviews) &&
        Objects.equals(this.facilities, hotelCard.facilities) &&
        Objects.equals(this.tags, hotelCard.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hotelId, title, link, description, address, gpsCoordinates, city, country, currency, price, hotelClass, reviews, facilities, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HotelCard {\n");
    sb.append("    hotelId: ").append(toIndentedString(hotelId)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    link: ").append(toIndentedString(link)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    gpsCoordinates: ").append(toIndentedString(gpsCoordinates)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    hotelClass: ").append(toIndentedString(hotelClass)).append("\n");
    sb.append("    reviews: ").append(toIndentedString(reviews)).append("\n");
    sb.append("    facilities: ").append(toIndentedString(facilities)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
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

