package com.tripify.pack.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.pack.generated.model.CheckInOut;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TermsPlacement
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class TermsPlacement {

  private @Nullable CheckInOut checkIn;

  private @Nullable CheckInOut checkOut;

  private @Nullable Boolean petFriendly;

  private @Nullable Boolean partyFriendly;

  private @Nullable String ageRestriction;

  private @Nullable String additionalInfo;

  public TermsPlacement checkIn(@Nullable CheckInOut checkIn) {
    this.checkIn = checkIn;
    return this;
  }

  /**
   * Get checkIn
   * @return checkIn
   */
  @Valid 
  @Schema(name = "checkIn", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("checkIn")
  public @Nullable CheckInOut getCheckIn() {
    return checkIn;
  }

  public void setCheckIn(@Nullable CheckInOut checkIn) {
    this.checkIn = checkIn;
  }

  public TermsPlacement checkOut(@Nullable CheckInOut checkOut) {
    this.checkOut = checkOut;
    return this;
  }

  /**
   * Get checkOut
   * @return checkOut
   */
  @Valid 
  @Schema(name = "checkOut", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("checkOut")
  public @Nullable CheckInOut getCheckOut() {
    return checkOut;
  }

  public void setCheckOut(@Nullable CheckInOut checkOut) {
    this.checkOut = checkOut;
  }

  public TermsPlacement petFriendly(@Nullable Boolean petFriendly) {
    this.petFriendly = petFriendly;
    return this;
  }

  /**
   * Get petFriendly
   * @return petFriendly
   */
  
  @Schema(name = "petFriendly", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("petFriendly")
  public @Nullable Boolean getPetFriendly() {
    return petFriendly;
  }

  public void setPetFriendly(@Nullable Boolean petFriendly) {
    this.petFriendly = petFriendly;
  }

  public TermsPlacement partyFriendly(@Nullable Boolean partyFriendly) {
    this.partyFriendly = partyFriendly;
    return this;
  }

  /**
   * Get partyFriendly
   * @return partyFriendly
   */
  
  @Schema(name = "partyFriendly", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partyFriendly")
  public @Nullable Boolean getPartyFriendly() {
    return partyFriendly;
  }

  public void setPartyFriendly(@Nullable Boolean partyFriendly) {
    this.partyFriendly = partyFriendly;
  }

  public TermsPlacement ageRestriction(@Nullable String ageRestriction) {
    this.ageRestriction = ageRestriction;
    return this;
  }

  /**
   * Get ageRestriction
   * @return ageRestriction
   */
  
  @Schema(name = "ageRestriction", example = "18+", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ageRestriction")
  public @Nullable String getAgeRestriction() {
    return ageRestriction;
  }

  public void setAgeRestriction(@Nullable String ageRestriction) {
    this.ageRestriction = ageRestriction;
  }

  public TermsPlacement additionalInfo(@Nullable String additionalInfo) {
    this.additionalInfo = additionalInfo;
    return this;
  }

  /**
   * Get additionalInfo
   * @return additionalInfo
   */
  
  @Schema(name = "additionalInfo", example = "Check-in starts at 14:00.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("additionalInfo")
  public @Nullable String getAdditionalInfo() {
    return additionalInfo;
  }

  public void setAdditionalInfo(@Nullable String additionalInfo) {
    this.additionalInfo = additionalInfo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TermsPlacement termsPlacement = (TermsPlacement) o;
    return Objects.equals(this.checkIn, termsPlacement.checkIn) &&
        Objects.equals(this.checkOut, termsPlacement.checkOut) &&
        Objects.equals(this.petFriendly, termsPlacement.petFriendly) &&
        Objects.equals(this.partyFriendly, termsPlacement.partyFriendly) &&
        Objects.equals(this.ageRestriction, termsPlacement.ageRestriction) &&
        Objects.equals(this.additionalInfo, termsPlacement.additionalInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(checkIn, checkOut, petFriendly, partyFriendly, ageRestriction, additionalInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TermsPlacement {\n");
    sb.append("    checkIn: ").append(toIndentedString(checkIn)).append("\n");
    sb.append("    checkOut: ").append(toIndentedString(checkOut)).append("\n");
    sb.append("    petFriendly: ").append(toIndentedString(petFriendly)).append("\n");
    sb.append("    partyFriendly: ").append(toIndentedString(partyFriendly)).append("\n");
    sb.append("    ageRestriction: ").append(toIndentedString(ageRestriction)).append("\n");
    sb.append("    additionalInfo: ").append(toIndentedString(additionalInfo)).append("\n");
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

