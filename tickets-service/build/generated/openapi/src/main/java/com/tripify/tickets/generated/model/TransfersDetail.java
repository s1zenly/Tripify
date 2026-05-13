package com.tripify.tickets.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.tickets.generated.model.TransferPlace;
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
 * TransfersDetail
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-30T21:33:13.369032554Z[Etc/UTC]", comments = "Generator version: 7.16.0")
public class TransfersDetail {

  private Integer count;

  private Boolean isDirect;

  private Boolean hasOvernight;

  private Boolean requiresAirportChange;

  private Boolean requiresSelfTransfer;

  @Valid
  private List<@Valid TransferPlace> places = new ArrayList<>();

  public TransfersDetail() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TransfersDetail(Integer count, Boolean isDirect, Boolean hasOvernight, Boolean requiresAirportChange, Boolean requiresSelfTransfer, List<@Valid TransferPlace> places) {
    this.count = count;
    this.isDirect = isDirect;
    this.hasOvernight = hasOvernight;
    this.requiresAirportChange = requiresAirportChange;
    this.requiresSelfTransfer = requiresSelfTransfer;
    this.places = places;
  }

  public TransfersDetail count(Integer count) {
    this.count = count;
    return this;
  }

  /**
   * Get count
   * minimum: 0
   * @return count
   */
  @NotNull @Min(0) 
  @Schema(name = "count", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("count")
  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public TransfersDetail isDirect(Boolean isDirect) {
    this.isDirect = isDirect;
    return this;
  }

  /**
   * Get isDirect
   * @return isDirect
   */
  @NotNull 
  @Schema(name = "is_direct", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("is_direct")
  public Boolean getIsDirect() {
    return isDirect;
  }

  public void setIsDirect(Boolean isDirect) {
    this.isDirect = isDirect;
  }

  public TransfersDetail hasOvernight(Boolean hasOvernight) {
    this.hasOvernight = hasOvernight;
    return this;
  }

  /**
   * Get hasOvernight
   * @return hasOvernight
   */
  @NotNull 
  @Schema(name = "has_overnight", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("has_overnight")
  public Boolean getHasOvernight() {
    return hasOvernight;
  }

  public void setHasOvernight(Boolean hasOvernight) {
    this.hasOvernight = hasOvernight;
  }

  public TransfersDetail requiresAirportChange(Boolean requiresAirportChange) {
    this.requiresAirportChange = requiresAirportChange;
    return this;
  }

  /**
   * Get requiresAirportChange
   * @return requiresAirportChange
   */
  @NotNull 
  @Schema(name = "requires_airport_change", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("requires_airport_change")
  public Boolean getRequiresAirportChange() {
    return requiresAirportChange;
  }

  public void setRequiresAirportChange(Boolean requiresAirportChange) {
    this.requiresAirportChange = requiresAirportChange;
  }

  public TransfersDetail requiresSelfTransfer(Boolean requiresSelfTransfer) {
    this.requiresSelfTransfer = requiresSelfTransfer;
    return this;
  }

  /**
   * Get requiresSelfTransfer
   * @return requiresSelfTransfer
   */
  @NotNull 
  @Schema(name = "requires_self_transfer", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("requires_self_transfer")
  public Boolean getRequiresSelfTransfer() {
    return requiresSelfTransfer;
  }

  public void setRequiresSelfTransfer(Boolean requiresSelfTransfer) {
    this.requiresSelfTransfer = requiresSelfTransfer;
  }

  public TransfersDetail places(List<@Valid TransferPlace> places) {
    this.places = places;
    return this;
  }

  public TransfersDetail addPlacesItem(TransferPlace placesItem) {
    if (this.places == null) {
      this.places = new ArrayList<>();
    }
    this.places.add(placesItem);
    return this;
  }

  /**
   * Get places
   * @return places
   */
  @NotNull @Valid 
  @Schema(name = "places", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("places")
  public List<@Valid TransferPlace> getPlaces() {
    return places;
  }

  public void setPlaces(List<@Valid TransferPlace> places) {
    this.places = places;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransfersDetail transfersDetail = (TransfersDetail) o;
    return Objects.equals(this.count, transfersDetail.count) &&
        Objects.equals(this.isDirect, transfersDetail.isDirect) &&
        Objects.equals(this.hasOvernight, transfersDetail.hasOvernight) &&
        Objects.equals(this.requiresAirportChange, transfersDetail.requiresAirportChange) &&
        Objects.equals(this.requiresSelfTransfer, transfersDetail.requiresSelfTransfer) &&
        Objects.equals(this.places, transfersDetail.places);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, isDirect, hasOvernight, requiresAirportChange, requiresSelfTransfer, places);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransfersDetail {\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    isDirect: ").append(toIndentedString(isDirect)).append("\n");
    sb.append("    hasOvernight: ").append(toIndentedString(hasOvernight)).append("\n");
    sb.append("    requiresAirportChange: ").append(toIndentedString(requiresAirportChange)).append("\n");
    sb.append("    requiresSelfTransfer: ").append(toIndentedString(requiresSelfTransfer)).append("\n");
    sb.append("    places: ").append(toIndentedString(places)).append("\n");
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

