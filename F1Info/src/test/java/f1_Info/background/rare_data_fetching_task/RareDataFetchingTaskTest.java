package f1_Info.background.rare_data_fetching_task;

import f1_Info.constants.Country;
import f1_Info.ergast.ErgastProxy;
import f1_Info.ergast.responses.*;
import f1_Info.ergast.responses.circuit.CircuitData;
import f1_Info.ergast.responses.circuit.LocationData;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RareDataFetchingTaskTest {

    private static final String WIKIPEDIA_URL = "https://f1.com/gaming/visst";

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
    void should_merge_in_constructors_data_sent_from_ergast_to_database() throws SQLException, MalformedURLException {
        final List<ConstructorData> data = List.of(new ConstructorData("1", WIKIPEDIA_URL, "3", Country.GERMANY.getNationalityKeywords().get(0)));
        when(mErgastProxy.fetchAllConstructors()).thenReturn(data);

        mRareDataFetchingTask.run();

        verify(mDatabase).mergeIntoConstructorsData(data);
    }

    @Test
    void should_not_attempt_to_merge_in_drivers_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mErgastProxy.fetchAllDrivers()).thenReturn(emptyList());

        mRareDataFetchingTask.run();

        verify(mDatabase, never()).mergeIntoDriversData(anyList());
    }

    @Test
    void should_merge_in_drivers_data_sent_from_ergast_to_database() throws SQLException, ParseException, MalformedURLException {
        final List<DriverData> data = List.of(new DriverData(
            "",
            WIKIPEDIA_URL,
            "",
            "",
            "1999-01-01",
            Country.GERMANY.getNationalityKeywords().get(0),
            0,
            ""
        ));
        when(mErgastProxy.fetchAllDrivers()).thenReturn(data);

        mRareDataFetchingTask.run();

        verify(mDatabase).mergeIntoDriversData(data);
    }

    @Test
    void should_not_attempt_to_merge_in_seasons_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mErgastProxy.fetchAllSeasons()).thenReturn(emptyList());

        mRareDataFetchingTask.run();

        verify(mDatabase, never()).mergeIntoSeasonsData(anyList());
    }

    @Test
    void should_merge_in_seasons_data_sent_from_ergast_to_database() throws SQLException, MalformedURLException {
        final List<SeasonData> data = List.of(new SeasonData(1950, WIKIPEDIA_URL));
        when(mErgastProxy.fetchAllSeasons()).thenReturn(data);

        mRareDataFetchingTask.run();

        verify(mDatabase).mergeIntoSeasonsData(data);
    }

    @Test
    void should_not_attempt_to_merge_in_circuits_data_to_database_if_no_data_was_returned_from_ergast() throws SQLException {
        when(mErgastProxy.fetchAllCircuits()).thenReturn(emptyList());

        mRareDataFetchingTask.run();

        verify(mDatabase, never()).mergeIntoCircuitsData(anyList());
    }

    @Test
    void should_merge_in_circuits_data_sent_from_ergast_to_database() throws SQLException, MalformedURLException {
        final List<CircuitData> data = List.of(
            new CircuitData("", WIKIPEDIA_URL, "", new LocationData(BigDecimal.ZERO, BigDecimal.ZERO, "", Country.GERMANY.getNames().get(0)))
        );
        when(mErgastProxy.fetchAllCircuits()).thenReturn(data);

        mRareDataFetchingTask.run();

        verify(mDatabase).mergeIntoCircuitsData(data);
    }

    @Test
    void should_log_severe_if_exception_is_thrown() throws SQLException, MalformedURLException {
        final List<ConstructorData> data = List.of(new ConstructorData("1", WIKIPEDIA_URL, "3", Country.GERMANY.getNationalityKeywords().get(0)));
        when(mErgastProxy.fetchAllConstructors()).thenReturn(data);
        doThrow(new SQLException("error")).when(mDatabase).mergeIntoConstructorsData(anyList());

        mRareDataFetchingTask.run();

        verify(mLogger).severe(anyString(), eq(RareDataFetchingTask.class), anyString(), any(SQLException.class));
    }
}
