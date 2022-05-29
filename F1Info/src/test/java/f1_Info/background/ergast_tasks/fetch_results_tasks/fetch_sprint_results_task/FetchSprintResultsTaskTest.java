package f1_Info.background.ergast_tasks.fetch_results_tasks.fetch_sprint_results_task;

import common.constants.Country;
import common.constants.f1.FinishStatus;
import common.constants.f1.SpeedUnit;
import common.logger.Logger;
import f1_Info.background.TaskWrapper;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
import f1_Info.background.ergast_tasks.ergast.responses.DriverData;
import f1_Info.background.ergast_tasks.ergast.responses.results.*;
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

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FetchSprintResultsTaskTest {
    private static final String WIKIPEDIA_URL = "https://f1.com/gaming/visst";

    @Mock
    ErgastProxy mErgastProxy;

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    FetchSprintResultsTask mFetchSprintResultsTask;

    @Test
    void should_not_attempt_to_merge_in_sprint_results_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mDatabase.getNextSeasonToFetchSprintResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchSprintResultsForSeason(anyInt())).thenReturn(emptyList());

        mFetchSprintResultsTask.run();

        verify(mDatabase, never()).mergeIntoSprintResultsData(anyList());
    }

    @Test
    void should_merge_in_sprint_results_sent_from_ergast_to_database() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchSprintResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchSprintResultsForSeason(1998)).thenReturn(getSprintResultData());

        mFetchSprintResultsTask.run();

        verify(mDatabase).mergeIntoSprintResultsData(getSprintResultData());
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchSprintResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchSprintResultsForSeason(1998)).thenReturn(getSprintResultData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoSprintResultsData(anyList());

        mFetchSprintResultsTask.run();

        verify(mLogger).severe(anyString(), eq(TaskWrapper.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_set_last_fetched_season_after_merging_sprint_results() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchSprintResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchSprintResultsForSeason(1998)).thenReturn(getSprintResultData());

        mFetchSprintResultsTask.run();

        verify(mDatabase).mergeIntoSprintResultsData(anyList());
        verify(mDatabase).setLastFetchedSeason(1998);
    }

    @Test
    void should_not_set_last_fetched_season_after_merging_sprint_results_if_it_throws() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchSprintResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchSprintResultsForSeason(1998)).thenReturn(getSprintResultData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoSprintResultsData(anyList());

        mFetchSprintResultsTask.run();

        verify(mDatabase, never()).setLastFetchedSeason(anyInt());
    }

    private List<ResultDataHolder> getSprintResultData() throws MalformedURLException, ParseException {
        return singletonList(
            new ResultDataHolder(1998, 4, singletonList(new ResultData(
                33,
                1,
                "1",
                BigDecimal.valueOf(25),
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
                1,
                48,
                FinishStatus.FINISHED.getStringCode(),
                new TimeData(1000000L, "50:00.00"),
                new FastestLapData(1, 33, new TimeData(null, "1:01:321"), new AverageSpeedData(SpeedUnit.KPH.getStringCode(), BigDecimal.valueOf(324)))
            )), null, null)
        );
    }
}
