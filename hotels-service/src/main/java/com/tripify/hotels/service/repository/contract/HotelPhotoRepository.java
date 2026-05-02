package com.tripify.hotels.service.repository.contract;

import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.model.HotelPhoto;

public interface HotelPhotoRepository {

    List<HotelPhoto> findByHotelId(UUID hotelId);

    void replaceAll(UUID hotelId, List<HotelPhoto> photos);
}
