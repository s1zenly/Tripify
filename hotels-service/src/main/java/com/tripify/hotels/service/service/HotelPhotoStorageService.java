package com.tripify.hotels.service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tripify.hotels.service.config.properties.S3Properties;
import com.tripify.hotels.service.kafka.model.PhotoDto;
import com.tripify.hotels.service.kafka.model.RoomPhotoDto;
import com.tripify.hotels.service.model.documents.RoomPhotoDocument;
import com.tripify.hotels.service.service.helper.PhotoDownloadUtils;
import com.tripify.hotels.service.service.helper.S3ObjectKeyBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelPhotoStorageService {

    private final S3Client s3Client;
    private final S3Properties properties;
    private final S3ObjectKeyBuilder keyBuilder;

    @PostConstruct
    void logMode() {
        log.info("S3 photo upload enabled: {}", properties.uploadEnabled());
    }

    public String uploadHotelPhoto(
            UUID hotelId,
            String fileName,
            byte[] content,
            String contentType
    ) {
        String key = keyBuilder.hotelPhotoKey(hotelId, fileName);
        putObject(properties.bucket(), key, content, contentType);
        return key;
    }

    public Optional<String> uploadHotelPhotoFromUrl(UUID hotelId, String sourceUrl) {
        if (!properties.uploadEnabled()) {
            return Optional.of(sourceUrl);
        }

        String fileName = PhotoDownloadUtils.resolveFileName(sourceUrl);
        return PhotoDownloadUtils.downloadBytes(sourceUrl)
                .map(bytes -> {
                    long start = System.currentTimeMillis();
                    String key = keyBuilder.hotelPhotoKey(hotelId, fileName);
                    putObject(properties.bucket(), key, bytes, S3ObjectKeyBuilder.resolveContentType(fileName));
                    log.info("Hotel photo uploaded to S3. hotelId={}, key={}, size={}KB, time={}ms",
                            hotelId, key, bytes.length / 1024, System.currentTimeMillis() - start);
                    return key;
                });
    }

    public Optional<String> uploadRoomPhotoFromUrl(UUID hotelId, String roomId, String sourceUrl) {
        if (!properties.uploadEnabled()) {
            return Optional.of(sourceUrl);
        }

        String fileName = PhotoDownloadUtils.resolveFileName(sourceUrl);
        return PhotoDownloadUtils.downloadBytes(sourceUrl)
                .map(bytes -> {
                    long start = System.currentTimeMillis();
                    String key = keyBuilder.roomPhotoKey(hotelId, roomId, fileName);
                    putObject(properties.roomsBucket(), key, bytes, S3ObjectKeyBuilder.resolveContentType(fileName));
                    log.info("Room photo uploaded to S3. hotelId={}, roomId={}, key={}, size={}KB, time={}ms",
                            hotelId, roomId, key, bytes.length / 1024, System.currentTimeMillis() - start);
                    return key;
                });
    }

    public List<RoomPhotoDocument> uploadRoomPhotosFromUrls(
            UUID hotelId,
            String roomId,
            List<RoomPhotoDto> photos
    ) {
        if (photos == null || photos.isEmpty()) {
            return List.of();
        }

        return photos.stream()
                .filter(p -> p.link() != null && !p.link().isBlank())
                .map(p -> uploadRoomPhotoFromUrl(hotelId, roomId, p.link())
                        .map(s3Key -> new RoomPhotoDocument(s3Key, p.order())))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public Optional<String> uploadReviewPhotoFromUrl(UUID hotelId, int commentIndex, String sourceUrl) {
        if (!properties.uploadEnabled()) {
            return Optional.of(sourceUrl);
        }

        String fileName = PhotoDownloadUtils.resolveFileName(sourceUrl);
        return PhotoDownloadUtils.downloadBytes(sourceUrl)
                .map(bytes -> {
                    long start = System.currentTimeMillis();
                    String key = keyBuilder.reviewPhotoKey(hotelId, commentIndex, fileName);
                    putObject(properties.reviewsBucket(), key, bytes, S3ObjectKeyBuilder.resolveContentType(fileName));
                    log.info("Review photo uploaded to S3. hotelId={}, commentIndex={}, key={}, size={}KB, time={}ms",
                            hotelId, commentIndex, key, bytes.length / 1024, System.currentTimeMillis() - start);
                    return key;
                });
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
                .map(PhotoDto::link)
                .filter(url -> url != null && !url.isBlank())
                .map(url -> uploadReviewPhotoFromUrl(hotelId, commentIndex, url))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public String buildHotelPhotoUrl(String s3KeyOrUrl) {
        if (isDirectUrl(s3KeyOrUrl)) {
            return s3KeyOrUrl;
        }
        return keyBuilder.publicUrl(properties.endpoint(), properties.bucket(), s3KeyOrUrl);
    }

    public String buildRoomPhotoUrl(String s3KeyOrUrl) {
        if (isDirectUrl(s3KeyOrUrl)) {
            return s3KeyOrUrl;
        }
        return keyBuilder.publicUrl(properties.endpoint(), properties.roomsBucket(), s3KeyOrUrl);
    }

    public String buildReviewPhotoUrl(String s3KeyOrUrl) {
        if (isDirectUrl(s3KeyOrUrl)) {
            return s3KeyOrUrl;
        }
        return keyBuilder.publicUrl(properties.endpoint(), properties.reviewsBucket(), s3KeyOrUrl);
    }

    private static boolean isDirectUrl(String value) {
        return value != null && (value.startsWith("http://") || value.startsWith("https://"));
    }

    private void putObject(String bucket, String key, byte[] content, String contentType) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(content));
    }
}
