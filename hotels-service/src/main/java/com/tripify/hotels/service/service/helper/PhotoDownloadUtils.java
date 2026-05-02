package com.tripify.hotels.service.service.helper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

public final class PhotoDownloadUtils {

    private PhotoDownloadUtils() {
    }

    public static byte[] downloadBytes(String sourceUrl) {
        try (InputStream inputStream = URI.create(sourceUrl).toURL().openStream()) {
            return inputStream.readAllBytes();
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to download photo. url=" + sourceUrl, exception);
        }
    }

    public static String resolveFileName(String sourceUrl) {
        String path = URI.create(sourceUrl).getPath();
        if (path == null || path.isBlank() || "/".equals(path)) {
            return UUID.randomUUID() + ".jpg";
        }

        String fileName = path.substring(path.lastIndexOf('/') + 1);
        if (fileName.isBlank()) {
            return UUID.randomUUID() + ".jpg";
        }

        return fileName;
    }
}
