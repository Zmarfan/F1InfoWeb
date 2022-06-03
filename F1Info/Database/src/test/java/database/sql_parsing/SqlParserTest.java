package database.sql_parsing;

import common.constants.Country;
import common.constants.Url;
import common.logger.Logger;
import common.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SqlParserTest {
    @Mock
    ResultSet mResultSet;

    @Mock
    Logger mLogger;

    @Test
    void should_return_empty_list_when_parsing_basics_if_result_contain_no_data() throws SQLException {
        when(mResultSet.next()).thenReturn(false);

        final List<Integer> list = new SqlParser<>(Integer.class, mResultSet, mLogger).parseBasicList();
        assertEquals(emptyList(), list);
    }

    @Test
    void should_append_basic_result_row_data_to_return_list_until_result_set_is_empty() throws SQLException {
        when(mResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mResultSet.getInt(1)).thenReturn(1).thenReturn(2).thenReturn(3).thenReturn(4);

        final List<Integer> list = new SqlParser<>(Integer.class, mResultSet, mLogger).parseBasicList();

        assertEquals(List.of(1, 2, 3, 4), list);
    }

    @Test
    void should_return_empty_list_when_parsing_records_if_result_contain_no_data() throws SQLException {
        when(mResultSet.next()).thenReturn(false);

        final List<TestRecord> list = new SqlParser<>(TestRecord.class, mResultSet, mLogger).parseRecordsList();
        assertEquals(emptyList(), list);
    }

    @Test
    void should_append_record_result_row_data_to_return_list_until_result_set_is_empty() throws SQLException {
        final List<TestRecord> expectedList = List.of(
            new TestRecord(1, "value1", 1D, true),
            new TestRecord(2, "value2", 2D, false),
            new TestRecord(3, "value3", 3D, true)
        );

        when(mResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mResultSet.getInt(1)).thenReturn(1).thenReturn(2).thenReturn(3);
        when(mResultSet.getString(2)).thenReturn("value1").thenReturn("value2").thenReturn("value3");
        when(mResultSet.getDouble(3)).thenReturn(1D).thenReturn(2D).thenReturn(3D);
        when(mResultSet.getBoolean(4)).thenReturn(true).thenReturn(false).thenReturn(true);

        final List<TestRecord> list = new SqlParser<>(TestRecord.class, mResultSet, mLogger).parseRecordsList();

        assertEquals(expectedList, list);
    }

    @Test
    void should_parse_int() throws SQLException {
        when(mResultSet.next()).thenReturn(true).thenReturn(false);
        when(mResultSet.getInt(1)).thenReturn(10);

        assertEquals(10, new SqlParser<>(Integer.class, mResultSet, mLogger).parseBasicList().get(0));
    }

    @Test
    void should_parse_long() throws SQLException {
        when(mResultSet.next()).thenReturn(true).thenReturn(false);
        when(mResultSet.getLong(1)).thenReturn(10L);

        assertEquals(10L, new SqlParser<>(Long.class, mResultSet, mLogger).parseBasicList().get(0));
    }

    @Test
    void should_parse_string() throws SQLException {
        when(mResultSet.next()).thenReturn(true).thenReturn(false);
        when(mResultSet.getString(1)).thenReturn("value");

        assertEquals("value", new SqlParser<>(String.class, mResultSet, mLogger).parseBasicList().get(0));
    }

    @Test
    void should_parse_date() throws SQLException {
        final LocalDate date = LocalDate.now();

        when(mResultSet.next()).thenReturn(true).thenReturn(false);
        when(mResultSet.getDate(1)).thenReturn(java.sql.Date.valueOf(date));

        assertEquals(date, new SqlParser<>(LocalDate.class, mResultSet, mLogger).parseBasicList().get(0));
    }

    @Test
    void should_parse_time() throws SQLException {
        final LocalTime time = DateUtils.parseTime("10:25:15Z");

        when(mResultSet.next()).thenReturn(true).thenReturn(false);
        when(mResultSet.getTime(1)).thenReturn(Time.valueOf(time));

        assertEquals(time, new SqlParser<>(LocalTime.class, mResultSet, mLogger).parseBasicList().get(0));
    }

    @Test
    void should_parse_boolean() throws SQLException {
        when(mResultSet.next()).thenReturn(true).thenReturn(false);
        when(mResultSet.getBoolean(1)).thenReturn(true);

        assertEquals(true, new SqlParser<>(Boolean.class, mResultSet, mLogger).parseBasicList().get(0));
    }

    @Test
    void should_parse_big_decimal() throws SQLException {
        when(mResultSet.next()).thenReturn(true).thenReturn(false);
        when(mResultSet.getBigDecimal(1)).thenReturn(BigDecimal.TEN);

        assertEquals(BigDecimal.TEN, new SqlParser<>(BigDecimal.class, mResultSet, mLogger).parseBasicList().get(0));
    }

    @Test
    void should_parse_double() throws SQLException {
        when(mResultSet.next()).thenReturn(true).thenReturn(false);
        when(mResultSet.getDouble(1)).thenReturn(123D);

        assertEquals(123D, new SqlParser<>(Double.class, mResultSet, mLogger).parseBasicList().get(0));
    }

    @Test
    void should_parse_country() throws SQLException {
        when(mResultSet.next()).thenReturn(true).thenReturn(false);
        when(mResultSet.getString(1)).thenReturn(Country.SWEDEN.getCode());

        assertEquals(Country.SWEDEN, new SqlParser<>(Country.class, mResultSet, mLogger).parseBasicList().get(0));
    }

    @Test
    void should_parse_url() throws SQLException, MalformedURLException {
        final String link = "http://en.wikipedia.org/wiki/Valtteri_Bottas";

        when(mResultSet.next()).thenReturn(true).thenReturn(false);
        when(mResultSet.getString(1)).thenReturn(link);

        assertEquals(new Url(link), new SqlParser<>(Url.class, mResultSet, mLogger).parseBasicList().get(0));
    }
}
