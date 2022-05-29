package f1_Info.background.ergast_tasks.fetch_finish_status_task;

import common.constants.f1.FinishStatus;
import common.logger.Logger;
import f1_Info.background.TaskWrapper;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.FinishStatusData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FetchFinishStatusTaskTest {
    @Mock
    ErgastProxy mErgastProxy;

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    FetchFinishStatusTask mFetchFinishStatusTask;

    @Test
    void should_not_attempt_to_merge_in_finish_status_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mErgastProxy.fetchAllFinishStatuses()).thenReturn(emptyList());

        mFetchFinishStatusTask.run();

        verify(mDatabase, never()).mergeIntoFinishStatusData(anyList());
    }

    @Test
    void should_merge_in_finish_status_data_sent_from_ergast_to_database() throws SQLException {
        final List<FinishStatusData> data = List.of(new FinishStatusData(FinishStatus.PNEUMATICS.getStringCode()));
        when(mErgastProxy.fetchAllFinishStatuses()).thenReturn(data);

        mFetchFinishStatusTask.run();

        verify(mDatabase).mergeIntoFinishStatusData(data);
    }

    @Test
    void should_log_warning_for_every_finish_status_that_can_not_be_parsed_to_finish_status_enum() {
        final List<FinishStatusData> data = List.of(
            new FinishStatusData("invalid1"),
            new FinishStatusData("invalid2"),
            new FinishStatusData(FinishStatus.FUEL_PUMP.getStringCode()),
            new FinishStatusData(FinishStatus.PLUS_13_LAPS.getStringCode()),
            new FinishStatusData("invalid3")
        );
        when(mErgastProxy.fetchAllFinishStatuses()).thenReturn(data);

        mFetchFinishStatusTask.run();

        verify(mLogger, times(3)).warning(anyString(), eq(FetchFinishStatusTask.class), anyString());
    }

    @Test
    void should_merge_in_finish_status_data_sent_from_ergast_to_database_even_for_entries_that_can_not_be_parsed_to_enum() throws SQLException {
        final List<FinishStatusData> data = List.of(
            new FinishStatusData("invalid1"),
            new FinishStatusData("invalid2"),
            new FinishStatusData(FinishStatus.FUEL_PUMP.getStringCode()),
            new FinishStatusData(FinishStatus.PLUS_13_LAPS.getStringCode()),
            new FinishStatusData("invalid3")
        );
        when(mErgastProxy.fetchAllFinishStatuses()).thenReturn(data);

        mFetchFinishStatusTask.run();

        verify(mDatabase).mergeIntoFinishStatusData(data);
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException {
        final List<FinishStatusData> data = List.of(new FinishStatusData(FinishStatus.SEAT.getStringCode()));
        when(mErgastProxy.fetchAllFinishStatuses()).thenReturn(data);
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoFinishStatusData(anyList());

        mFetchFinishStatusTask.run();

        verify(mLogger).severe(anyString(), eq(TaskWrapper.class), anyString(), any(SQLException.class));
    }
}
