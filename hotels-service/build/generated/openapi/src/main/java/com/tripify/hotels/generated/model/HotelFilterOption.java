package com.tripify.hotels.generated.model;

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
 * HotelFilterOption
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-25T20:44:55.035359+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class HotelFilterOption {

  private String id;

  /**
   * facet — поисковый фасет (hotel_search_facets), terms — условие размещения, attribute — рейтинг/звёзды (колонки hotels) 
   */
  public enum TypeEnum {
    FACET("facet"),
    
    TERMS("terms"),
    
    ATTRIBUTE("attribute");

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

  private Integer count;

  public HotelFilterOption() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public HotelFilterOption(String id, TypeEnum type, String label, Integer count) {
    this.id = id;
    this.type = type;
    this.label = label;
    this.count = count;
  }

  public HotelFilterOption id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Идентификатор для query-параметра filters
   * @return id
   */
  @NotNull 
  @Schema(name = "id", example = "beachfront", description = "Идентификатор для query-параметра filters", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public HotelFilterOption type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * facet — поисковый фасет (hotel_search_facets), terms — условие размещения, attribute — рейтинг/звёзды (колонки hotels) 
   * @return type
   */
  @NotNull 
  @Schema(name = "type", description = "facet — поисковый фасет (hotel_search_facets), terms — условие размещения, attribute — рейтинг/звёзды (колонки hotels) ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public HotelFilterOption label(String label) {
    this.label = label;
    return this;
  }

  /**
   * Подпись для UI
   * @return label
   */
  @NotNull 
  @Schema(name = "label", example = "У моря", description = "Подпись для UI", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("label")
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public HotelFilterOption count(Integer count) {
    this.count = count;
    return this;
  }

  /**
   * Число отелей в выбранном городе
   * minimum: 0
   * @return count
   */
  @NotNull @Min(0) 
  @Schema(name = "count", description = "Число отелей в выбранном городе", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("count")
  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HotelFilterOption hotelFilterOption = (HotelFilterOption) o;
    return Objects.equals(this.id, hotelFilterOption.id) &&
        Objects.equals(this.type, hotelFilterOption.type) &&
        Objects.equals(this.label, hotelFilterOption.label) &&
        Objects.equals(this.count, hotelFilterOption.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, label, count);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HotelFilterOption {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
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

