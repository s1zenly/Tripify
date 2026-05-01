package com.tripify.tickets.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.net.URI;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AirlineSummary
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-29T20:25:02.204846+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class AirlineSummary {

  private String code;

  private String name;

  private URI logoUrl;

  public AirlineSummary() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AirlineSummary(String code, String name, URI logoUrl) {
    this.code = code;
    this.name = name;
    this.logoUrl = logoUrl;
  }

  public AirlineSummary code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   * @return code
   */
  @NotNull @Size(min = 2, max = 3) 
  @Schema(name = "code", example = "EK", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public AirlineSummary name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull 
  @Schema(name = "name", example = "Emirates", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AirlineSummary logoUrl(URI logoUrl) {
    this.logoUrl = logoUrl;
    return this;
  }

  /**
   * Get logoUrl
   * @return logoUrl
   */
  @NotNull @Valid 
  @Schema(name = "logo_url", example = "https://pics.avs.io/200/200/EK.png", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("logo_url")
  public URI getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(URI logoUrl) {
    this.logoUrl = logoUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AirlineSummary airlineSummary = (AirlineSummary) o;
    return Objects.equals(this.code, airlineSummary.code) &&
        Objects.equals(this.name, airlineSummary.name) &&
        Objects.equals(this.logoUrl, airlineSummary.logoUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, name, logoUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AirlineSummary {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    logoUrl: ").append(toIndentedString(logoUrl)).append("\n");
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

