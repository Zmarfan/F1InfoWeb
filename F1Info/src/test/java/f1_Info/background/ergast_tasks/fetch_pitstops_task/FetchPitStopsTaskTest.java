package f1_Info.background.ergast_tasks.fetch_pitstops_task;

import common.logger.Logger;
import f1_Info.background.TaskWrapper;
import f1_Info.background.ergast_tasks.RaceRecord;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.pit_stop.PitStopData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FetchPitStopsTaskTest {
    private static final RaceRecord RACE_RECORD = new RaceRecord(1998, 2, 1);

    @Mock
    ErgastProxy mErgastProxy;

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    FetchPitStopsTask mFetchPitStopsTask;

    @Test
    void should_not_call_ergast_for_pitstop_data_if_there_is_no_next_race_to_fetch_for() throws SQLException {
        when(mDatabase.getNextRaceToFetchPitStopsFor()).thenReturn(Optional.empty());

        mFetchPitStopsTask.run();

        verify(mErgastProxy, never()).fetchPitStopsForRace(any(RaceRecord.class));
    }

    @Test
    void should_not_attempt_to_merge_in_pitstop_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mDatabase.getNextRaceToFetchPitStopsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchPitStopsForRace(RACE_RECORD)).thenReturn(emptyList());

        mFetchPitStopsTask.run();

        verify(mDatabase, never()).mergeIntoPitStopsData(anyList(), any(RaceRecord.class));
    }

    @Test
    void should_merge_in_pitstop_data_sent_from_ergast_to_database() throws SQLException {
        when(mDatabase.getNextRaceToFetchPitStopsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchPitStopsForRace(RACE_RECORD)).thenReturn(getPitstopData());

        mFetchPitStopsTask.run();

        verify(mDatabase).mergeIntoPitStopsData(getPitstopData(), RACE_RECORD);
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException {
        when(mDatabase.getNextRaceToFetchPitStopsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchPitStopsForRace(RACE_RECORD)).thenReturn(getPitstopData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoPitStopsData(anyList(), eq(RACE_RECORD));

        mFetchPitStopsTask.run();

        verify(mLogger).severe(anyString(), eq(TaskWrapper.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_set_last_fetched_race_after_merging_pitstops() throws SQLException {
        when(mDatabase.getNextRaceToFetchPitStopsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchPitStopsForRace(RACE_RECORD)).thenReturn(getPitstopData());

        mFetchPitStopsTask.run();

        verify(mDatabase).mergeIntoPitStopsData(anyList(), eq(RACE_RECORD));
        verify(mDatabase).setLastFetchedRaceInHistory(RACE_RECORD);
    }

    @Test
    void should_not_set_last_fetched_race_after_merging_pitstops_if_it_throws() throws SQLException {
        when(mDatabase.getNextRaceToFetchPitStopsFor()).thenReturn(Optional.of(RACE_RECORD));
        when(mErgastProxy.fetchPitStopsForRace(RACE_RECORD)).thenReturn(getPitstopData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoPitStopsData(anyList(), eq(RACE_RECORD));

        mFetchPitStopsTask.run();

        verify(mDatabase, never()).setLastFetchedRaceInHistory(any(RaceRecord.class));
    }

    private List<PitStopData> getPitstopData() {
        return List.of(
            new PitStopData("driver_id1", 23, 1, "12:32:14Z", "4.5"),
            new PitStopData("driver_id2", 33, 2, "12:52:14Z", "5.5"),
            new PitStopData("driver_id3", 5, 1, "11:52:14Z", "6.5")
        );
    }
}
