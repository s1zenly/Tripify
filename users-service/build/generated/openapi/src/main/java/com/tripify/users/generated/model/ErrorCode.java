package com.tripify.users.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets ErrorCode
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-18T23:55:42.822499+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public enum ErrorCode {
  
  PROFILE_NOT_FOUND("PROFILE_NOT_FOUND"),
  
  INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
  
  UPDATE_USER_PROFILE_ERROR("UPDATE_USER_PROFILE_ERROR");

  private final String value;

  ErrorCode(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ErrorCode fromValue(String value) {
    for (ErrorCode b : ErrorCode.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

