package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.hotels.generated.model.Facility;
import com.tripify.hotels.generated.model.GpsCoordinates;
import com.tripify.hotels.generated.model.HotelReviewsFull;
import com.tripify.hotels.generated.model.HotelScore;
import com.tripify.hotels.generated.model.NearbyPlace;
import com.tripify.hotels.generated.model.Photo;
import com.tripify.hotels.generated.model.Room;
import com.tripify.hotels.generated.model.TermsPlacement;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * HotelDetails
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-25T20:44:55.035359+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class HotelDetails {

  private java.util.UUID hotelId;

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

  private HotelReviewsFull reviews;

  @Valid
  private List<@Valid Facility> facilities = new ArrayList<>();

  @Valid
  private List<String> tags = new ArrayList<>();

  @Valid
  private List<@Valid Photo> photos = new ArrayList<>();

  @Valid
  private Map<String, List<@Valid NearbyPlace>> nearbyPlaces = new HashMap<>();

  private TermsPlacement termsPlacement;

  @Valid
  private List<@Valid Room> rooms = new ArrayList<>();

  private @Nullable HotelScore score;

  public HotelDetails() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public HotelDetails(java.util.UUID hotelId, String title, String city, String country, String currency, Long price, Integer hotelClass, HotelReviewsFull reviews, List<@Valid Facility> facilities, List<String> tags, List<@Valid Photo> photos, Map<String, List<@Valid NearbyPlace>> nearbyPlaces, TermsPlacement termsPlacement, List<@Valid Room> rooms) {
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
    this.photos = photos;
    this.nearbyPlaces = nearbyPlaces;
    this.termsPlacement = termsPlacement;
    this.rooms = rooms;
  }

  public HotelDetails hotelId(java.util.UUID hotelId) {
    this.hotelId = hotelId;
    return this;
  }

  /**
   * Get hotelId
   * @return hotelId
   */
  @NotNull @Valid 
  @Schema(name = "hotelId", example = "8f4e3c2d-1b0a-5f6e-7c8d-9e0f1a2b3c4d", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("hotelId")
  public java.util.UUID getHotelId() {
    return hotelId;
  }

  public void setHotelId(java.util.UUID hotelId) {
    this.hotelId = hotelId;
  }

  public HotelDetails title(String title) {
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

  public HotelDetails link(@Nullable URI link) {
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

  public HotelDetails description(@Nullable String description) {
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

  public HotelDetails address(@Nullable String address) {
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

  public HotelDetails gpsCoordinates(@Nullable GpsCoordinates gpsCoordinates) {
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

  public HotelDetails city(String city) {
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

  public HotelDetails country(String country) {
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

  public HotelDetails currency(String currency) {
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

  public HotelDetails price(Long price) {
    this.price = price;
    return this;
  }

  /**
   * Минимальная цена за весь период проживания (min_price_per_night × кол-во ночей)
   * @return price
   */
  @NotNull 
  @Schema(name = "price", example = "1408", description = "Минимальная цена за весь период проживания (min_price_per_night × кол-во ночей)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("price")
  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public HotelDetails hotelClass(Integer hotelClass) {
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

  public HotelDetails reviews(HotelReviewsFull reviews) {
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
  public HotelReviewsFull getReviews() {
    return reviews;
  }

  public void setReviews(HotelReviewsFull reviews) {
    this.reviews = reviews;
  }

  public HotelDetails facilities(List<@Valid Facility> facilities) {
    this.facilities = facilities;
    return this;
  }

  public HotelDetails addFacilitiesItem(Facility facilitiesItem) {
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

  public HotelDetails tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public HotelDetails addTagsItem(String tagsItem) {
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
  @Schema(name = "tags", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tags")
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public HotelDetails photos(List<@Valid Photo> photos) {
    this.photos = photos;
    return this;
  }

  public HotelDetails addPhotosItem(Photo photosItem) {
    if (this.photos == null) {
      this.photos = new ArrayList<>();
    }
    this.photos.add(photosItem);
    return this;
  }

  /**
   * Публичные ссылки на фото отеля (пустой массив, если фото нет)
   * @return photos
   */
  @NotNull @Valid 
  @Schema(name = "photos", description = "Публичные ссылки на фото отеля (пустой массив, если фото нет)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("photos")
  public List<@Valid Photo> getPhotos() {
    return photos;
  }

  public void setPhotos(List<@Valid Photo> photos) {
    this.photos = photos;
  }

  public HotelDetails nearbyPlaces(Map<String, List<@Valid NearbyPlace>> nearbyPlaces) {
    this.nearbyPlaces = nearbyPlaces;
    return this;
  }

  public HotelDetails putNearbyPlacesItem(String key, List<@Valid NearbyPlace> nearbyPlacesItem) {
    if (this.nearbyPlaces == null) {
      this.nearbyPlaces = new HashMap<>();
    }
    this.nearbyPlaces.put(key, nearbyPlacesItem);
    return this;
  }

  /**
   * Nearby places grouped by category
   * @return nearbyPlaces
   */
  @NotNull @Valid 
  @Schema(name = "nearbyPlaces", example = "{food=[{title=Pool Bar, distance=2, unit=km}], beaches=[{title=Ladies Club, distance=500, unit=m}]}", description = "Nearby places grouped by category", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nearbyPlaces")
  public Map<String, List<@Valid NearbyPlace>> getNearbyPlaces() {
    return nearbyPlaces;
  }

  public void setNearbyPlaces(Map<String, List<@Valid NearbyPlace>> nearbyPlaces) {
    this.nearbyPlaces = nearbyPlaces;
  }

  public HotelDetails termsPlacement(TermsPlacement termsPlacement) {
    this.termsPlacement = termsPlacement;
    return this;
  }

  /**
   * Get termsPlacement
   * @return termsPlacement
   */
  @NotNull @Valid 
  @Schema(name = "termsPlacement", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("termsPlacement")
  public TermsPlacement getTermsPlacement() {
    return termsPlacement;
  }

  public void setTermsPlacement(TermsPlacement termsPlacement) {
    this.termsPlacement = termsPlacement;
  }

  public HotelDetails rooms(List<@Valid Room> rooms) {
    this.rooms = rooms;
    return this;
  }

  public HotelDetails addRoomsItem(Room roomsItem) {
    if (this.rooms == null) {
      this.rooms = new ArrayList<>();
    }
    this.rooms.add(roomsItem);
    return this;
  }

  /**
   * Доступные номера отеля с тарифами
   * @return rooms
   */
  @NotNull @Valid 
  @Schema(name = "rooms", description = "Доступные номера отеля с тарифами", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("rooms")
  public List<@Valid Room> getRooms() {
    return rooms;
  }

  public void setRooms(List<@Valid Room> rooms) {
    this.rooms = rooms;
  }

  public HotelDetails score(@Nullable HotelScore score) {
    this.score = score;
    return this;
  }

  /**
   * Get score
   * @return score
   */
  @Valid 
  @Schema(name = "score", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("score")
  public @Nullable HotelScore getScore() {
    return score;
  }

  public void setScore(@Nullable HotelScore score) {
    this.score = score;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HotelDetails hotelDetails = (HotelDetails) o;
    return Objects.equals(this.hotelId, hotelDetails.hotelId) &&
        Objects.equals(this.title, hotelDetails.title) &&
        Objects.equals(this.link, hotelDetails.link) &&
        Objects.equals(this.description, hotelDetails.description) &&
        Objects.equals(this.address, hotelDetails.address) &&
        Objects.equals(this.gpsCoordinates, hotelDetails.gpsCoordinates) &&
        Objects.equals(this.city, hotelDetails.city) &&
        Objects.equals(this.country, hotelDetails.country) &&
        Objects.equals(this.currency, hotelDetails.currency) &&
        Objects.equals(this.price, hotelDetails.price) &&
        Objects.equals(this.hotelClass, hotelDetails.hotelClass) &&
        Objects.equals(this.reviews, hotelDetails.reviews) &&
        Objects.equals(this.facilities, hotelDetails.facilities) &&
        Objects.equals(this.tags, hotelDetails.tags) &&
        Objects.equals(this.photos, hotelDetails.photos) &&
        Objects.equals(this.nearbyPlaces, hotelDetails.nearbyPlaces) &&
        Objects.equals(this.termsPlacement, hotelDetails.termsPlacement) &&
        Objects.equals(this.rooms, hotelDetails.rooms) &&
        Objects.equals(this.score, hotelDetails.score);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hotelId, title, link, description, address, gpsCoordinates, city, country, currency, price, hotelClass, reviews, facilities, tags, photos, nearbyPlaces, termsPlacement, rooms, score);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HotelDetails {\n");
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
    sb.append("    photos: ").append(toIndentedString(photos)).append("\n");
    sb.append("    nearbyPlaces: ").append(toIndentedString(nearbyPlaces)).append("\n");
    sb.append("    termsPlacement: ").append(toIndentedString(termsPlacement)).append("\n");
    sb.append("    rooms: ").append(toIndentedString(rooms)).append("\n");
    sb.append("    score: ").append(toIndentedString(score)).append("\n");
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

