package database;

import lombok.experimental.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@UtilityClass
public class StatementHelper {
    public static void setDate(final PreparedStatement preparedStatement, final int parameterIndex, final LocalDate date) throws SQLException {
        if (date != null) {
            preparedStatement.setDate(parameterIndex, java.sql.Date.valueOf(date));
        } else {
            preparedStatement.setNull(parameterIndex, Types.DATE);
        }
    }

    public static void setTime(final PreparedStatement preparedStatement, final int parameterIndex, final LocalTime time) throws SQLException {
        if (time != null) {
            preparedStatement.setTime(parameterIndex, Time.valueOf(time));
        } else {
            preparedStatement.setNull(parameterIndex, Types.TIME);
        }
    }

    public static void setDateTime(final PreparedStatement preparedStatement, final int parameterIndex, final LocalDateTime localDateTime) throws SQLException {
        if (localDateTime != null) {
            preparedStatement.setObject(parameterIndex, localDateTime);
        } else {
            preparedStatement.setNull(parameterIndex, Types.TIMESTAMP);
        }
    }

    public static void setInt(final PreparedStatement preparedStatement, final int parameterIndex, final Integer value) throws SQLException {
        if (value != null) {
            preparedStatement.setInt(parameterIndex, value);
        } else {
            preparedStatement.setNull(parameterIndex, Types.INTEGER);
        }
    }

    public static void setBoolean(final PreparedStatement preparedStatement, final int parameterIndex, final Boolean value) throws SQLException {
        if (value != null) {
            preparedStatement.setString(parameterIndex, value ? "Y" : "N");
        } else {
            preparedStatement.setNull(parameterIndex, Types.CHAR);
        }
    }

    public static void setLong(final PreparedStatement preparedStatement, final int parameterIndex, final Long value) throws SQLException {
        if (value != null) {
            preparedStatement.setLong(parameterIndex, value);
        } else {
            preparedStatement.setNull(parameterIndex, Types.BIGINT);
        }
    }

    public static void setDouble(final PreparedStatement preparedStatement, final int parameterIndex, final Double value) throws SQLException {
        if (value != null) {
            preparedStatement.setDouble(parameterIndex, value);
        } else {
            preparedStatement.setNull(parameterIndex, Types.DOUBLE);
        }
    }

    public static void setString(final PreparedStatement preparedStatement, final int parameterIndex, final String value) throws SQLException {
        if (value != null) {
            preparedStatement.setString(parameterIndex, value);
        } else {
            preparedStatement.setNull(parameterIndex, Types.VARCHAR);
        }
    }
}
