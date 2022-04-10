package f1_Info.background.ergast_tasks.fetch_constructors_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.ergast_tasks.fetch_constructors_task.Database;
import f1_Info.background.ergast_tasks.fetch_constructors_task.FetchConstructorsTask;
import f1_Info.constants.Country;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
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
class FetchConstructorsTaskTest {
    private static final String WIKIPEDIA_URL = "https://f1.com/gaming/visst";

    @Mock
    ErgastProxy mErgastProxy;

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    FetchConstructorsTask mFetchConstructorsTask;

    @Test
    void should_not_attempt_to_merge_in_constructors_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mErgastProxy.fetchAllConstructors()).thenReturn(emptyList());

        mFetchConstructorsTask.run();

        verify(mDatabase, never()).mergeIntoConstructorsData(anyList());
    }

    @Test
    void should_merge_in_constructors_data_sent_from_ergast_to_database() throws SQLException, MalformedURLException {
        final List<ConstructorData> data = List.of(new ConstructorData("1", WIKIPEDIA_URL, "3", Country.GERMANY.getNationalityKeywords().get(0)));
        when(mErgastProxy.fetchAllConstructors()).thenReturn(data);

        mFetchConstructorsTask.run();

        verify(mDatabase).mergeIntoConstructorsData(data);
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException, MalformedURLException {
        final List<ConstructorData> data = List.of(new ConstructorData("1", WIKIPEDIA_URL, "3", Country.GERMANY.getNationalityKeywords().get(0)));
        when(mErgastProxy.fetchAllConstructors()).thenReturn(data);
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoConstructorsData(anyList());

        mFetchConstructorsTask.run();

        verify(mLogger).severe(anyString(), eq(TaskWrapper.class), anyString(), any(SQLException.class));
    }
}
