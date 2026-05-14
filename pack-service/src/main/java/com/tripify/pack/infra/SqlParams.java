package com.tripify.pack.infra;

import java.sql.Types;
import java.time.Instant;
import java.time.ZoneOffset;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class SqlParams extends MapSqlParameterSource {

    @Override
    public SqlParams addValue(String paramName, Object value) {
        super.addValue(paramName, value);
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
