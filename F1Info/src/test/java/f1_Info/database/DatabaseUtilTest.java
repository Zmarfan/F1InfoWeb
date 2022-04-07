package f1_Info.database;

import f1_Info.database.sql_parsing.SqlParser;
import f1_Info.logger.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.List;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DatabaseUtilTest {
    public static final String PROCEDURE_NAME = "test_procedure_name";

    @Mock
    Connection mConnection;

    @Mock
    CallableStatement mCallableStatement;

    @Mock
    ResultSet mResultSet;

    @Mock
    Function<SqlParser<Integer>, List<Integer>> mSqlParser;

    @Mock
    Logger mLogger;

    @BeforeEach
    void init() throws SQLException {
        when(mConnection.prepareCall(anyString())).thenReturn(mCallableStatement);
    }

    @Test
    void should_execute_statement() throws SQLException {
        DatabaseUtil.executeQuery(mConnection, new NoParametersVoidQueryData(), null, mLogger);
        verify(mCallableStatement).execute();
    }

    @Test
    void should_prepare_call_with_correct_format_when_no_parameters_exist_in_query_data() throws SQLException {
        DatabaseUtil.executeQuery(mConnection, new NoParametersVoidQueryData(), null, mLogger);
        verify(mConnection).prepareCall("{ call test_procedure_name() }");
    }

    @Test
    void should_prepare_call_with_correct_format_when_parameters_exist_in_query_data() throws SQLException {
        DatabaseUtil.executeQuery(mConnection, new ParametersVoidQueryData(1, "hej", null), null, mLogger);
        verify(mConnection).prepareCall("{ call test_procedure_name(?, ?, ?) }");
    }

    @Test
    void should_never_set_query_data_parameters_when_parameters_does_not_exist_in_query_data() throws SQLException {
        DatabaseUtil.executeQuery(mConnection, new NoParametersVoidQueryData(), null, mLogger);

        verify(mCallableStatement, never()).setInt(anyInt(), anyInt());
        verify(mCallableStatement, never()).setString(anyInt(), anyString());
        verify(mCallableStatement, never()).setDouble(anyInt(), anyDouble());
        verify(mCallableStatement, never()).setBoolean(anyInt(), anyBoolean());
        verify(mCallableStatement, never()).setNull(anyInt(), anyInt());
    }

    @Test
    void should_set_query_data_parameters_correctly_and_in_order_when_parameters_exist_in_query_data() throws SQLException {
        DatabaseUtil.executeQuery(mConnection, new ParametersVoidQueryData(123, "hej", 321L), null, mLogger);

        verify(mCallableStatement).setInt(1, 123);
        verify(mCallableStatement).setString(2, "hej");
        verify(mCallableStatement).setLong(3, 321L);
    }

    @Test
    void should_set_query_data_parameters_as_null_for_correct_types_when_null_parameters_exist_in_query_data() throws SQLException {
        DatabaseUtil.executeQuery(mConnection, new ParametersVoidQueryData(123, null, null), null, mLogger);

        verify(mCallableStatement).setNull(2, Types.VARCHAR);
        verify(mCallableStatement).setNull(3, Types.BIGINT);
    }

    @Test
    void should_throw_sql_exception_if_query_data_contains_parameters_it_can_not_set() {
        assertThrows(
            SQLException.class,
            () -> DatabaseUtil.executeQuery(mConnection, new BadParametersQueryData(new NoParametersVoidQueryData()), null, mLogger)
        );
    }

    @Test
    void should_log_severe_if_exception_is_thrown_if_query_data_contains_parameters_it_can_not_set() {
        assertThrows(
            SQLException.class,
            () -> DatabaseUtil.executeQuery(mConnection, new BadParametersQueryData(new NoParametersVoidQueryData()), null, mLogger)
        );

        verify(mLogger).severe(anyString(), eq(DatabaseUtil.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_return_empty_list_if_query_has_no_result() throws SQLException {
        when(mCallableStatement.execute()).thenReturn(false);

        final List<Void> list = DatabaseUtil.executeQuery(mConnection, new ParametersVoidQueryData(123, "hej", 321L), null, mLogger);

        assertEquals(emptyList(), list);
    }

    @Test
    void should_return_empty_list_if_no_parser_was_provided() throws SQLException {
        final List<Void> list = DatabaseUtil.executeQuery(mConnection, new ParametersVoidQueryData(123, "hej", 321L), null, mLogger);

        assertEquals(emptyList(), list);
    }

    @Test
    void should_return_empty_list_if_query_has_no_result_set() throws SQLException {
        when(mCallableStatement.execute()).thenReturn(true);

        final List<Integer> list = DatabaseUtil.executeQuery(mConnection, new ReturnQueryData(), mSqlParser, mLogger);

        assertEquals(emptyList(), list);
    }

    @Test
    void should_run_sql_parsed_list_if_query_has_result_set() throws SQLException {
        when(mCallableStatement.execute()).thenReturn(true);
        when(mCallableStatement.getResultSet()).thenReturn(mResultSet);

        DatabaseUtil.executeQuery(mConnection, new ReturnQueryData(), mSqlParser, mLogger);

        verify(mSqlParser).apply(any(SqlParser.class));
    }

    @Test
    void should_create_sql_parser_from_generated_result_set_query_data_record_class_and_logger_if_query_has_result_set() throws SQLException {
        when(mCallableStatement.execute()).thenReturn(true);
        when(mCallableStatement.getResultSet()).thenReturn(mResultSet);

        final SqlParser<Integer> expectedParser = new SqlParser<>(new ReturnQueryData().getRecordClass(), mResultSet, mLogger);
        DatabaseUtil.executeQuery(mConnection, new ReturnQueryData(), mSqlParser, mLogger);

        ArgumentCaptor<SqlParser<Integer>> captor = ArgumentCaptor.forClass(SqlParser.class);

        verify(mSqlParser).apply(captor.capture());

        assertEquals(expectedParser, captor.getValue());
    }

    @Test
    void should_return_sql_parsed_list_if_query_has_result_set() throws SQLException {
        final List<Integer> expectedList = List.of(1, 2, 3, 4, 5, 6, 7, 8);

        when(mCallableStatement.execute()).thenReturn(true);
        when(mCallableStatement.getResultSet()).thenReturn(mResultSet);
        when(mSqlParser.apply(any(SqlParser.class))).thenReturn(expectedList);

        final List<Integer> list = DatabaseUtil.executeQuery(mConnection, new ReturnQueryData(), mSqlParser, mLogger);

        assertEquals(expectedList, list);
    }
}
