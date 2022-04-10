package f1_Info.background.fetch_drivers_task;

import f1_Info.background.TaskWrapper;
import f1_Info.constants.Country;
import f1_Info.background.ergast.ErgastProxy;
import f1_Info.background.ergast.responses.DriverData;
import f1_Info.logger.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FetchDriversTaskTest {
    private static final String WIKIPEDIA_URL = "https://f1.com/gaming/visst";

    @Mock
    ErgastProxy mErgastProxy;

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    FetchDriversTask mFetchDriversTask;

    @Test
    void should_not_attempt_to_merge_in_drivers_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mErgastProxy.fetchAllDrivers()).thenReturn(emptyList());

        mFetchDriversTask.run();

        verify(mDatabase, never()).mergeIntoDriversData(anyList());
    }

    @Test
    void should_merge_in_drivers_data_sent_from_ergast_to_database() throws SQLException, ParseException, MalformedURLException {
        final List<DriverData> data = List.of(new DriverData(
            "",
            WIKIPEDIA_URL,
            "",
            "",
            "1999-01-01",
            Country.GERMANY.getNationalityKeywords().get(0),
            0,
            ""
        ));
        when(mErgastProxy.fetchAllDrivers()).thenReturn(data);

        mFetchDriversTask.run();

        verify(mDatabase).mergeIntoDriversData(data);
    }


    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException, MalformedURLException, ParseException {
        final List<DriverData> data = List.of(new DriverData(
            "",
            WIKIPEDIA_URL,
            "",
            "",
            "1999-01-01",
            Country.GERMANY.getNationalityKeywords().get(0),
            0,
            ""
        ));
        when(mErgastProxy.fetchAllDrivers()).thenReturn(data);
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoDriversData(anyList());

        mFetchDriversTask.run();

        verify(mLogger).severe(anyString(), eq(TaskWrapper.class), anyString(), any(SQLException.class));
    }
}
