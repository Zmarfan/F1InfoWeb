package f1_Info.background.fetch_seasons_task;

import f1_Info.background.TaskWrapper;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.SeasonData;
import f1_Info.logger.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FetchSeasonsTaskTest {
    private static final String WIKIPEDIA_URL = "https://f1.com/gaming/visst";

    @Mock
    ErgastProxy mErgastProxy;

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    FetchSeasonsTask mFetchSeasonsTask;

    @Test
    void should_not_attempt_to_merge_in_seasons_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mErgastProxy.fetchAllSeasons()).thenReturn(emptyList());

        mFetchSeasonsTask.run();

        verify(mDatabase, never()).mergeIntoSeasonsData(anyList());
    }

    @Test
    void should_merge_in_seasons_data_sent_from_ergast_to_database() throws SQLException, MalformedURLException {
        final List<SeasonData> data = List.of(new SeasonData(1950, WIKIPEDIA_URL));
        when(mErgastProxy.fetchAllSeasons()).thenReturn(data);

        mFetchSeasonsTask.run();

        verify(mDatabase).mergeIntoSeasonsData(data);
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException, MalformedURLException {
        final List<SeasonData> data = List.of(new SeasonData(1950, WIKIPEDIA_URL));
        when(mErgastProxy.fetchAllSeasons()).thenReturn(data);
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoSeasonsData(anyList());

        mFetchSeasonsTask.run();

        verify(mLogger).severe(anyString(), eq(TaskWrapper.class), anyString(), any(SQLException.class));
    }
}
