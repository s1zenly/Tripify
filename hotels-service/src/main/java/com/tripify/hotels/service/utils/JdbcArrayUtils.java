package com.tripify.hotels.service.utils;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public final class JdbcArrayUtils {

    private JdbcArrayUtils() {
    }

    public static List<String> getStringList(String columnName, ResultSet rs) throws SQLException {
        Array array = rs.getArray(columnName);
        if (array == null) {
            return List.of();
        }

        Object raw = array.getArray();
        if (raw instanceof String[] values) {
            return Arrays.asList(values);
        }

        return List.of();
    }
}
