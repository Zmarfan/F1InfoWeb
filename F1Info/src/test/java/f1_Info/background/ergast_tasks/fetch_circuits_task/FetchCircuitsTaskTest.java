package f1_Info.background.ergast_tasks.fetch_circuits_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.ergast_tasks.fetch_circuits_task.Database;
import f1_Info.background.ergast_tasks.fetch_circuits_task.FetchCircuitsTask;
import f1_Info.constants.Country;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.circuit.CircuitData;
import f1_Info.background.ergast_tasks.ergast.responses.circuit.LocationData;
import f1_Info.logger.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FetchCircuitsTaskTest {
    private static final String WIKIPEDIA_URL = "https://f1.com/gaming/visst";

    @Mock
    ErgastProxy mErgastProxy;

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    FetchCircuitsTask mFetchCircuitsTask;

    @Test
    void should_not_attempt_to_merge_in_circuits_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mErgastProxy.fetchAllCircuits()).thenReturn(emptyList());

        mFetchCircuitsTask.run();

        verify(mDatabase, never()).mergeIntoCircuitsData(anyList());
    }

    @Test
    void should_merge_in_circuits_data_sent_from_ergast_to_database() throws SQLException, MalformedURLException {
        final List<CircuitData> data = List.of(
            new CircuitData("", WIKIPEDIA_URL, "", new LocationData(BigDecimal.ZERO, BigDecimal.ZERO, "", Country.GERMANY.getNames().get(0)))
        );
        when(mErgastProxy.fetchAllCircuits()).thenReturn(data);

        mFetchCircuitsTask.run();

        verify(mDatabase).mergeIntoCircuitsData(data);
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException, MalformedURLException {
        final List<CircuitData> data = List.of(
            new CircuitData("", WIKIPEDIA_URL, "", new LocationData(BigDecimal.ZERO, BigDecimal.ZERO, "", Country.GERMANY.getNames().get(0)))
        );
        when(mErgastProxy.fetchAllCircuits()).thenReturn(data);
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoCircuitsData(anyList());

        mFetchCircuitsTask.run();

        verify(mLogger).severe(anyString(), eq(TaskWrapper.class), anyString(), any(SQLException.class));
    }
}
