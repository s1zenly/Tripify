package com.tripify.auth.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import java.util.UUID;
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
 * SessionResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-06T22:34:43.832653+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class SessionResponse {

  private UUID id;

  private @Nullable String deviceName;

  private @Nullable String ipAddress;

  private @Nullable String userAgent;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime lastUsedAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime expiresAt;

  private Boolean current;

  public SessionResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SessionResponse(UUID id, OffsetDateTime createdAt, OffsetDateTime expiresAt, Boolean current) {
    this.id = id;
    this.createdAt = createdAt;
    this.expiresAt = expiresAt;
    this.current = current;
  }

  public SessionResponse id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @NotNull @Valid 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public SessionResponse deviceName(@Nullable String deviceName) {
    this.deviceName = deviceName;
    return this;
  }

  /**
   * Get deviceName
   * @return deviceName
   */
  
  @Schema(name = "deviceName", example = "Chrome on macOS", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("deviceName")
  public @Nullable String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(@Nullable String deviceName) {
    this.deviceName = deviceName;
  }

  public SessionResponse ipAddress(@Nullable String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

  /**
   * Get ipAddress
   * @return ipAddress
   */
  
  @Schema(name = "ipAddress", example = "127.0.0.1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ipAddress")
  public @Nullable String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(@Nullable String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public SessionResponse userAgent(@Nullable String userAgent) {
    this.userAgent = userAgent;
    return this;
  }

  /**
   * Get userAgent
   * @return userAgent
   */
  
  @Schema(name = "userAgent", example = "Mozilla/5.0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("userAgent")
  public @Nullable String getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(@Nullable String userAgent) {
    this.userAgent = userAgent;
  }

  public SessionResponse createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
   */
  @NotNull @Valid 
  @Schema(name = "createdAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("createdAt")
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public SessionResponse lastUsedAt(@Nullable OffsetDateTime lastUsedAt) {
    this.lastUsedAt = lastUsedAt;
    return this;
  }

  /**
   * Get lastUsedAt
   * @return lastUsedAt
   */
  @Valid 
  @Schema(name = "lastUsedAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastUsedAt")
  public @Nullable OffsetDateTime getLastUsedAt() {
    return lastUsedAt;
  }

  public void setLastUsedAt(@Nullable OffsetDateTime lastUsedAt) {
    this.lastUsedAt = lastUsedAt;
  }

  public SessionResponse expiresAt(OffsetDateTime expiresAt) {
    this.expiresAt = expiresAt;
    return this;
  }

  /**
   * Get expiresAt
   * @return expiresAt
   */
  @NotNull @Valid 
  @Schema(name = "expiresAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("expiresAt")
  public OffsetDateTime getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(OffsetDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }

  public SessionResponse current(Boolean current) {
    this.current = current;
    return this;
  }

  /**
   * Get current
   * @return current
   */
  @NotNull 
  @Schema(name = "current", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("current")
  public Boolean getCurrent() {
    return current;
  }

  public void setCurrent(Boolean current) {
    this.current = current;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SessionResponse sessionResponse = (SessionResponse) o;
    return Objects.equals(this.id, sessionResponse.id) &&
        Objects.equals(this.deviceName, sessionResponse.deviceName) &&
        Objects.equals(this.ipAddress, sessionResponse.ipAddress) &&
        Objects.equals(this.userAgent, sessionResponse.userAgent) &&
        Objects.equals(this.createdAt, sessionResponse.createdAt) &&
        Objects.equals(this.lastUsedAt, sessionResponse.lastUsedAt) &&
        Objects.equals(this.expiresAt, sessionResponse.expiresAt) &&
        Objects.equals(this.current, sessionResponse.current);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, deviceName, ipAddress, userAgent, createdAt, lastUsedAt, expiresAt, current);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SessionResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    deviceName: ").append(toIndentedString(deviceName)).append("\n");
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    userAgent: ").append(toIndentedString(userAgent)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    lastUsedAt: ").append(toIndentedString(lastUsedAt)).append("\n");
    sb.append("    expiresAt: ").append(toIndentedString(expiresAt)).append("\n");
    sb.append("    current: ").append(toIndentedString(current)).append("\n");
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

