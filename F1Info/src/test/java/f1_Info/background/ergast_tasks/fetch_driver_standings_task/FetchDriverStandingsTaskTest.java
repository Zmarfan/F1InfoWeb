package f1_Info.background.ergast_tasks.fetch_driver_standings_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
import f1_Info.background.ergast_tasks.ergast.responses.DriverData;
import f1_Info.background.ergast_tasks.ergast.responses.standings.DriverStandingsData;
import f1_Info.constants.Country;
import f1_Info.logger.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FetchDriverStandingsTaskTest {
    private static final RaceRecord RACE_RECORD = new RaceRecord(1998, 2, 1);
    private static final String WIKIPEDIA_URL = "https://f1.com/gaming/visst";

    @Mock
    ErgastProxy mErgastProxy;

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    FetchDriverStandingsTask mFetchDriverStandingsTask;

    @Test
    void should_not_call_ergast_for_driver_standings_data_if_there_is_no_next_race_to_fetch_for() throws SQLException {
        when(mDatabase.getNextRaceToFetchDriverStandingsFor()).thenReturn(Optional.empty());

        mFetchDriverStandingsTask.run();

        verify(mErgastProxy, never()).fetchDriverStandingsForRace(any(RaceRecord.class));
    }

    @Test
    void should_not_attempt_to_merge_in_driver_standings_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mDatabase.getNextRaceToFetchDriverStandingsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchDriverStandingsForRace(RACE_RECORD)).thenReturn(emptyList());

        mFetchDriverStandingsTask.run();

        verify(mDatabase, never()).mergeIntoDriverStandingsData(anyList(), any(RaceRecord.class));
    }

    @Test
    void should_merge_in_driver_standings_data_sent_from_ergast_to_database() throws SQLException, ParseException, MalformedURLException {
        when(mDatabase.getNextRaceToFetchDriverStandingsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchDriverStandingsForRace(RACE_RECORD)).thenReturn(getDriverStandingsData());

        mFetchDriverStandingsTask.run();

        verify(mDatabase).mergeIntoDriverStandingsData(getDriverStandingsData(), RACE_RECORD);
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException, ParseException, MalformedURLException {
        when(mDatabase.getNextRaceToFetchDriverStandingsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchDriverStandingsForRace(RACE_RECORD)).thenReturn(getDriverStandingsData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoDriverStandingsData(anyList(), eq(RACE_RECORD));

        mFetchDriverStandingsTask.run();

        verify(mLogger).severe(anyString(), eq(TaskWrapper.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_set_last_fetched_race_after_merging_driver_standings() throws SQLException, ParseException, MalformedURLException {
        when(mDatabase.getNextRaceToFetchDriverStandingsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchDriverStandingsForRace(RACE_RECORD)).thenReturn(getDriverStandingsData());

        mFetchDriverStandingsTask.run();

        verify(mDatabase).mergeIntoDriverStandingsData(anyList(), eq(RACE_RECORD));
        verify(mDatabase).setLastFetchedRaceInHistory(RACE_RECORD);
    }

    @Test
    void should_not_set_last_fetched_race_after_merging_driver_standings_if_it_throws() throws SQLException, ParseException, MalformedURLException {
        when(mDatabase.getNextRaceToFetchDriverStandingsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchDriverStandingsForRace(RACE_RECORD)).thenReturn(getDriverStandingsData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoDriverStandingsData(anyList(), eq(RACE_RECORD));

        mFetchDriverStandingsTask.run();

        verify(mDatabase, never()).setLastFetchedRaceInHistory(any(RaceRecord.class));
    }

    private List<DriverStandingsData> getDriverStandingsData() throws ParseException, MalformedURLException {
        return singletonList(
            new DriverStandingsData(1, "1", BigDecimal.ONE, 2, new DriverData(
                "driverId",
                WIKIPEDIA_URL,
                "firstName",
                "secondName",
                "1998-04-11",
                Country.GERMANY.getNationalityKeywords().get(0),
                11,
                "FES"
            ), singletonList(new ConstructorData("constId", WIKIPEDIA_URL, "vroom", Country.SWEDEN.getNationalityKeywords().get(0))))
        );
    }
}
