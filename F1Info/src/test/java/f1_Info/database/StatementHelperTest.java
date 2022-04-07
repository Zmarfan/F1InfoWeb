package f1_Info.database;

import f1_Info.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.text.ParseException;
import java.util.Date;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StatementHelperTest {
    private static final int PARAMETER_INDEX = 1;

    @Mock
    PreparedStatement mPreparedStatement;

    @Test
    void should_set_date_if_defined() throws SQLException {
        final Date date = new Date();
        StatementHelper.setDate(mPreparedStatement, PARAMETER_INDEX, date);
        verify(mPreparedStatement).setDate(PARAMETER_INDEX, new java.sql.Date(date.getTime()));
    }

    @Test
    void should_set_null_date_if_not_defined() throws SQLException {
        StatementHelper.setDate(mPreparedStatement, PARAMETER_INDEX, null);
        verify(mPreparedStatement).setNull(PARAMETER_INDEX, Types.DATE);
    }

    @Test
    void should_set_time_if_defined() throws SQLException, ParseException {
        final Time time = DateUtils.parseTime("10:25:15Z");
        StatementHelper.setTime(mPreparedStatement, PARAMETER_INDEX, time);
        verify(mPreparedStatement).setTime(PARAMETER_INDEX, time);
    }

    @Test
    void should_set_null_time_if_not_defined() throws SQLException {
        StatementHelper.setTime(mPreparedStatement, PARAMETER_INDEX, null);
        verify(mPreparedStatement).setNull(PARAMETER_INDEX, Types.TIME);
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
    void should_set_boolean_if_defined() throws SQLException {
        final boolean value = true;
        StatementHelper.setBoolean(mPreparedStatement, PARAMETER_INDEX, value);
        verify(mPreparedStatement).setBoolean(PARAMETER_INDEX, value);
    }

    @Test
    void should_set_null_boolean_if_not_defined() throws SQLException {
        StatementHelper.setBoolean(mPreparedStatement, PARAMETER_INDEX, null);
        verify(mPreparedStatement).setNull(PARAMETER_INDEX, Types.BOOLEAN);
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