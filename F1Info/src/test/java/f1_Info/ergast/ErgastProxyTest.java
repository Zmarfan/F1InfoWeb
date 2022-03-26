package f1_Info.ergast;

import f1_Info.configuration.Configuration;
import f1_Info.configuration.ConfigurationRules;
import f1_Info.constants.Country;
import f1_Info.ergast.responses.*;
import f1_Info.logger.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ErgastProxyTest {
    private static final ConfigurationRules MOCK_CONFIGURATION = new ConfigurationRules("", "", "", true);
    private static final ConfigurationRules LIVE_CONFIGURATION = new ConfigurationRules("", "", "", false);

    @Mock
    Parser mParser;

    @Mock
    Fetcher mFetcher;

    @Mock
    Configuration mConfiguration;

    @Mock
    Logger mLogger;

    @InjectMocks
    ErgastProxy mErgastProxy;

    @Test
    void should_not_fetch_constructor_data_from_ergast_if_running_mock_configuration() throws IOException {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);

        mErgastProxy.fetchAllConstructors();

        verify(mFetcher, never()).readDataAsJsonStringFromUri(anyString(), anyInt());
    }

    @Test
    void should_return_empty_list_of_constructors_if_running_mock_configuration() {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);
        assertEquals(emptyList(), mErgastProxy.fetchAllConstructors());
    }

    @Test
    void should_return_empty_list_if_ioexception_gets_thrown_while_fetching_constructors() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString(), anyInt())).thenThrow(new IOException());

        assertEquals(emptyList(), mErgastProxy.fetchAllConstructors());
    }

    @Test
    void should_log_severe_if_ioexception_gets_thrown_while_fetching_constructors() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString(), anyInt())).thenThrow(new IOException());

        mErgastProxy.fetchAllConstructors();

        verify(mLogger).severe(anyString(), eq(ErgastProxy.class), anyString(), any(IOException.class));
    }

    @Test
    void should_return_formatted_data_from_parser_when_fetching_constructors() throws IOException {
        final List<ConstructorData> expectedReturnData = List.of(new ConstructorData("", "", "", Country.GERMANY.getNationalityKeywords().get(0)));

        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mParser.parseConstructorsResponseToObjects(any())).thenReturn(expectedReturnData);

        assertEquals(expectedReturnData, mErgastProxy.fetchAllConstructors());
    }

    @Test
    void should_not_fetch_driver_data_from_ergast_if_running_mock_configuration() throws IOException {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);

        mErgastProxy.fetchAllDrivers();

        verify(mFetcher, never()).readDataAsJsonStringFromUri(anyString(), anyInt());
    }

    @Test
    void should_return_empty_list_of_drivers_if_running_mock_configuration() {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);
        assertEquals(emptyList(), mErgastProxy.fetchAllDrivers());
    }

    @Test
    void should_return_empty_list_if_ioexception_gets_thrown_while_fetching_drivers() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString(), anyInt())).thenThrow(new IOException());

        assertEquals(emptyList(), mErgastProxy.fetchAllDrivers());
    }

    @Test
    void should_log_severe_if_ioexception_gets_thrown_while_fetching_drivers() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString(), anyInt())).thenThrow(new IOException());

        mErgastProxy.fetchAllDrivers();

        verify(mLogger).severe(anyString(), eq(ErgastProxy.class), anyString(), any(IOException.class));
    }

    @Test
    void should_return_formatted_data_from_parser_when_fetching_drivers() throws IOException, ParseException {
        final List<DriverData> expectedReturnData = List.of(new DriverData(
            "",
            "",
            "",
            "",
            "1999-01-01",
            Country.GERMANY.getNationalityKeywords().get(0),
            0,
            ""
        ));

        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mParser.parseDriversResponseToObjects(any())).thenReturn(expectedReturnData);

        assertEquals(expectedReturnData, mErgastProxy.fetchAllDrivers());
    }

    @Test
    void should_not_fetch_season_data_from_ergast_if_running_mock_configuration() throws IOException {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);

        mErgastProxy.fetchAllSeasons();

        verify(mFetcher, never()).readDataAsJsonStringFromUri(anyString(), anyInt());
    }

    @Test
    void should_return_empty_list_of_seasons_if_running_mock_configuration() {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);
        assertEquals(emptyList(), mErgastProxy.fetchAllSeasons());
    }

    @Test
    void should_return_empty_list_if_ioexception_gets_thrown_while_fetching_seasons() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString(), anyInt())).thenThrow(new IOException());

        assertEquals(emptyList(), mErgastProxy.fetchAllSeasons());
    }

    @Test
    void should_log_severe_if_ioexception_gets_thrown_while_fetching_seasons() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString(), anyInt())).thenThrow(new IOException());

        mErgastProxy.fetchAllSeasons();

        verify(mLogger).severe(anyString(), eq(ErgastProxy.class), anyString(), any(IOException.class));
    }

    @Test
    void should_return_formatted_data_from_parser_when_fetching_seasons() throws IOException {
        final List<SeasonData> expectedReturnData = List.of(new SeasonData(1950, ""));

        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mParser.parseSeasonsResponseToObjects(any())).thenReturn(expectedReturnData);

        assertEquals(expectedReturnData, mErgastProxy.fetchAllSeasons());
    }

    @Test
    void should_not_fetch_circuit_data_from_ergast_if_running_mock_configuration() throws IOException {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);

        mErgastProxy.fetchAllCircuits();

        verify(mFetcher, never()).readDataAsJsonStringFromUri(anyString(), anyInt());
    }

    @Test
    void should_return_empty_list_of_circuits_if_running_mock_configuration() {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);
        assertEquals(emptyList(), mErgastProxy.fetchAllCircuits());
    }

    @Test
    void should_return_empty_list_if_ioexception_gets_thrown_while_fetching_circuits() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString(), anyInt())).thenThrow(new IOException());

        assertEquals(emptyList(), mErgastProxy.fetchAllCircuits());
    }

    @Test
    void should_log_severe_if_ioexception_gets_thrown_while_fetching_circuits() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString(), anyInt())).thenThrow(new IOException());

        mErgastProxy.fetchAllCircuits();

        verify(mLogger).severe(anyString(), eq(ErgastProxy.class), anyString(), any(IOException.class));
    }

    @Test
    void should_return_formatted_data_from_parser_when_fetching_circuits() throws IOException {
        final List<CircuitData> expectedReturnData = List.of(
            new CircuitData("", "", "", new LocationData(BigDecimal.ZERO, BigDecimal.ZERO, "", Country.GERMANY.getNames().get(0)))
        );

        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mParser.parseCircuitsResponseToObjects(any())).thenReturn(expectedReturnData);

        assertEquals(expectedReturnData, mErgastProxy.fetchAllCircuits());
    }
}
