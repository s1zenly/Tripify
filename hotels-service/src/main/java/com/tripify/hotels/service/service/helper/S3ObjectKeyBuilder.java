package com.tripify.hotels.service.service.helper;

import java.util.Locale;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class S3ObjectKeyBuilder {

    public String hotelPhotoKey(UUID hotelId, String fileName) {
        return "hotels/%s/photos/%s".formatted(hotelId, sanitizeFileName(fileName));
    }

    public String reviewPhotoKey(UUID hotelId, int commentIndex, String fileName) {
        return "hotels/%s/reviews/%s/%s".formatted(
                hotelId,
                commentIndex,
                sanitizeFileName(fileName)
        );
    }

    public String publicUrl(String endpoint, String bucket, String s3Key) {
        String normalizedEndpoint = endpoint.replaceAll("/$", "");
        return normalizedEndpoint + "/" + bucket + "/" + s3Key;
    }

    private static String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    public static String resolveContentType(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0) {
            return "application/octet-stream";
        }

        String extension = fileName.substring(dotIndex + 1).toLowerCase(Locale.ROOT);
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "webp" -> "image/webp";
            case "gif" -> "image/gif";
            default -> "application/octet-stream";
        };
    }
}
