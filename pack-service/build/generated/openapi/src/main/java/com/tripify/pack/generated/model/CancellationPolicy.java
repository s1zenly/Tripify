package com.tripify.pack.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.pack.generated.model.CancelPenalty;
import java.time.OffsetDateTime;
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
 * CancellationPolicy
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class CancellationPolicy {

  private @Nullable Boolean refundable;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime freeCancellationUntil;

  private @Nullable CancelPenalty cancelPenalty;

  private @Nullable CancelPenalty noShowPenalty;

  public CancellationPolicy refundable(@Nullable Boolean refundable) {
    this.refundable = refundable;
    return this;
  }

  /**
   * Get refundable
   * @return refundable
   */
  
  @Schema(name = "refundable", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("refundable")
  public @Nullable Boolean getRefundable() {
    return refundable;
  }

  public void setRefundable(@Nullable Boolean refundable) {
    this.refundable = refundable;
  }

  public CancellationPolicy freeCancellationUntil(@Nullable OffsetDateTime freeCancellationUntil) {
    this.freeCancellationUntil = freeCancellationUntil;
    return this;
  }

  /**
   * Get freeCancellationUntil
   * @return freeCancellationUntil
   */
  @Valid 
  @Schema(name = "freeCancellationUntil", example = "2026-06-05T16:32:07Z", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("freeCancellationUntil")
  public @Nullable OffsetDateTime getFreeCancellationUntil() {
    return freeCancellationUntil;
  }

  public void setFreeCancellationUntil(@Nullable OffsetDateTime freeCancellationUntil) {
    this.freeCancellationUntil = freeCancellationUntil;
  }

  public CancellationPolicy cancelPenalty(@Nullable CancelPenalty cancelPenalty) {
    this.cancelPenalty = cancelPenalty;
    return this;
  }

  /**
   * Get cancelPenalty
   * @return cancelPenalty
   */
  @Valid 
  @Schema(name = "cancelPenalty", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cancelPenalty")
  public @Nullable CancelPenalty getCancelPenalty() {
    return cancelPenalty;
  }

  public void setCancelPenalty(@Nullable CancelPenalty cancelPenalty) {
    this.cancelPenalty = cancelPenalty;
  }

  public CancellationPolicy noShowPenalty(@Nullable CancelPenalty noShowPenalty) {
    this.noShowPenalty = noShowPenalty;
    return this;
  }

  /**
   * Get noShowPenalty
   * @return noShowPenalty
   */
  @Valid 
  @Schema(name = "noShowPenalty", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("noShowPenalty")
  public @Nullable CancelPenalty getNoShowPenalty() {
    return noShowPenalty;
  }

  public void setNoShowPenalty(@Nullable CancelPenalty noShowPenalty) {
    this.noShowPenalty = noShowPenalty;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CancellationPolicy cancellationPolicy = (CancellationPolicy) o;
    return Objects.equals(this.refundable, cancellationPolicy.refundable) &&
        Objects.equals(this.freeCancellationUntil, cancellationPolicy.freeCancellationUntil) &&
        Objects.equals(this.cancelPenalty, cancellationPolicy.cancelPenalty) &&
        Objects.equals(this.noShowPenalty, cancellationPolicy.noShowPenalty);
  }

  @Override
  public int hashCode() {
    return Objects.hash(refundable, freeCancellationUntil, cancelPenalty, noShowPenalty);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CancellationPolicy {\n");
    sb.append("    refundable: ").append(toIndentedString(refundable)).append("\n");
    sb.append("    freeCancellationUntil: ").append(toIndentedString(freeCancellationUntil)).append("\n");
    sb.append("    cancelPenalty: ").append(toIndentedString(cancelPenalty)).append("\n");
    sb.append("    noShowPenalty: ").append(toIndentedString(noShowPenalty)).append("\n");
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

