package com.tripify.hotels.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.hotels.generated.model.Bathrooms;
import com.tripify.hotels.generated.model.Bed;
import com.tripify.hotels.generated.model.Occupancy;
import com.tripify.hotels.generated.model.Rate;
import com.tripify.hotels.generated.model.RoomArea;
import com.tripify.hotels.generated.model.RoomPhoto;
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
 * Room
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-30T21:24:42.207813975Z[Etc/UTC]", comments = "Generator version: 7.16.0")
public class Room {

  private String roomId;

  private String name;

  private String roomType;

  private @Nullable String description;

  private @Nullable RoomArea area;

  private @Nullable Integer floor;

  private @Nullable Boolean smokingAllowed;

  @Valid
  private List<String> views = new ArrayList<>();

  @Valid
  private List<@Valid RoomPhoto> photos = new ArrayList<>();

  @Valid
  private List<@Valid Bed> beds = new ArrayList<>();

  private @Nullable Bathrooms bathrooms;

  private @Nullable Occupancy occupancy;

  @Valid
  private List<String> amenities = new ArrayList<>();

  @Valid
  private List<String> accessibility = new ArrayList<>();

  @Valid
  private List<@Valid Rate> rates = new ArrayList<>();

  public Room() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Room(String roomId, String name, String roomType, List<@Valid Rate> rates) {
    this.roomId = roomId;
    this.name = name;
    this.roomType = roomType;
    this.rates = rates;
  }

  public Room roomId(String roomId) {
    this.roomId = roomId;
    return this;
  }

  /**
   * Get roomId
   * @return roomId
   */
  @NotNull 
  @Schema(name = "roomId", example = "54c8fa2f-bbbe-4aed-974a-ef18760fe30e", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("roomId")
  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }

  public Room name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull 
  @Schema(name = "name", example = "Standard Room", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Room roomType(String roomType) {
    this.roomType = roomType;
    return this;
  }

  /**
   * Get roomType
   * @return roomType
   */
  @NotNull 
  @Schema(name = "roomType", example = "STANDARD", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("roomType")
  public String getRoomType() {
    return roomType;
  }

  public void setRoomType(String roomType) {
    this.roomType = roomType;
  }

  public Room description(@Nullable String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  
  @Schema(name = "description", example = "Comfortable room with all essential amenities for a pleasant stay", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public @Nullable String getDescription() {
    return description;
  }

  public void setDescription(@Nullable String description) {
    this.description = description;
  }

  public Room area(@Nullable RoomArea area) {
    this.area = area;
    return this;
  }

  /**
   * Get area
   * @return area
   */
  @Valid 
  @Schema(name = "area", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("area")
  public @Nullable RoomArea getArea() {
    return area;
  }

  public void setArea(@Nullable RoomArea area) {
    this.area = area;
  }

  public Room floor(@Nullable Integer floor) {
    this.floor = floor;
    return this;
  }

  /**
   * Get floor
   * @return floor
   */
  
  @Schema(name = "floor", example = "9", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("floor")
  public @Nullable Integer getFloor() {
    return floor;
  }

  public void setFloor(@Nullable Integer floor) {
    this.floor = floor;
  }

  public Room smokingAllowed(@Nullable Boolean smokingAllowed) {
    this.smokingAllowed = smokingAllowed;
    return this;
  }

  /**
   * Get smokingAllowed
   * @return smokingAllowed
   */
  
  @Schema(name = "smokingAllowed", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("smokingAllowed")
  public @Nullable Boolean getSmokingAllowed() {
    return smokingAllowed;
  }

  public void setSmokingAllowed(@Nullable Boolean smokingAllowed) {
    this.smokingAllowed = smokingAllowed;
  }

  public Room views(List<String> views) {
    this.views = views;
    return this;
  }

  public Room addViewsItem(String viewsItem) {
    if (this.views == null) {
      this.views = new ArrayList<>();
    }
    this.views.add(viewsItem);
    return this;
  }

  /**
   * Get views
   * @return views
   */
  
  @Schema(name = "views", example = "[POOL_VIEW, GARDEN_VIEW]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("views")
  public List<String> getViews() {
    return views;
  }

  public void setViews(List<String> views) {
    this.views = views;
  }

  public Room photos(List<@Valid RoomPhoto> photos) {
    this.photos = photos;
    return this;
  }

  public Room addPhotosItem(RoomPhoto photosItem) {
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
  public List<@Valid RoomPhoto> getPhotos() {
    return photos;
  }

  public void setPhotos(List<@Valid RoomPhoto> photos) {
    this.photos = photos;
  }

  public Room beds(List<@Valid Bed> beds) {
    this.beds = beds;
    return this;
  }

  public Room addBedsItem(Bed bedsItem) {
    if (this.beds == null) {
      this.beds = new ArrayList<>();
    }
    this.beds.add(bedsItem);
    return this;
  }

  /**
   * Get beds
   * @return beds
   */
  @Valid 
  @Schema(name = "beds", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("beds")
  public List<@Valid Bed> getBeds() {
    return beds;
  }

  public void setBeds(List<@Valid Bed> beds) {
    this.beds = beds;
  }

  public Room bathrooms(@Nullable Bathrooms bathrooms) {
    this.bathrooms = bathrooms;
    return this;
  }

  /**
   * Get bathrooms
   * @return bathrooms
   */
  @Valid 
  @Schema(name = "bathrooms", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("bathrooms")
  public @Nullable Bathrooms getBathrooms() {
    return bathrooms;
  }

  public void setBathrooms(@Nullable Bathrooms bathrooms) {
    this.bathrooms = bathrooms;
  }

  public Room occupancy(@Nullable Occupancy occupancy) {
    this.occupancy = occupancy;
    return this;
  }

  /**
   * Get occupancy
   * @return occupancy
   */
  @Valid 
  @Schema(name = "occupancy", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("occupancy")
  public @Nullable Occupancy getOccupancy() {
    return occupancy;
  }

  public void setOccupancy(@Nullable Occupancy occupancy) {
    this.occupancy = occupancy;
  }

  public Room amenities(List<String> amenities) {
    this.amenities = amenities;
    return this;
  }

  public Room addAmenitiesItem(String amenitiesItem) {
    if (this.amenities == null) {
      this.amenities = new ArrayList<>();
    }
    this.amenities.add(amenitiesItem);
    return this;
  }

  /**
   * Get amenities
   * @return amenities
   */
  
  @Schema(name = "amenities", example = "[WIFI, AIR_CONDITIONING, BALCONY]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("amenities")
  public List<String> getAmenities() {
    return amenities;
  }

  public void setAmenities(List<String> amenities) {
    this.amenities = amenities;
  }

  public Room accessibility(List<String> accessibility) {
    this.accessibility = accessibility;
    return this;
  }

  public Room addAccessibilityItem(String accessibilityItem) {
    if (this.accessibility == null) {
      this.accessibility = new ArrayList<>();
    }
    this.accessibility.add(accessibilityItem);
    return this;
  }

  /**
   * Get accessibility
   * @return accessibility
   */
  
  @Schema(name = "accessibility", example = "[WHEELCHAIR_ACCESSIBLE]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("accessibility")
  public List<String> getAccessibility() {
    return accessibility;
  }

  public void setAccessibility(List<String> accessibility) {
    this.accessibility = accessibility;
  }

  public Room rates(List<@Valid Rate> rates) {
    this.rates = rates;
    return this;
  }

  public Room addRatesItem(Rate ratesItem) {
    if (this.rates == null) {
      this.rates = new ArrayList<>();
    }
    this.rates.add(ratesItem);
    return this;
  }

  /**
   * Get rates
   * @return rates
   */
  @NotNull @Valid 
  @Schema(name = "rates", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("rates")
  public List<@Valid Rate> getRates() {
    return rates;
  }

  public void setRates(List<@Valid Rate> rates) {
    this.rates = rates;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Room room = (Room) o;
    return Objects.equals(this.roomId, room.roomId) &&
        Objects.equals(this.name, room.name) &&
        Objects.equals(this.roomType, room.roomType) &&
        Objects.equals(this.description, room.description) &&
        Objects.equals(this.area, room.area) &&
        Objects.equals(this.floor, room.floor) &&
        Objects.equals(this.smokingAllowed, room.smokingAllowed) &&
        Objects.equals(this.views, room.views) &&
        Objects.equals(this.photos, room.photos) &&
        Objects.equals(this.beds, room.beds) &&
        Objects.equals(this.bathrooms, room.bathrooms) &&
        Objects.equals(this.occupancy, room.occupancy) &&
        Objects.equals(this.amenities, room.amenities) &&
        Objects.equals(this.accessibility, room.accessibility) &&
        Objects.equals(this.rates, room.rates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomId, name, roomType, description, area, floor, smokingAllowed, views, photos, beds, bathrooms, occupancy, amenities, accessibility, rates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Room {\n");
    sb.append("    roomId: ").append(toIndentedString(roomId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    roomType: ").append(toIndentedString(roomType)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    area: ").append(toIndentedString(area)).append("\n");
    sb.append("    floor: ").append(toIndentedString(floor)).append("\n");
    sb.append("    smokingAllowed: ").append(toIndentedString(smokingAllowed)).append("\n");
    sb.append("    views: ").append(toIndentedString(views)).append("\n");
    sb.append("    photos: ").append(toIndentedString(photos)).append("\n");
    sb.append("    beds: ").append(toIndentedString(beds)).append("\n");
    sb.append("    bathrooms: ").append(toIndentedString(bathrooms)).append("\n");
    sb.append("    occupancy: ").append(toIndentedString(occupancy)).append("\n");
    sb.append("    amenities: ").append(toIndentedString(amenities)).append("\n");
    sb.append("    accessibility: ").append(toIndentedString(accessibility)).append("\n");
    sb.append("    rates: ").append(toIndentedString(rates)).append("\n");
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

