package com.tripify.pack.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.tripify.pack.generated.model.PackCard;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PacksResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class PacksResponse {

  @Valid
  private List<@Valid PackCard> items = new ArrayList<>();

  public PacksResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PacksResponse(List<@Valid PackCard> items) {
    this.items = items;
  }

  public PacksResponse items(List<@Valid PackCard> items) {
    this.items = items;
    return this;
  }

  public PacksResponse addItemsItem(PackCard itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }
    this.items.add(itemsItem);
    return this;
  }

  /**
   * Get items
   * @return items
   */
  @NotNull @Valid 
  @Schema(name = "items", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("items")
  public List<@Valid PackCard> getItems() {
    return items;
  }

  public void setItems(List<@Valid PackCard> items) {
    this.items = items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PacksResponse packsResponse = (PacksResponse) o;
    return Objects.equals(this.items, packsResponse.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PacksResponse {\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
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

