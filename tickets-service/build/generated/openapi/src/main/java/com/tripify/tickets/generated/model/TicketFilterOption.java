package com.tripify.tickets.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TicketFilterOption
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-30T21:33:13.369032554Z[Etc/UTC]", comments = "Generator version: 7.16.0")
public class TicketFilterOption {

  private String id;

  /**
   * facet — характеристики перелёта (пересадки, layover), terms — условия тарифа и багажа 
   */
  public enum TypeEnum {
    FACET("facet"),
    
    TERMS("terms");

    private final String value;

    TypeEnum(String value) {
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
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TypeEnum type;

  private String label;

  public TicketFilterOption() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TicketFilterOption(String id, TypeEnum type, String label) {
    this.id = id;
    this.type = type;
    this.label = label;
  }

  public TicketFilterOption id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Идентификатор для query-параметра filters
   * @return id
   */
  @NotNull 
  @Schema(name = "id", example = "direct", description = "Идентификатор для query-параметра filters", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public TicketFilterOption type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * facet — характеристики перелёта (пересадки, layover), terms — условия тарифа и багажа 
   * @return type
   */
  @NotNull 
  @Schema(name = "type", description = "facet — характеристики перелёта (пересадки, layover), terms — условия тарифа и багажа ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public TicketFilterOption label(String label) {
    this.label = label;
    return this;
  }

  /**
   * Подпись для UI
   * @return label
   */
  @NotNull 
  @Schema(name = "label", example = "Без пересадок", description = "Подпись для UI", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("label")
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketFilterOption ticketFilterOption = (TicketFilterOption) o;
    return Objects.equals(this.id, ticketFilterOption.id) &&
        Objects.equals(this.type, ticketFilterOption.type) &&
        Objects.equals(this.label, ticketFilterOption.label);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, label);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketFilterOption {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
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

