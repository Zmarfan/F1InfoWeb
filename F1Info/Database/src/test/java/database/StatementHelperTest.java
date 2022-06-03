package database;

import common.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StatementHelperTest {
    private static final int PARAMETER_INDEX = 1;

    @Mock
    PreparedStatement mPreparedStatement;

    @Test
    void should_set_date_if_defined() throws SQLException {
        final LocalDate date = LocalDate.now();
        StatementHelper.setDate(mPreparedStatement, PARAMETER_INDEX, date);
        verify(mPreparedStatement).setDate(PARAMETER_INDEX, java.sql.Date.valueOf(date));
    }

    @Test
    void should_set_null_date_if_not_defined() throws SQLException {
        StatementHelper.setDate(mPreparedStatement, PARAMETER_INDEX, null);
        verify(mPreparedStatement).setNull(PARAMETER_INDEX, Types.DATE);
    }

    @Test
    void should_set_time_if_defined() throws SQLException {
        final LocalTime time = DateUtils.parseTime("10:25:15Z");
        StatementHelper.setTime(mPreparedStatement, PARAMETER_INDEX, time);
        verify(mPreparedStatement).setTime(PARAMETER_INDEX, Time.valueOf(time));
    }

    @Test
    void should_set_null_time_if_not_defined() throws SQLException {
        StatementHelper.setTime(mPreparedStatement, PARAMETER_INDEX, null);
        verify(mPreparedStatement).setNull(PARAMETER_INDEX, Types.TIME);
    }

    @Test
    void should_set_date_time_if_defined() throws SQLException {
        final LocalDateTime time = LocalDateTime.now();
        StatementHelper.setDateTime(mPreparedStatement, PARAMETER_INDEX, time);
        verify(mPreparedStatement).setObject(PARAMETER_INDEX, time);
    }

    @Test
    void should_set_null_date_time_if_not_defined() throws SQLException {
        StatementHelper.setDateTime(mPreparedStatement, PARAMETER_INDEX, null);
        verify(mPreparedStatement).setNull(PARAMETER_INDEX, Types.TIMESTAMP);
    }

    @Test
    void should_set_int_if_defined() throws SQLException {
        final int value = 5;
        StatementHelper.setInt(mPreparedStatement, PARAMETER_INDEX, value);
        verify(mPreparedStatement).setInt(PARAMETER_INDEX, value);
    }

    @Test
    void should_set_null_int_if_not_defined() throws SQLException {
        StatementHelper.setInt(mPreparedStatement, PARAMETER_INDEX, null);
        verify(mPreparedStatement).setNull(PARAMETER_INDEX, Types.INTEGER);
    }

    @Test
    void should_set_true_boolean_to_Y() throws SQLException {
        final boolean value = true;
        StatementHelper.setBoolean(mPreparedStatement, PARAMETER_INDEX, value);
        verify(mPreparedStatement).setString(PARAMETER_INDEX, "Y");
    }

    @Test
    void should_set_false_boolean_to_N() throws SQLException {
        final boolean value = false;
        StatementHelper.setBoolean(mPreparedStatement, PARAMETER_INDEX, value);
        verify(mPreparedStatement).setString(PARAMETER_INDEX, "N");
    }

    @Test
    void should_set_null_boolean_if_not_defined() throws SQLException {
        StatementHelper.setBoolean(mPreparedStatement, PARAMETER_INDEX, null);
        verify(mPreparedStatement).setNull(PARAMETER_INDEX, Types.CHAR);
    }

    @Test
    void should_set_long_if_defined() throws SQLException {
        final long value = 5L;
        StatementHelper.setLong(mPreparedStatement, PARAMETER_INDEX, value);
        verify(mPreparedStatement).setLong(PARAMETER_INDEX, value);
    }

    @Test
    void should_set_null_long_if_not_defined() throws SQLException {
        StatementHelper.setLong(mPreparedStatement, PARAMETER_INDEX, null);
        verify(mPreparedStatement).setNull(PARAMETER_INDEX, Types.BIGINT);
    }

    @Test
    void should_set_double_if_defined() throws SQLException {
        final double value = 5;
        StatementHelper.setDouble(mPreparedStatement, PARAMETER_INDEX, value);
        verify(mPreparedStatement).setDouble(PARAMETER_INDEX, value);
    }

    @Test
    void should_set_null_double_if_not_defined() throws SQLException {
        StatementHelper.setDouble(mPreparedStatement, PARAMETER_INDEX, null);
        verify(mPreparedStatement).setNull(PARAMETER_INDEX, Types.DOUBLE);
    }

    @Test
    void should_set_string_if_defined() throws SQLException {
        final String value = "hej";
        StatementHelper.setString(mPreparedStatement, PARAMETER_INDEX, value);
        verify(mPreparedStatement).setString(PARAMETER_INDEX, value);
    }

    @Test
    void should_set_null_string_if_not_defined() throws SQLException {
        StatementHelper.setString(mPreparedStatement, PARAMETER_INDEX, null);
        verify(mPreparedStatement).setNull(PARAMETER_INDEX, Types.VARCHAR);
    }
}
