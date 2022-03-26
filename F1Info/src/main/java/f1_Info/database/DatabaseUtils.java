package f1_Info.database;

import f1_Info.constants.Country;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class DatabaseUtils {
    public static void setDate(final PreparedStatement preparedStatement, final int parameterIndex, final Date value) throws SQLException {
        preparedStatement.setDate(parameterIndex, new java.sql.Date(value.getTime()));
    }

    public static void setCountry(final PreparedStatement preparedStatement, final int parameterIndex, final Country value) throws SQLException {
        preparedStatement.setString(parameterIndex, value.getCode());
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
}
