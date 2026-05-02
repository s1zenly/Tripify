package com.tripify.hotels.service.infra;

import java.sql.Types;
import java.time.Instant;
import java.time.ZoneOffset;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class SqlParams extends MapSqlParameterSource {

    @Override
    public SqlParams addValue(@NonNull String paramName, @Nullable Object value) {
        super.addValue(paramName, value);
        return this;
    }

    @Override
    public SqlParams addValue(@NonNull String paramName, @Nullable Object value, int sqlType) {
        super.addValue(paramName, value, sqlType);
        return this;
    }

    public SqlParams addTimestamp(String paramName, Instant value) {
        addValue(
                paramName,
                value == null ? null : value.atOffset(ZoneOffset.UTC),
                Types.TIMESTAMP_WITH_TIMEZONE
        );

        return this;
    }
}
