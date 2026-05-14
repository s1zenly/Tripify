package com.tripify.pack.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripify.pack.generated.model.HotelDetails;
import com.tripify.pack.generated.model.Pack;
import com.tripify.pack.generated.model.PackCard;
import com.tripify.pack.generated.model.PackCardDate;
import com.tripify.pack.generated.model.PackCardHotel;
import com.tripify.pack.generated.model.PackCardPrice;
import com.tripify.pack.generated.model.PackSearchContext;
import com.tripify.pack.generated.model.PacksResponse;
import com.tripify.pack.generated.model.TicketDetail;
import com.tripify.pack.persistence.mongo.document.PackSnapshotDocument;
import com.tripify.pack.persistence.mongo.document.SearchContextDocument;
import com.tripify.pack.persistence.postgres.model.PackRevisionRecord;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class PackApiMapper {

    private final ObjectMapper objectMapper;

    public PacksResponse toPacksResponse(java.util.List<PackCard> items) {
        return new PacksResponse().items(items);
    }

    public PackCard toPackCard(PackRevisionRecord revision, PackSnapshotDocument snapshot) {
        SearchContextDocument searchContext = snapshot.searchContext();
        Document hotel = snapshot.hotel();
        Document ticket = snapshot.ticket();

        String imageUrl = extractFirstPhotoUrl(hotel);

        return new PackCard(
                revision.id(),
                searchContext.destinationCountry(),
                searchContext.destinationCity(),
                toPackCardHotel(hotel),
                toPackCardDate(searchContext),
                calculateDurationDays(searchContext.dateFrom(), searchContext.dateTo()),
                toPackCardPrice(hotel, ticket, searchContext.currency())
        ).imagePackUrl(toUri(imageUrl));
    }

    public Pack toPack(PackRevisionRecord revision, PackSnapshotDocument snapshot) {
        return new Pack(
                revision.id(),
                revision.generationId(),
                revision.packRevisionId(),
                revision.generationMode(),
                revision.hotelRevisionId(),
                revision.ticketRevisionId(),
                toHotelDetails(snapshot.hotel()),
                toTicketDetail(snapshot.ticket()),
                toSearchContext(snapshot.searchContext()),
                toOffsetDateTime(revision.createdAt())
        ).updatedAt(toOffsetDateTime(revision.updatedAt()));
    }

    private PackCardHotel toPackCardHotel(Document hotel) {
        return new PackCardHotel(
                readString(hotel, "title"),
                readInteger(hotel, "hotel_class", "hotelClass")
        )
                .mealType(extractMealType(hotel))
                .imageUrl(toUri(extractFirstPhotoUrl(hotel)));
    }

    private PackCardDate toPackCardDate(SearchContextDocument searchContext) {
        return new PackCardDate(searchContext.dateFrom(), searchContext.dateTo());
    }

    private PackCardPrice toPackCardPrice(Document hotel, Document ticket, String currency) {
        long hotelPrice = readLong(hotel, "price");
        long ticketPrice = readNestedLong(ticket, "price", "amount");
        return new PackCardPrice(hotelPrice + ticketPrice, currency);
    }

    private static int calculateDurationDays(LocalDate dateFrom, LocalDate dateTo) {
        long days = ChronoUnit.DAYS.between(dateFrom, dateTo);
        return (int) Math.max(days, 1L);
    }

    private String extractMealType(Document hotel) {
        JsonNode rooms = toJsonNode(hotel).path("rooms");
        if (!rooms.isArray() || rooms.isEmpty()) {
            return null;
        }

        JsonNode rates = rooms.get(0).path("rates");
        if (!rates.isArray() || rates.isEmpty()) {
            return null;
        }

        JsonNode mealPlan = rates.get(0).path("meal_plan");
        if (mealPlan.isMissingNode()) {
            mealPlan = rates.get(0).path("mealPlan");
        }

        JsonNode type = mealPlan.path("type");
        return type.isMissingNode() || type.isNull() ? null : type.asText();
    }

    private String extractFirstPhotoUrl(Document hotel) {
        JsonNode photos = toJsonNode(hotel).path("photos");
        if (!photos.isArray() || photos.isEmpty()) {
            return null;
        }

        JsonNode link = photos.get(0).path("link");
        return link.isMissingNode() || link.isNull() ? null : link.asText();
    }

    private HotelDetails toHotelDetails(Document document) {
        return objectMapper.convertValue(document, HotelDetails.class);
    }

    private TicketDetail toTicketDetail(Document document) {
        return objectMapper.convertValue(document, TicketDetail.class);
    }

    private PackSearchContext toSearchContext(SearchContextDocument searchContext) {
        return objectMapper.convertValue(searchContext, PackSearchContext.class);
    }

    private JsonNode toJsonNode(Document document) {
        if (document == null) {
            return objectMapper.createObjectNode();
        }
        return objectMapper.convertValue(document, JsonNode.class);
    }

    private static String readString(Document document, String... keys) {
        if (document == null) {
            return null;
        }
        for (String key : keys) {
            if (document.containsKey(key)) {
                Object value = document.get(key);
                return value == null ? null : value.toString();
            }
        }
        return null;
    }

    private static Integer readInteger(Document document, String... keys) {
        if (document == null) {
            return null;
        }
        for (String key : keys) {
            if (document.containsKey(key)) {
                Object value = document.get(key);
                if (value instanceof Number number) {
                    return number.intValue();
                }
            }
        }
        return null;
    }

    private static long readLong(Document document, String key) {
        if (document == null || !document.containsKey(key)) {
            return 0L;
        }
        Object value = document.get(key);
        return value instanceof Number number ? number.longValue() : 0L;
    }

    private static long readNestedLong(Document document, String objectKey, String fieldKey) {
        if (document == null || !document.containsKey(objectKey)) {
            return 0L;
        }
        Object nested = document.get(objectKey);
        if (!(nested instanceof Document nestedDocument)) {
            return 0L;
        }
        return readLong(nestedDocument, fieldKey);
    }

    private static URI toUri(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return URI.create(value);
    }

    private static OffsetDateTime toOffsetDateTime(Instant instant) {
        return OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
