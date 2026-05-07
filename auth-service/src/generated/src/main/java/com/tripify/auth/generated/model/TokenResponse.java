package com.tripify.auth.generated.model;

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
 * TokenResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-07T23:12:49.949733+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class TokenResponse {

  private String tokenType;

  private String accessToken;

  private Long accessExpiresIn;

  private String refreshToken;

  private Long refreshExpiresIn;

  public TokenResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TokenResponse(String tokenType, String accessToken, Long accessExpiresIn, String refreshToken, Long refreshExpiresIn) {
    this.tokenType = tokenType;
    this.accessToken = accessToken;
    this.accessExpiresIn = accessExpiresIn;
    this.refreshToken = refreshToken;
    this.refreshExpiresIn = refreshExpiresIn;
  }

  public TokenResponse tokenType(String tokenType) {
    this.tokenType = tokenType;
    return this;
  }

  /**
   * Get tokenType
   * @return tokenType
   */
  @NotNull 
  @Schema(name = "tokenType", example = "Bearer", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tokenType")
  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public TokenResponse accessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  /**
   * Get accessToken
   * @return accessToken
   */
  @NotNull 
  @Schema(name = "accessToken", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("accessToken")
  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public TokenResponse accessExpiresIn(Long accessExpiresIn) {
    this.accessExpiresIn = accessExpiresIn;
    return this;
  }

  /**
   * Get accessExpiresIn
   * @return accessExpiresIn
   */
  @NotNull 
  @Schema(name = "accessExpiresIn", example = "900", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("accessExpiresIn")
  public Long getAccessExpiresIn() {
    return accessExpiresIn;
  }

  public void setAccessExpiresIn(Long accessExpiresIn) {
    this.accessExpiresIn = accessExpiresIn;
  }

  public TokenResponse refreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

  /**
   * Get refreshToken
   * @return refreshToken
   */
  @NotNull 
  @Schema(name = "refreshToken", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("refreshToken")
  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public TokenResponse refreshExpiresIn(Long refreshExpiresIn) {
    this.refreshExpiresIn = refreshExpiresIn;
    return this;
  }

  /**
   * Get refreshExpiresIn
   * @return refreshExpiresIn
   */
  @NotNull 
  @Schema(name = "refreshExpiresIn", example = "2592000", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("refreshExpiresIn")
  public Long getRefreshExpiresIn() {
    return refreshExpiresIn;
  }

  public void setRefreshExpiresIn(Long refreshExpiresIn) {
    this.refreshExpiresIn = refreshExpiresIn;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TokenResponse tokenResponse = (TokenResponse) o;
    return Objects.equals(this.tokenType, tokenResponse.tokenType) &&
        Objects.equals(this.accessToken, tokenResponse.accessToken) &&
        Objects.equals(this.accessExpiresIn, tokenResponse.accessExpiresIn) &&
        Objects.equals(this.refreshToken, tokenResponse.refreshToken) &&
        Objects.equals(this.refreshExpiresIn, tokenResponse.refreshExpiresIn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tokenType, accessToken, accessExpiresIn, refreshToken, refreshExpiresIn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenResponse {\n");
    sb.append("    tokenType: ").append(toIndentedString(tokenType)).append("\n");
    sb.append("    accessToken: ").append(toIndentedString(accessToken)).append("\n");
    sb.append("    accessExpiresIn: ").append(toIndentedString(accessExpiresIn)).append("\n");
    sb.append("    refreshToken: ").append(toIndentedString(refreshToken)).append("\n");
    sb.append("    refreshExpiresIn: ").append(toIndentedString(refreshExpiresIn)).append("\n");
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

