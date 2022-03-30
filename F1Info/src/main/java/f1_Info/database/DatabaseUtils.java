package f1_Info.database;

import f1_Info.constants.Country;
import f1_Info.constants.Url;

import java.sql.*;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

public class DatabaseUtils {
    public static void setDate(final PreparedStatement preparedStatement, final int parameterIndex, final Date date) throws SQLException {
        if (date != null) {
            preparedStatement.setDate(parameterIndex, new java.sql.Date(date.getTime()));
        } else {
            preparedStatement.setNull(parameterIndex, Types.DATE);
        }
    }

    public static void setTime(final PreparedStatement preparedStatement, final int parameterIndex, final Time time) throws SQLException {
        if (time != null) {
            preparedStatement.setTime(parameterIndex, time);
        } else {
            preparedStatement.setNull(parameterIndex, Types.TIME);
        }
    }

    public static void setCountry(final PreparedStatement preparedStatement, final int parameterIndex, final Country country) throws SQLException {
        if (country != null) {
            preparedStatement.setString(parameterIndex, country.getCode());
        } else {
            preparedStatement.setNull(parameterIndex, Types.VARCHAR);
        }
    }

    public static void setUrl(final PreparedStatement preparedStatement, final int parameterIndex, final Url url) throws SQLException {
        if (url != null) {
            preparedStatement.setString(parameterIndex, url.getUrl());
        } else {
            preparedStatement.setNull(parameterIndex, Types.VARCHAR);
        }
    }

    public static void setInstant(final PreparedStatement preparedStatement, final int parameterIndex, final Instant instant) throws SQLException {
        if (instant != null) {
            preparedStatement.setString(parameterIndex, instant.toString());
        } else {
            preparedStatement.setNull(parameterIndex, Types.VARCHAR);
        }
    }

    public static void setNullableInt(final PreparedStatement preparedStatement, final int parameterIndex, final Integer value) throws SQLException {
        if (value != null) {
            preparedStatement.setInt(parameterIndex, value);
        } else {
            preparedStatement.setNull(parameterIndex, Types.INTEGER);
        }
    }

    public static void setNullableString(final PreparedStatement preparedStatement, final int parameterIndex, final String value) throws SQLException {
        if (value != null) {
            preparedStatement.setString(parameterIndex, value);
        } else {
            preparedStatement.setNull(parameterIndex, Types.VARCHAR);
        }
    }

    public static void setNullableException(final PreparedStatement preparedStatement, final int parameterIndex, final Exception value) throws SQLException {
        if (value != null) {
            preparedStatement.setString(parameterIndex, value.toString());
        } else {
            preparedStatement.setNull(parameterIndex, Types.VARCHAR);
        }
    }

    public static Optional<Integer> readNullableInt(final ResultSet resultSet, final int columnIndex) throws SQLException {
        final int readInt = resultSet.getInt(columnIndex);
        return resultSet.wasNull() ? Optional.empty() : Optional.of(readInt);
    }
}
