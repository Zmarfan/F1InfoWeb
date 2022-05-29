package f1_Info.background.ergast_tasks.fetch_qualifying_results_task;

import common.constants.Country;
import common.logger.Logger;
import f1_Info.background.TaskWrapper;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
import f1_Info.background.ergast_tasks.ergast.responses.DriverData;
import f1_Info.background.ergast_tasks.ergast.responses.results.QualifyingResultData;
import f1_Info.background.ergast_tasks.ergast.responses.results.ResultDataHolder;
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
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FetchQualifyingResultsTaskTest {
    private static final String WIKIPEDIA_URL = "https://f1.com/gaming/visst";

    @Mock
    ErgastProxy mErgastProxy;

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    FetchQualifyingResultsTask mFetchQualifyingResultsTask;

    @Test
    void should_not_attempt_to_merge_in_qualifying_results_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mDatabase.getNextSeasonToFetchQualifyingResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchQualifyingResultsForSeason(anyInt())).thenReturn(emptyList());

        mFetchQualifyingResultsTask.run();

        verify(mDatabase, never()).mergeIntoQualifyingResultsData(anyList());
    }

    @Test
    void should_merge_in_qualifying_results_sent_from_ergast_to_database() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchQualifyingResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchQualifyingResultsForSeason(1998)).thenReturn(getQualifyingResultData());

        mFetchQualifyingResultsTask.run();

        verify(mDatabase).mergeIntoQualifyingResultsData(getQualifyingResultData());
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchQualifyingResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchQualifyingResultsForSeason(1998)).thenReturn(getQualifyingResultData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoQualifyingResultsData(anyList());

        mFetchQualifyingResultsTask.run();

        verify(mLogger).severe(anyString(), eq(TaskWrapper.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_set_last_fetched_season_after_merging_qualifying_results() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchQualifyingResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchQualifyingResultsForSeason(1998)).thenReturn(getQualifyingResultData());

        mFetchQualifyingResultsTask.run();

        verify(mDatabase).mergeIntoQualifyingResultsData(anyList());
        verify(mDatabase).setLastFetchedSeason(1998);
    }

    @Test
    void should_not_set_last_fetched_season_after_merging_qualifying_results_if_it_throws() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchQualifyingResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchQualifyingResultsForSeason(1998)).thenReturn(getQualifyingResultData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoQualifyingResultsData(anyList());

        mFetchQualifyingResultsTask.run();

        verify(mDatabase, never()).setLastFetchedSeason(anyInt());
    }

    private List<ResultDataHolder> getQualifyingResultData() throws MalformedURLException, ParseException {
        return singletonList(new ResultDataHolder(1998, 3, null, null, singletonList(new QualifyingResultData(
            31,
            1,
            new DriverData(
                "filip",
                WIKIPEDIA_URL,
                "F",
                "P",
                "1998-04-11",
                Country.SWEDEN.getNationalityKeywords().get(0),
                33,
                "FIL"
            ),
            new ConstructorData("const", WIKIPEDIA_URL, "cool", Country.UNITED_KINGDOM.getNationalityKeywords().get(0)),
            "1:30.132",
            "1:29.552",
            "1:29.321"
        ))));
    }
}
