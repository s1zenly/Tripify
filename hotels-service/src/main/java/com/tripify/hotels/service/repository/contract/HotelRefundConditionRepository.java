package com.tripify.hotels.service.repository.contract;

import java.util.List;
import java.util.UUID;
import com.tripify.hotels.service.model.HotelRefundCondition;

public interface HotelRefundConditionRepository {

    List<HotelRefundCondition> findByHotelId(UUID hotelId);

    void replaceAll(UUID hotelId, List<HotelRefundCondition> refundConditions);
}
