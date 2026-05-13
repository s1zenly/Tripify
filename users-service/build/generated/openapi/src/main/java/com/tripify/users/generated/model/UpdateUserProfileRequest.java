package com.tripify.users.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * UpdateUserProfileRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-30T21:05:59.653411027Z[Etc/UTC]", comments = "Generator version: 7.16.0")
public class UpdateUserProfileRequest {

  private @Nullable String email;

  private @Nullable String firstName;

  private @Nullable String lastName;

  private @Nullable String city;

  private @Nullable String country;

  private @Nullable String currency;

  private @Nullable String citizenship;

  public UpdateUserProfileRequest email(@Nullable String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  @jakarta.validation.constraints.Email 
  @Schema(name = "email", example = "lev@example.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public @Nullable String getEmail() {
    return email;
  }

  public void setEmail(@Nullable String email) {
    this.email = email;
  }

  public UpdateUserProfileRequest firstName(@Nullable String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   */
  
  @Schema(name = "firstName", example = "Лев", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("firstName")
  public @Nullable String getFirstName() {
    return firstName;
  }

  public void setFirstName(@Nullable String firstName) {
    this.firstName = firstName;
  }

  public UpdateUserProfileRequest lastName(@Nullable String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   */
  
  @Schema(name = "lastName", example = "Гурьков", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastName")
  public @Nullable String getLastName() {
    return lastName;
  }

  public void setLastName(@Nullable String lastName) {
    this.lastName = lastName;
  }

  public UpdateUserProfileRequest city(@Nullable String city) {
    this.city = city;
    return this;
  }

  /**
   * Get city
   * @return city
   */
  
  @Schema(name = "city", example = "Москва", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("city")
  public @Nullable String getCity() {
    return city;
  }

  public void setCity(@Nullable String city) {
    this.city = city;
  }

  public UpdateUserProfileRequest country(@Nullable String country) {
    this.country = country;
    return this;
  }

  /**
   * Get country
   * @return country
   */
  
  @Schema(name = "country", example = "Россия", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("country")
  public @Nullable String getCountry() {
    return country;
  }

  public void setCountry(@Nullable String country) {
    this.country = country;
  }

  public UpdateUserProfileRequest currency(@Nullable String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Get currency
   * @return currency
   */
  
  @Schema(name = "currency", example = "Российский рубль", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("currency")
  public @Nullable String getCurrency() {
    return currency;
  }

  public void setCurrency(@Nullable String currency) {
    this.currency = currency;
  }

  public UpdateUserProfileRequest citizenship(@Nullable String citizenship) {
    this.citizenship = citizenship;
    return this;
  }

  /**
   * Get citizenship
   * @return citizenship
   */
  
  @Schema(name = "citizenship", example = "РФ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("citizenship")
  public @Nullable String getCitizenship() {
    return citizenship;
  }

  public void setCitizenship(@Nullable String citizenship) {
    this.citizenship = citizenship;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateUserProfileRequest updateUserProfileRequest = (UpdateUserProfileRequest) o;
    return Objects.equals(this.email, updateUserProfileRequest.email) &&
        Objects.equals(this.firstName, updateUserProfileRequest.firstName) &&
        Objects.equals(this.lastName, updateUserProfileRequest.lastName) &&
        Objects.equals(this.city, updateUserProfileRequest.city) &&
        Objects.equals(this.country, updateUserProfileRequest.country) &&
        Objects.equals(this.currency, updateUserProfileRequest.currency) &&
        Objects.equals(this.citizenship, updateUserProfileRequest.citizenship);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, firstName, lastName, city, country, currency, citizenship);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateUserProfileRequest {\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    citizenship: ").append(toIndentedString(citizenship)).append("\n");
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

