package f1_Info.background.fetch_pitstops_task;

import f1_Info.background.TaskWrapper;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.pit_stop.PitStopData;
import f1_Info.logger.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FetchPitStopsTaskTest {
    private static final PitStopFetchInformationRecord FETCH_INFORMATION_RECORD = new PitStopFetchInformationRecord(1998, 2);

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
        when(mDatabase.getNextSeasonAndRoundToFetchPitStopsFor()).thenReturn(Optional.empty());

        mFetchPitStopsTask.run();

        verify(mErgastProxy, never()).fetchPitStopsFromRoundAndSeason(any(PitStopFetchInformationRecord.class));
    }

    @Test
    void should_not_attempt_to_merge_in_pitstop_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mDatabase.getNextSeasonAndRoundToFetchPitStopsFor()).thenReturn(Optional.of(FETCH_INFORMATION_RECORD));
        when(mErgastProxy.fetchPitStopsFromRoundAndSeason(FETCH_INFORMATION_RECORD)).thenReturn(emptyList());

        mFetchPitStopsTask.run();

        verify(mDatabase, never()).mergeIntoPitStopsData(anyList(), any(PitStopFetchInformationRecord.class));
    }

    @Test
    void should_merge_in_pitstop_data_sent_from_ergast_to_database() throws SQLException, ParseException {
        when(mDatabase.getNextSeasonAndRoundToFetchPitStopsFor()).thenReturn(Optional.of(FETCH_INFORMATION_RECORD));
        when(mErgastProxy.fetchPitStopsFromRoundAndSeason(FETCH_INFORMATION_RECORD)).thenReturn(getPitstopData());

        mFetchPitStopsTask.run();

        verify(mDatabase).mergeIntoPitStopsData(getPitstopData(), FETCH_INFORMATION_RECORD);
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException, ParseException {
        when(mDatabase.getNextSeasonAndRoundToFetchPitStopsFor()).thenReturn(Optional.of(FETCH_INFORMATION_RECORD));
        when(mErgastProxy.fetchPitStopsFromRoundAndSeason(FETCH_INFORMATION_RECORD)).thenReturn(getPitstopData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoPitStopsData(anyList(), eq(FETCH_INFORMATION_RECORD));

        mFetchPitStopsTask.run();

        verify(mLogger).severe(anyString(), eq(TaskWrapper.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_set_last_fetched_race_after_merging_pitstops() throws SQLException, ParseException {
        when(mDatabase.getNextSeasonAndRoundToFetchPitStopsFor()).thenReturn(Optional.of(FETCH_INFORMATION_RECORD));
        when(mErgastProxy.fetchPitStopsFromRoundAndSeason(FETCH_INFORMATION_RECORD)).thenReturn(getPitstopData());

        mFetchPitStopsTask.run();

        verify(mDatabase).mergeIntoPitStopsData(anyList(), eq(FETCH_INFORMATION_RECORD));
        verify(mDatabase).setLastFetchedPitstopsForRace(FETCH_INFORMATION_RECORD);
    }

    @Test
    void should_not_set_last_fetched_race_after_merging_pitstops_if_it_throws() throws SQLException, ParseException {
        when(mDatabase.getNextSeasonAndRoundToFetchPitStopsFor()).thenReturn(Optional.of(FETCH_INFORMATION_RECORD));
        when(mErgastProxy.fetchPitStopsFromRoundAndSeason(FETCH_INFORMATION_RECORD)).thenReturn(getPitstopData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoPitStopsData(anyList(), eq(FETCH_INFORMATION_RECORD));

        mFetchPitStopsTask.run();

        verify(mDatabase, never()).setLastFetchedPitstopsForRace(any(PitStopFetchInformationRecord.class));
    }

    private List<PitStopData> getPitstopData() throws ParseException {
        return List.of(
            new PitStopData("driver_id1", 23, 1, "12:32:14Z", BigDecimal.valueOf(4.5)),
            new PitStopData("driver_id2", 33, 2, "12:52:14Z", BigDecimal.valueOf(5.5)),
            new PitStopData("driver_id3", 5, 1, "11:52:14Z", BigDecimal.valueOf(6.5))
        );
    }
}
