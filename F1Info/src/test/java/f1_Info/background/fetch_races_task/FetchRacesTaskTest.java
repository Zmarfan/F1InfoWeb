package f1_Info.background.fetch_races_task;

import f1_Info.background.TaskWrapper;
import f1_Info.constants.Country;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.circuit.CircuitData;
import f1_Info.ergast.responses.circuit.LocationData;
import f1_Info.ergast.responses.race.RaceData;
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
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FetchRacesTaskTest {
    private static final String WIKIPEDIA_URL = "https://f1.com/gaming/visst";

    @Mock
    ErgastProxy mErgastProxy;

    @Mock
    Database mDatabase;

    @Mock
    Logger mLogger;

    @InjectMocks
    FetchRacesTask mFetchRacesTask;

    @Test
    void should_not_call_ergast_for_races_data_if_there_is_no_next_season_to_fetch_for() throws SQLException {
        when(mDatabase.getNextSeasonToFetchForRaces()).thenReturn(Optional.empty());

        mFetchRacesTask.runTask();

        verify(mErgastProxy, never()).fetchRacesFromYear(anyInt());
    }

    @Test
    void should_not_attempt_to_merge_in_races_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mDatabase.getNextSeasonToFetchForRaces()).thenReturn(Optional.of(1998));
        when(mErgastProxy.fetchRacesFromYear(anyInt())).thenReturn(emptyList());

        mFetchRacesTask.run();

        verify(mDatabase, never()).mergeIntoRacesData(anyList());
    }

    @Test
    void should_merge_in_races_data_sent_from_ergast_to_database() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchForRaces()).thenReturn(Optional.of(1998));
        when(mErgastProxy.fetchRacesFromYear(1998)).thenReturn(getRaceData());

        mFetchRacesTask.run();

        verify(mDatabase).mergeIntoRacesData(getRaceData());
    }

    @Test
    void should_set_last_fetched_season_to_fetched_seasons_if_able_merge_in_all_races() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchForRaces()).thenReturn(Optional.of(1998));
        when(mErgastProxy.fetchRacesFromYear(1998)).thenReturn(getRaceData());

        mFetchRacesTask.run();

        verify(mDatabase).setLastFetchedSeason(1998);
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchForRaces()).thenReturn(Optional.of(1998));
        when(mErgastProxy.fetchRacesFromYear(1998)).thenReturn(getRaceData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoRacesData(anyList());

        mFetchRacesTask.run();

        verify(mLogger).severe(anyString(), eq(TaskWrapper.class), anyString(), any(SQLException.class));
    }

    @Test
    void should_never_set_last_fetched_season_of_race_data_if_unable_to_merge_in_all_fetched_races() throws SQLException, MalformedURLException, ParseException {
        when(mDatabase.getNextSeasonToFetchForRaces()).thenReturn(Optional.of(1998));
        when(mErgastProxy.fetchRacesFromYear(1998)).thenReturn(getRaceData());
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoRacesData(anyList());

        mFetchRacesTask.run();

        verify(mDatabase, never()).setLastFetchedSeason(anyInt());
    }


    private List<RaceData> getRaceData() throws MalformedURLException, ParseException {
        return List.of(
            new RaceData(1998, 1, WIKIPEDIA_URL, "race", null, "1998-01-01", null, null, null, null, null, new CircuitData(
                "circuit",
                WIKIPEDIA_URL,
                "circuit",
                new LocationData(BigDecimal.ZERO, BigDecimal.ZERO, "location", Country.GERMANY.getNames().get(0))
            ))
        );
    }
}
