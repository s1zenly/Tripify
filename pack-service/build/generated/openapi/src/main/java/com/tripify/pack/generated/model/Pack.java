package com.tripify.pack.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tripify.pack.domain.GenerationMode;
import com.tripify.pack.generated.model.HotelDetails;
import com.tripify.pack.generated.model.PackSearchContext;
import com.tripify.pack.generated.model.TicketDetail;
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
 * Pack
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-01T06:59:57.033665+03:00[Europe/Moscow]", comments = "Generator version: 7.16.0")
public class Pack {

  private UUID id;

  private String generationId;

  private Integer packRevisionId;

  private GenerationMode generationMode;

  private Integer hotelRevisionId;

  private Integer ticketRevisionId;

  private HotelDetails hotel;

  private TicketDetail ticket;

  private PackSearchContext searchContext;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime updatedAt;

  public Pack() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Pack(UUID id, String generationId, Integer packRevisionId, GenerationMode generationMode, Integer hotelRevisionId, Integer ticketRevisionId, HotelDetails hotel, TicketDetail ticket, PackSearchContext searchContext, OffsetDateTime createdAt) {
    this.id = id;
    this.generationId = generationId;
    this.packRevisionId = packRevisionId;
    this.generationMode = generationMode;
    this.hotelRevisionId = hotelRevisionId;
    this.ticketRevisionId = ticketRevisionId;
    this.hotel = hotel;
    this.ticket = ticket;
    this.searchContext = searchContext;
    this.createdAt = createdAt;
  }

  public Pack id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Pack revision identifier
   * @return id
   */
  @NotNull @Valid 
  @Schema(name = "id", example = "8f4e3c2d-1b0a-5f6e-7c8d-9e0f1a2b3c4d", description = "Pack revision identifier", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Pack generationId(String generationId) {
    this.generationId = generationId;
    return this;
  }

  /**
   * Get generationId
   * @return generationId
   */
  @NotNull 
  @Schema(name = "generation_id", example = "gen_a8f91d_ab91ff23", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("generation_id")
  public String getGenerationId() {
    return generationId;
  }

  public void setGenerationId(String generationId) {
    this.generationId = generationId;
  }

  public Pack packRevisionId(Integer packRevisionId) {
    this.packRevisionId = packRevisionId;
    return this;
  }

  /**
   * Get packRevisionId
   * minimum: 1
   * @return packRevisionId
   */
  @NotNull @Min(1) 
  @Schema(name = "pack_revision_id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pack_revision_id")
  public Integer getPackRevisionId() {
    return packRevisionId;
  }

  public void setPackRevisionId(Integer packRevisionId) {
    this.packRevisionId = packRevisionId;
  }

  public Pack generationMode(GenerationMode generationMode) {
    this.generationMode = generationMode;
    return this;
  }

  /**
   * Get generationMode
   * @return generationMode
   */
  @NotNull @Valid 
  @Schema(name = "generation_mode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("generation_mode")
  public GenerationMode getGenerationMode() {
    return generationMode;
  }

  public void setGenerationMode(GenerationMode generationMode) {
    this.generationMode = generationMode;
  }

  public Pack hotelRevisionId(Integer hotelRevisionId) {
    this.hotelRevisionId = hotelRevisionId;
    return this;
  }

  /**
   * Get hotelRevisionId
   * minimum: 1
   * @return hotelRevisionId
   */
  @NotNull @Min(1) 
  @Schema(name = "hotel_revision_id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("hotel_revision_id")
  public Integer getHotelRevisionId() {
    return hotelRevisionId;
  }

  public void setHotelRevisionId(Integer hotelRevisionId) {
    this.hotelRevisionId = hotelRevisionId;
  }

  public Pack ticketRevisionId(Integer ticketRevisionId) {
    this.ticketRevisionId = ticketRevisionId;
    return this;
  }

  /**
   * Get ticketRevisionId
   * minimum: 1
   * @return ticketRevisionId
   */
  @NotNull @Min(1) 
  @Schema(name = "ticket_revision_id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("ticket_revision_id")
  public Integer getTicketRevisionId() {
    return ticketRevisionId;
  }

  public void setTicketRevisionId(Integer ticketRevisionId) {
    this.ticketRevisionId = ticketRevisionId;
  }

  public Pack hotel(HotelDetails hotel) {
    this.hotel = hotel;
    return this;
  }

  /**
   * Get hotel
   * @return hotel
   */
  @NotNull @Valid 
  @Schema(name = "hotel", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("hotel")
  public HotelDetails getHotel() {
    return hotel;
  }

  public void setHotel(HotelDetails hotel) {
    this.hotel = hotel;
  }

  public Pack ticket(TicketDetail ticket) {
    this.ticket = ticket;
    return this;
  }

  /**
   * Get ticket
   * @return ticket
   */
  @NotNull @Valid 
  @Schema(name = "ticket", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("ticket")
  public TicketDetail getTicket() {
    return ticket;
  }

  public void setTicket(TicketDetail ticket) {
    this.ticket = ticket;
  }

  public Pack searchContext(PackSearchContext searchContext) {
    this.searchContext = searchContext;
    return this;
  }

  /**
   * Get searchContext
   * @return searchContext
   */
  @NotNull @Valid 
  @Schema(name = "search_context", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("search_context")
  public PackSearchContext getSearchContext() {
    return searchContext;
  }

  public void setSearchContext(PackSearchContext searchContext) {
    this.searchContext = searchContext;
  }

  public Pack createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
   */
  @NotNull @Valid 
  @Schema(name = "created_at", example = "2026-05-31T20:20:25.090Z", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("created_at")
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Pack updatedAt(@Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
   */
  @Valid 
  @Schema(name = "updated_at", example = "2026-05-31T20:20:25.090Z", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updated_at")
  public @Nullable OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(@Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pack pack = (Pack) o;
    return Objects.equals(this.id, pack.id) &&
        Objects.equals(this.generationId, pack.generationId) &&
        Objects.equals(this.packRevisionId, pack.packRevisionId) &&
        Objects.equals(this.generationMode, pack.generationMode) &&
        Objects.equals(this.hotelRevisionId, pack.hotelRevisionId) &&
        Objects.equals(this.ticketRevisionId, pack.ticketRevisionId) &&
        Objects.equals(this.hotel, pack.hotel) &&
        Objects.equals(this.ticket, pack.ticket) &&
        Objects.equals(this.searchContext, pack.searchContext) &&
        Objects.equals(this.createdAt, pack.createdAt) &&
        Objects.equals(this.updatedAt, pack.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, generationId, packRevisionId, generationMode, hotelRevisionId, ticketRevisionId, hotel, ticket, searchContext, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Pack {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    generationId: ").append(toIndentedString(generationId)).append("\n");
    sb.append("    packRevisionId: ").append(toIndentedString(packRevisionId)).append("\n");
    sb.append("    generationMode: ").append(toIndentedString(generationMode)).append("\n");
    sb.append("    hotelRevisionId: ").append(toIndentedString(hotelRevisionId)).append("\n");
    sb.append("    ticketRevisionId: ").append(toIndentedString(ticketRevisionId)).append("\n");
    sb.append("    hotel: ").append(toIndentedString(hotel)).append("\n");
    sb.append("    ticket: ").append(toIndentedString(ticket)).append("\n");
    sb.append("    searchContext: ").append(toIndentedString(searchContext)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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

