package f1_Info.background.ergast_tasks.fetch_lap_times_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.lap_times.LapTimeData;
import f1_Info.background.ergast_tasks.ergast.responses.lap_times.TimingData;
import f1_Info.background.ergast_tasks.fetch_lap_times_task.Database;
import f1_Info.background.ergast_tasks.fetch_lap_times_task.FetchLapTimesTask;
import f1_Info.background.ergast_tasks.fetch_lap_times_task.LapTimesFetchInformationRecord;
import f1_Info.logger.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FetchLapTimesTaskTest {
    private static final LapTimesFetchInformationRecord FETCH_INFORMATION_RECORD = new LapTimesFetchInformationRecord(1998, 2, 1);

    @Mock
    ErgastProxy mErgastProxy;

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    FetchLapTimesTask mFetchLapTimesTask;

    @Test
    void should_not_call_ergast_for_lap_times_data_if_there_is_no_next_race_to_fetch_for() throws SQLException {
        when(mDatabase.getNextSeasonAndRoundToFetchLapTimesFor()).thenReturn(Optional.empty());

        mFetchLapTimesTask.run();

        verify(mErgastProxy, never()).fetchLapTimesFromRoundAndSeason(any(LapTimesFetchInformationRecord.class));
    }

    @Test
    void should_not_attempt_to_merge_in_lap_times_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mDatabase.getNextSeasonAndRoundToFetchLapTimesFor()).thenReturn(Optional.of(FETCH_INFORMATION_RECORD));
        when(mErgastProxy.fetchLapTimesFromRoundAndSeason(FETCH_INFORMATION_RECORD)).thenReturn(emptyList());

        mFetchLapTimesTask.run();

        verify(mDatabase, never()).mergeIntoLapTimesData(anyList(), any(LapTimesFetchInformationRecord.class));
    }

    @Test
    void should_merge_in_lap_times_data_sent_from_ergast_to_database() throws SQLException, ParseException {
        when(mDatabase.getNextSeasonAndRoundToFetchLapTimesFor()).thenReturn(Optional.of(FETCH_INFORMATION_RECORD));
        when(mErgastProxy.fetchLapTimesFromRoundAndSeason(FETCH_INFORMATION_RECORD)).thenReturn(getLapTimesData());

        mFetchLapTimesTask.run();

        verify(mDatabase).mergeIntoLapTimesData(getLapTimesData(), FETCH_INFORMATION_RECORD);
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException, ParseException {
        when(mDatabase.getNextSeasonAndRoundToFetchLapTimesFor()).thenReturn(Optional.of(FETCH_INFORMATION_RECORD));
        when(mErgastProxy.fetchLapTimesFromRoundAndSeason(FETCH_INFORMATION_RECORD)).thenReturn(getLapTimesData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoLapTimesData(anyList(), eq(FETCH_INFORMATION_RECORD));

        mFetchLapTimesTask.run();

        verify(mLogger).severe(anyString(), eq(TaskWrapper.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_set_last_fetched_race_after_merging_lap_times() throws SQLException, ParseException {
        when(mDatabase.getNextSeasonAndRoundToFetchLapTimesFor()).thenReturn(Optional.of(FETCH_INFORMATION_RECORD));
        when(mErgastProxy.fetchLapTimesFromRoundAndSeason(FETCH_INFORMATION_RECORD)).thenReturn(getLapTimesData());

        mFetchLapTimesTask.run();

        verify(mDatabase).mergeIntoLapTimesData(anyList(), eq(FETCH_INFORMATION_RECORD));
        verify(mDatabase).setLastFetchedLapTimesForRace(FETCH_INFORMATION_RECORD);
    }

    @Test
    void should_not_set_last_fetched_race_after_merging_lap_times_if_it_throws() throws SQLException, ParseException {
        when(mDatabase.getNextSeasonAndRoundToFetchLapTimesFor()).thenReturn(Optional.of(FETCH_INFORMATION_RECORD));
        when(mErgastProxy.fetchLapTimesFromRoundAndSeason(FETCH_INFORMATION_RECORD)).thenReturn(getLapTimesData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoLapTimesData(anyList(), eq(FETCH_INFORMATION_RECORD));

        mFetchLapTimesTask.run();

        verify(mDatabase, never()).setLastFetchedLapTimesForRace(any(LapTimesFetchInformationRecord.class));
    }

    private List<LapTimeData> getLapTimesData() throws ParseException {
        return List.of(
            new LapTimeData(1, singletonList(new TimingData("driverId1", 1, "1:12.001"))),
            new LapTimeData(2, singletonList(new TimingData("driverId1", 1, "1:09.001")))
        );
    }
}
