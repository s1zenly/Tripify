package com.tripify.hotels.service.service.mapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.kafka.model.BathroomsDto;
import com.tripify.hotels.service.kafka.model.BedDto;
import com.tripify.hotels.service.kafka.model.CancelPenaltyDto;
import com.tripify.hotels.service.kafka.model.CancellationPolicyDto;
import com.tripify.hotels.service.kafka.model.DiscountDto;
import com.tripify.hotels.service.kafka.model.LoyaltyDto;
import com.tripify.hotels.service.kafka.model.MealPlanDto;
import com.tripify.hotels.service.kafka.model.MoneyDto;
import com.tripify.hotels.service.kafka.model.OccupancyDto;
import com.tripify.hotels.service.kafka.model.RateAvailabilityDto;
import com.tripify.hotels.service.kafka.model.RateDto;
import com.tripify.hotels.service.kafka.model.RatePaymentDto;
import com.tripify.hotels.service.kafka.model.RatePricingDto;
import com.tripify.hotels.service.kafka.model.RoomAreaDto;
import com.tripify.hotels.service.kafka.model.RoomDto;
import com.tripify.hotels.service.model.documents.AreaDocument;
import com.tripify.hotels.service.model.documents.AvailabilityDocument;
import com.tripify.hotels.service.model.documents.BathroomsDocument;
import com.tripify.hotels.service.model.documents.BedDocument;
import com.tripify.hotels.service.model.documents.CancelPenaltyDocument;
import com.tripify.hotels.service.model.documents.CancellationPolicyDocument;
import com.tripify.hotels.service.model.documents.DiscountDocument;
import com.tripify.hotels.service.model.documents.HotelRoomsDocument;
import com.tripify.hotels.service.model.documents.LoyaltyDocument;
import com.tripify.hotels.service.model.documents.MealPlanDocument;
import com.tripify.hotels.service.model.documents.MoneyDocument;
import com.tripify.hotels.service.model.documents.OccupancyDocument;
import com.tripify.hotels.service.model.documents.PaymentDocument;
import com.tripify.hotels.service.model.documents.PricingDocument;
import com.tripify.hotels.service.model.documents.RateDocument;
import com.tripify.hotels.service.model.documents.RoomDocument;
import com.tripify.hotels.service.model.documents.RoomPhotoDocument;
import org.springframework.stereotype.Component;

@Component
public class RoomsDocumentMapper {

    public HotelRoomsDocument toDocument(
            UUID hotelId,
            List<RoomDto> rooms,
            List<List<RoomPhotoDocument>> roomPhotos,
            Instant createdAt,
            Instant updatedAt
    ) {
        List<RoomDocument> roomDocs = List.of();
        if (rooms != null) {
            roomDocs = new java.util.ArrayList<>(rooms.size());
            for (int i = 0; i < rooms.size(); i++) {
                List<RoomPhotoDocument> photos = i < roomPhotos.size() ? roomPhotos.get(i) : List.of();
                roomDocs.add(toRoom(rooms.get(i), photos));
            }
        }

        return new HotelRoomsDocument(
                hotelId.toString(),
                roomDocs,
                createdAt,
                updatedAt
        );
    }

    private RoomDocument toRoom(RoomDto dto, List<RoomPhotoDocument> uploadedPhotos) {
        return new RoomDocument(
                dto.roomId(),
                dto.providerRoomId(),
                dto.name(),
                dto.roomType(),
                dto.description(),
                toArea(dto.area()),
                dto.floor(),
                dto.smokingAllowed(),
                dto.views() != null ? dto.views() : List.of(),
                uploadedPhotos != null ? uploadedPhotos : List.of(),
                toBeds(dto.beds()),
                toBathrooms(dto.bathrooms()),
                toOccupancy(dto.occupancy()),
                dto.amenities() != null ? dto.amenities() : List.of(),
                dto.accessibility() != null ? dto.accessibility() : List.of(),
                toRates(dto.rates())
        );
    }

    private AreaDocument toArea(RoomAreaDto dto) {
        if (dto == null) {
            return null;
        }
        return new AreaDocument(
                toDouble(dto.value()),
                dto.unit()
        );
    }

    private List<BedDocument> toBeds(List<BedDto> beds) {
        if (beds == null || beds.isEmpty()) {
            return List.of();
        }
        return beds.stream()
                .map(b -> new BedDocument(b.type(), b.count()))
                .toList();
    }

    private BathroomsDocument toBathrooms(BathroomsDto dto) {
        if (dto == null) {
            return null;
        }
        return new BathroomsDocument(dto.count(), dto.isPrivate());
    }

    private OccupancyDocument toOccupancy(OccupancyDto dto) {
        if (dto == null) {
            return null;
        }
        return new OccupancyDocument(dto.minAdults(), dto.maxAdults(), dto.maxChildren(), dto.maxGuests());
    }

    private List<RateDocument> toRates(List<RateDto> rates) {
        if (rates == null || rates.isEmpty()) {
            return List.of();
        }
        return rates.stream().map(this::toRate).toList();
    }

    private RateDocument toRate(RateDto dto) {
        return new RateDocument(
                dto.rateId(),
                dto.providerRateId(),
                dto.title(),
                dto.tags() != null ? dto.tags() : List.of(),
                toPricing(dto.pricing()),
                toPayment(dto.payment()),
                toMealPlan(dto.mealPlan()),
                toCancellationPolicy(dto.cancellationPolicy()),
                toAvailability(dto.availability()),
                dto.instantConfirmation(),
                toLoyalty(dto.loyalty()),
                dto.perks() != null ? dto.perks() : List.of()
        );
    }

    private PricingDocument toPricing(RatePricingDto dto) {
        if (dto == null) {
            return null;
        }
        return new PricingDocument(
                toMoney(dto.basePrice()),
                toMoney(dto.taxesAndFees()),
                toMoney(dto.totalPrice()),
                toMoney(dto.pricePerNight()),
                toDiscount(dto.discount())
        );
    }

    private MoneyDocument toMoney(MoneyDto dto) {
        if (dto == null) {
            return null;
        }
        return new MoneyDocument(toDouble(dto.amount()), dto.currency());
    }

    private DiscountDocument toDiscount(DiscountDto dto) {
        if (dto == null) {
            return null;
        }
        return new DiscountDocument(dto.percent(), toDouble(dto.amount()));
    }

    private PaymentDocument toPayment(RatePaymentDto dto) {
        if (dto == null) {
            return null;
        }
        return new PaymentDocument(dto.type(), dto.prepaymentRequired(), dto.cards() != null ? dto.cards() : List.of());
    }

    private MealPlanDocument toMealPlan(MealPlanDto dto) {
        if (dto == null) {
            return null;
        }
        return new MealPlanDocument(dto.type(), dto.description());
    }

    private CancellationPolicyDocument toCancellationPolicy(CancellationPolicyDto dto) {
        if (dto == null) {
            return null;
        }
        return new CancellationPolicyDocument(
                dto.refundable(),
                dto.freeCancellationUntil(),
                toCancelPenalty(dto.cancelPenalty()),
                toCancelPenalty(dto.noShowPenalty())
        );
    }

    private CancelPenaltyDocument toCancelPenalty(CancelPenaltyDto dto) {
        if (dto == null) {
            return null;
        }
        return new CancelPenaltyDocument(dto.type(), toDouble(dto.amount()), dto.percent());
    }

    private AvailabilityDocument toAvailability(RateAvailabilityDto dto) {
        if (dto == null) {
            return null;
        }
        return new AvailabilityDocument(dto.roomsLeft(), dto.soldOut());
    }

    private LoyaltyDocument toLoyalty(LoyaltyDto dto) {
        if (dto == null) {
            return null;
        }
        return new LoyaltyDocument(dto.pointsEarned());
    }

    private static Double toDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : null;
    }
}
