package com.tripify.hotels.service.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public final class ResultSetUtils {

    private ResultSetUtils() {
    }

    public static Instant getTimestamp(String columnName, ResultSet rs) throws SQLException {
        return rs.getTimestamp(columnName) == null
                ? null
                : rs.getTimestamp(columnName).toInstant();
    }
}
