package database;

import lombok.experimental.UtilityClass;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.util.Date;

@UtilityClass
public class StatementHelper {
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
