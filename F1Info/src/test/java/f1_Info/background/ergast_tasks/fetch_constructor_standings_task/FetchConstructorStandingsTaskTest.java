package f1_Info.background.ergast_tasks.fetch_constructor_standings_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
import f1_Info.background.ergast_tasks.ergast.responses.standings.ConstructorStandingsData;
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
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FetchConstructorStandingsTaskTest {
    private static final RaceRecord RACE_RECORD = new RaceRecord(1998, 2, 1);
    private static final String WIKIPEDIA_URL = "https://f1.com/gaming/visst";

    @Mock
    ErgastProxy mErgastProxy;

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    FetchConstructorStandingsTask mFetchConstructorStandingsTask;

    @Test
    void should_not_call_ergast_for_constructor_standings_data_if_there_is_no_next_race_to_fetch_for() throws SQLException {
        when(mDatabase.getNextRaceToFetchConstructorStandingsFor()).thenReturn(Optional.empty());

        mFetchConstructorStandingsTask.run();

        verify(mErgastProxy, never()).fetchConstructorStandingsForRace(any(RaceRecord.class));
    }

    @Test
    void should_not_attempt_to_merge_in_constructor_standings_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mDatabase.getNextRaceToFetchConstructorStandingsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchConstructorStandingsForRace(RACE_RECORD)).thenReturn(emptyList());

        mFetchConstructorStandingsTask.run();

        verify(mDatabase, never()).mergeIntoConstructorStandingsData(anyList(), any(RaceRecord.class));
    }

    @Test
    void should_merge_in_constructor_standings_data_sent_from_ergast_to_database() throws SQLException, MalformedURLException {
        when(mDatabase.getNextRaceToFetchConstructorStandingsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchConstructorStandingsForRace(RACE_RECORD)).thenReturn(getConstructorStandingsData());

        mFetchConstructorStandingsTask.run();

        verify(mDatabase).mergeIntoConstructorStandingsData(getConstructorStandingsData(), RACE_RECORD);
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException, MalformedURLException {
        when(mDatabase.getNextRaceToFetchConstructorStandingsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchConstructorStandingsForRace(RACE_RECORD)).thenReturn(getConstructorStandingsData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoConstructorStandingsData(anyList(), eq(RACE_RECORD));

        mFetchConstructorStandingsTask.run();

        verify(mLogger).severe(anyString(), eq(TaskWrapper.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_set_last_fetched_race_after_merging_constructor_standings() throws SQLException, MalformedURLException {
        when(mDatabase.getNextRaceToFetchConstructorStandingsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchConstructorStandingsForRace(RACE_RECORD)).thenReturn(getConstructorStandingsData());

        mFetchConstructorStandingsTask.run();

        verify(mDatabase).mergeIntoConstructorStandingsData(anyList(), eq(RACE_RECORD));
        verify(mDatabase).setLastFetchedRaceInHistory(RACE_RECORD);
    }

    @Test
    void should_not_set_last_fetched_race_after_merging_constructor_standings_if_it_throws() throws SQLException, MalformedURLException {
        when(mDatabase.getNextRaceToFetchConstructorStandingsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchConstructorStandingsForRace(RACE_RECORD)).thenReturn(getConstructorStandingsData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoConstructorStandingsData(anyList(), eq(RACE_RECORD));

        mFetchConstructorStandingsTask.run();

        verify(mDatabase, never()).setLastFetchedRaceInHistory(any(RaceRecord.class));
    }

    private List<ConstructorStandingsData> getConstructorStandingsData() throws MalformedURLException {
        return singletonList(
            new ConstructorStandingsData(1, "1", BigDecimal.ONE, 1, new ConstructorData(
                "cId",
                WIKIPEDIA_URL,
                "name",
                Country.SPAIN.getNationalityKeywords().get(0)
            ))
        );
    }
}
