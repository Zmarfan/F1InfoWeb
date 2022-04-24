package f1_Info.background.ergast_tasks.fetch_results_tasks.fetch_race_results_task;

import f1_Info.background.TaskWrapper;
import f1_Info.background.ergast_tasks.ergast.ErgastProxy;
import f1_Info.background.ergast_tasks.ergast.responses.ConstructorData;
import f1_Info.background.ergast_tasks.ergast.responses.DriverData;
import f1_Info.background.ergast_tasks.ergast.responses.results.*;
import f1_Info.constants.Country;
import f1_Info.constants.FinishStatus;
import f1_Info.constants.SpeedUnit;
import f1_Info.logger.Logger;
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
class FetchRaceResultsTaskTest {
    private static final String WIKIPEDIA_URL = "https://f1.com/gaming/visst";

    @Mock
    ErgastProxy mErgastProxy;

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    FetchRaceResultsTask mFetchRaceResultsTask;

    @Test
    void should_not_attempt_to_merge_in_race_results_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mDatabase.getNextSeasonToFetchRaceResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchRaceResultsForSeason(anyInt())).thenReturn(emptyList());

        mFetchRaceResultsTask.run();

        verify(mDatabase, never()).mergeIntoRaceResultsData(anyList());
    }

    @Test
    void should_merge_in_race_results_sent_from_ergast_to_database() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchRaceResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchRaceResultsForSeason(1998)).thenReturn(getRaceResultData());

        mFetchRaceResultsTask.run();

        verify(mDatabase).mergeIntoRaceResultsData(getRaceResultData());
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchRaceResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchRaceResultsForSeason(1998)).thenReturn(getRaceResultData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoRaceResultsData(anyList());

        mFetchRaceResultsTask.run();

        verify(mLogger).severe(anyString(), eq(TaskWrapper.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_set_last_fetched_season_after_merging_race_results() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchRaceResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchRaceResultsForSeason(1998)).thenReturn(getRaceResultData());

        mFetchRaceResultsTask.run();

        verify(mDatabase).mergeIntoRaceResultsData(anyList());
        verify(mDatabase).setLastFetchedSeason(1998);
    }

    @Test
    void should_not_set_last_fetched_season_after_merging_race_results_if_it_throws() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchRaceResultsFor()).thenReturn(1998);
        when(mErgastProxy.fetchRaceResultsForSeason(1998)).thenReturn(getRaceResultData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoRaceResultsData(anyList());

        mFetchRaceResultsTask.run();

        verify(mDatabase, never()).setLastFetchedSeason(anyInt());
    }

    private List<ResultDataHolder> getRaceResultData() throws MalformedURLException, ParseException {
        return singletonList(
            new ResultDataHolder(1998, 4, null, singletonList(new ResultData(
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
            )), null)
        );
    }
}
