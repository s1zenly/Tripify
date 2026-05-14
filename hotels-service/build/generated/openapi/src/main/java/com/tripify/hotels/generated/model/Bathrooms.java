package com.tripify.hotels.generated.model;

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
 * Bathrooms
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T01:20:13.365045+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class Bathrooms {

  private @Nullable Integer count;

  private @Nullable Boolean _private;

  public Bathrooms count(@Nullable Integer count) {
    this.count = count;
    return this;
  }

  /**
   * Get count
   * @return count
   */
  
  @Schema(name = "count", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("count")
  public @Nullable Integer getCount() {
    return count;
  }

  public void setCount(@Nullable Integer count) {
    this.count = count;
  }

  public Bathrooms _private(@Nullable Boolean _private) {
    this._private = _private;
    return this;
  }

  /**
   * Get _private
   * @return _private
   */
  
  @Schema(name = "private", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("private")
  public @Nullable Boolean getPrivate() {
    return _private;
  }

  public void setPrivate(@Nullable Boolean _private) {
    this._private = _private;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Bathrooms bathrooms = (Bathrooms) o;
    return Objects.equals(this.count, bathrooms.count) &&
        Objects.equals(this._private, bathrooms._private);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, _private);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Bathrooms {\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    _private: ").append(toIndentedString(_private)).append("\n");
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

