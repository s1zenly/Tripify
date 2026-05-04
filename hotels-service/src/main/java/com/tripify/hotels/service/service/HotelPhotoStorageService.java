package com.tripify.hotels.service.service;

import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.config.properties.S3Properties;
import com.tripify.hotels.service.kafka.model.PhotoDto;
import com.tripify.hotels.service.service.helper.PhotoDownloadUtils;
import com.tripify.hotels.service.service.helper.S3ObjectKeyBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class HotelPhotoStorageService {

    private final S3Client s3Client;
    private final S3Properties properties;
    private final S3ObjectKeyBuilder keyBuilder;

    public String uploadHotelPhoto(
            UUID hotelId,
            String fileName,
            byte[] content,
            String contentType
    ) {
        String key = keyBuilder.hotelPhotoKey(hotelId, fileName);
        putObject(key, content, contentType);
        return key;
    }

    public String uploadHotelPhotoFromUrl(UUID hotelId, String sourceUrl) {
        String fileName = PhotoDownloadUtils.resolveFileName(sourceUrl);
        String key = keyBuilder.hotelPhotoKey(hotelId, fileName);
        putObject(key, PhotoDownloadUtils.downloadBytes(sourceUrl), S3ObjectKeyBuilder.resolveContentType(fileName));
        return key;
    }

    public String uploadReviewPhotoFromUrl(UUID hotelId, int commentIndex, String sourceUrl) {
        String fileName = PhotoDownloadUtils.resolveFileName(sourceUrl);
        String key = keyBuilder.reviewPhotoKey(hotelId, commentIndex, fileName);
        putObject(key, PhotoDownloadUtils.downloadBytes(sourceUrl), S3ObjectKeyBuilder.resolveContentType(fileName));
        return key;
    }

    public List<String> uploadReviewPhotosFromUrls(
            UUID hotelId,
            int commentIndex,
            List<PhotoDto> photos
    ) {
        if (photos == null || photos.isEmpty()) {
            return List.of();
        }

        return photos.stream()
                .map(PhotoDto::url)
                .filter(url -> url != null && !url.isBlank())
                .map(url -> uploadReviewPhotoFromUrl(hotelId, commentIndex, url))
                .toList();
    }

    public String buildPublicUrl(String s3Key) {
        return keyBuilder.publicUrl(properties.endpoint(), properties.bucket(), s3Key);
    }

    private void putObject(String key, byte[] content, String contentType) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(properties.bucket())
                .key(key)
                .contentType(contentType)
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(content));
    }
}
