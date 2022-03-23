package f1_Info.background;

import f1_Info.constants.Country;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.logger.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RareDataFetchingTaskTest {

    @Mock
    ErgastProxy mErgastProxy;

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    RareDataFetchingTask mRareDataFetchingTask;

    @Test
    void should_not_attempt_to_merge_in_constructors_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mErgastProxy.fetchAllConstructors()).thenReturn(emptyList());

        mRareDataFetchingTask.run();

        verify(mDatabase, never()).mergeIntoConstructorsData(anyList());
    }

    @Test
    void should_merge_in_data_sent_from_ergast_to_database() throws SQLException {
        final List<ConstructorData> data = List.of(new ConstructorData("1", "2", "3", Country.GERMANY.getNationality()));
        when(mErgastProxy.fetchAllConstructors()).thenReturn(data);

        mRareDataFetchingTask.run();

        verify(mDatabase).mergeIntoConstructorsData(data);
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException {
        final List<ConstructorData> data = List.of(new ConstructorData("1", "2", "3", Country.GERMANY.getNationality()));
        when(mErgastProxy.fetchAllConstructors()).thenReturn(data);
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoConstructorsData(anyList());

        mRareDataFetchingTask.run();

        verify(mLogger).severe(anyString(), eq(RareDataFetchingTask.class), anyString(), any(SQLException.class));
    }
}
