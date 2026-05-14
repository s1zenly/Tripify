package com.tripify.pack.infra;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public final class ResultSetUtils {

    private ResultSetUtils() {
    }

    public static Instant getInstant(String columnName, ResultSet resultSet) throws SQLException {
        var timestamp = resultSet.getTimestamp(columnName);
        return timestamp == null ? null : timestamp.toInstant();
    }
}
