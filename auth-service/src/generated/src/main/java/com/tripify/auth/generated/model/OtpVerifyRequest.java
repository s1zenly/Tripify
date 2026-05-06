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
 * OtpVerifyRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-06T22:34:43.832653+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class OtpVerifyRequest {

  private String phoneNumber;

  private String code;

  private @Nullable String deviceName;

  public OtpVerifyRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public OtpVerifyRequest(String phoneNumber, String code) {
    this.phoneNumber = phoneNumber;
    this.code = code;
  }

  public OtpVerifyRequest phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Phone number in E.164 format
   * @return phoneNumber
   */
  @NotNull 
  @Schema(name = "phoneNumber", example = "+79991234567", description = "Phone number in E.164 format", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("phoneNumber")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public OtpVerifyRequest code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   * @return code
   */
  @NotNull @Size(min = 4, max = 8) 
  @Schema(name = "code", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public OtpVerifyRequest deviceName(@Nullable String deviceName) {
    this.deviceName = deviceName;
    return this;
  }

  /**
   * Get deviceName
   * @return deviceName
   */
  @Size(max = 255) 
  @Schema(name = "deviceName", example = "Chrome on macOS", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("deviceName")
  public @Nullable String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(@Nullable String deviceName) {
    this.deviceName = deviceName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OtpVerifyRequest otpVerifyRequest = (OtpVerifyRequest) o;
    return Objects.equals(this.phoneNumber, otpVerifyRequest.phoneNumber) &&
        Objects.equals(this.code, otpVerifyRequest.code) &&
        Objects.equals(this.deviceName, otpVerifyRequest.deviceName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(phoneNumber, code, deviceName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OtpVerifyRequest {\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    deviceName: ").append(toIndentedString(deviceName)).append("\n");
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

