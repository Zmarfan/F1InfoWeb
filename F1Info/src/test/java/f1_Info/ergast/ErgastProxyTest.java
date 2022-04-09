package f1_Info.ergast;

import f1_Info.background.fetch_pitstops_task.PitStopFetchInformationRecord;
import f1_Info.configuration.Configuration;
import f1_Info.configuration.ConfigurationRules;
import f1_Info.constants.Country;
import f1_Info.ergast.responses.*;
import f1_Info.ergast.responses.circuit.CircuitData;
import f1_Info.ergast.responses.circuit.LocationData;
import f1_Info.ergast.responses.pit_stop.PitStopData;
import f1_Info.ergast.responses.pit_stop.PitStopDataHolder;
import f1_Info.ergast.responses.race.RaceData;
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
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ErgastProxyTest {
    private static final ResponseHeader RESPONSE_HEADER = new ResponseHeader(1000, 0, 500);
    private static final ConfigurationRules MOCK_CONFIGURATION = new ConfigurationRules("", "", "", true);
    private static final ConfigurationRules LIVE_CONFIGURATION = new ConfigurationRules("", "", "", false);
    private static final String WIKIPEDIA_URL = "http://coolUrl.com/very-wow/12";
    private static final PitStopFetchInformationRecord FETCH_INFORMATION_RECORD = new PitStopFetchInformationRecord(1998, 2);

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
    void should_only_make_one_call_to_fetcher_if_first_response_contains_a_total_lesser_than_limit() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenReturn("");
        when(mParser.parseSeasonsResponseToObjects(anyString())).thenReturn(
            new ErgastResponse<>(new ResponseHeader(5, 0, 7), emptyList())
        );

        mErgastProxy.fetchAllSeasons();

        verify(mFetcher).readDataAsJsonStringFromUri(anyString());
    }

    @Test
    void should_make_one_call_to_fetcher_for_every_limit_partition_needed_to_get_full_response() throws IOException {
        final int total = (int)Math.round(ErgastProxy.MAX_LIMIT * 3.5);

        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenReturn("");
        when(mParser.parseSeasonsResponseToObjects(anyString()))
            .thenReturn(new ErgastResponse<>(new ResponseHeader(ErgastProxy.MAX_LIMIT, 0, total), emptyList()))
            .thenReturn(new ErgastResponse<>(new ResponseHeader(ErgastProxy.MAX_LIMIT, ErgastProxy.MAX_LIMIT, total), emptyList()))
            .thenReturn(new ErgastResponse<>(new ResponseHeader(ErgastProxy.MAX_LIMIT, ErgastProxy.MAX_LIMIT * 2, total), emptyList()))
            .thenReturn(new ErgastResponse<>(new ResponseHeader(ErgastProxy.MAX_LIMIT, ErgastProxy.MAX_LIMIT * 3, total), emptyList()));

        mErgastProxy.fetchAllSeasons();

        verify(mFetcher, times(4)).readDataAsJsonStringFromUri(anyString());
    }

    @Test
    void should_make_one_call_to_fetcher_containing_growing_offset_for_every_limit_partition_needed_to_get_full_response() throws IOException {
        final int total = (int)Math.round(ErgastProxy.MAX_LIMIT * 3.5);

        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenReturn("");
        when(mParser.parseSeasonsResponseToObjects(anyString()))
            .thenReturn(new ErgastResponse<>(new ResponseHeader(ErgastProxy.MAX_LIMIT, 0, total), emptyList()))
            .thenReturn(new ErgastResponse<>(new ResponseHeader(ErgastProxy.MAX_LIMIT, ErgastProxy.MAX_LIMIT, total), emptyList()))
            .thenReturn(new ErgastResponse<>(new ResponseHeader(ErgastProxy.MAX_LIMIT, ErgastProxy.MAX_LIMIT * 2, total), emptyList()))
            .thenReturn(new ErgastResponse<>(new ResponseHeader(ErgastProxy.MAX_LIMIT, ErgastProxy.MAX_LIMIT * 3, total), emptyList()));

        mErgastProxy.fetchAllSeasons();

        verify(mFetcher).readDataAsJsonStringFromUri(ErgastProxy.FETCH_ALL_SEASONS_URI + String.format(ErgastProxy.QUERY_PARAMETERS, ErgastProxy.MAX_LIMIT, 0));
        verify(mFetcher).readDataAsJsonStringFromUri(ErgastProxy.FETCH_ALL_SEASONS_URI + String.format(ErgastProxy.QUERY_PARAMETERS, ErgastProxy.MAX_LIMIT, ErgastProxy.MAX_LIMIT));
        verify(mFetcher).readDataAsJsonStringFromUri(ErgastProxy.FETCH_ALL_SEASONS_URI + String.format(ErgastProxy.QUERY_PARAMETERS, ErgastProxy.MAX_LIMIT, ErgastProxy.MAX_LIMIT * 2));
        verify(mFetcher).readDataAsJsonStringFromUri(ErgastProxy.FETCH_ALL_SEASONS_URI + String.format(ErgastProxy.QUERY_PARAMETERS, ErgastProxy.MAX_LIMIT, ErgastProxy.MAX_LIMIT * 3));
    }

    @Test
    void should_combine_results_given_from_parser_into_one_list_when_calling_for_multiple_partitions() throws IOException {
        final int total = (int)Math.round(ErgastProxy.MAX_LIMIT * 3.5);

        final SeasonData seasonData1 = new SeasonData(1998, WIKIPEDIA_URL);
        final SeasonData seasonData2 = new SeasonData(1999, WIKIPEDIA_URL);
        final SeasonData seasonData3 = new SeasonData(2000, WIKIPEDIA_URL);
        final SeasonData seasonData4 = new SeasonData(2001, WIKIPEDIA_URL);

        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenReturn("");
        when(mParser.parseSeasonsResponseToObjects(anyString()))
            .thenReturn(new ErgastResponse<>(new ResponseHeader(ErgastProxy.MAX_LIMIT, 0, total), singletonList(seasonData1)))
            .thenReturn(new ErgastResponse<>(new ResponseHeader(ErgastProxy.MAX_LIMIT, ErgastProxy.MAX_LIMIT , total), singletonList(seasonData2)))
            .thenReturn(new ErgastResponse<>(new ResponseHeader(ErgastProxy.MAX_LIMIT, ErgastProxy.MAX_LIMIT * 2, total), singletonList(seasonData3)))
            .thenReturn(new ErgastResponse<>(new ResponseHeader(ErgastProxy.MAX_LIMIT, ErgastProxy.MAX_LIMIT * 3, total), singletonList(seasonData4)));

        final List<SeasonData> list = mErgastProxy.fetchAllSeasons();

        assertEquals(List.of(seasonData1, seasonData2, seasonData3, seasonData4), list);
    }

    @Test
    void should_not_fetch_constructor_data_from_ergast_if_running_mock_configuration() throws IOException {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);

        mErgastProxy.fetchAllConstructors();

        verify(mFetcher, never()).readDataAsJsonStringFromUri(anyString());
    }

    @Test
    void should_return_empty_list_of_constructors_if_running_mock_configuration() {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);
        assertEquals(emptyList(), mErgastProxy.fetchAllConstructors());
    }

    @Test
    void should_return_empty_list_if_ioexception_gets_thrown_while_fetching_constructors() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenThrow(new IOException());

        assertEquals(emptyList(), mErgastProxy.fetchAllConstructors());
    }

    @Test
    void should_log_severe_if_ioexception_gets_thrown_while_fetching_constructors() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenThrow(new IOException());

        mErgastProxy.fetchAllConstructors();

        verify(mLogger).severe(anyString(), eq(ErgastProxy.class), anyString(), any(IOException.class));
    }

    @Test
    void should_return_formatted_data_from_parser_when_fetching_constructors() throws IOException {
        final List<ConstructorData> expectedReturnData = singletonList(
            new ConstructorData("", WIKIPEDIA_URL, "", Country.GERMANY.getNationalityKeywords().get(0))
        );

        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mParser.parseConstructorsResponseToObjects(any())).thenReturn(new ErgastResponse<>(RESPONSE_HEADER, expectedReturnData));

        assertEquals(expectedReturnData, mErgastProxy.fetchAllConstructors());
    }

    @Test
    void should_not_fetch_driver_data_from_ergast_if_running_mock_configuration() throws IOException {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);

        mErgastProxy.fetchAllDrivers();

        verify(mFetcher, never()).readDataAsJsonStringFromUri(anyString());
    }

    @Test
    void should_return_empty_list_of_drivers_if_running_mock_configuration() {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);
        assertEquals(emptyList(), mErgastProxy.fetchAllDrivers());
    }

    @Test
    void should_return_empty_list_if_ioexception_gets_thrown_while_fetching_drivers() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenThrow(new IOException());

        assertEquals(emptyList(), mErgastProxy.fetchAllDrivers());
    }

    @Test
    void should_log_severe_if_ioexception_gets_thrown_while_fetching_drivers() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenThrow(new IOException());

        mErgastProxy.fetchAllDrivers();

        verify(mLogger).severe(anyString(), eq(ErgastProxy.class), anyString(), any(IOException.class));
    }

    @Test
    void should_return_formatted_data_from_parser_when_fetching_drivers() throws IOException, ParseException {
        final List<DriverData> expectedReturnData = List.of(new DriverData(
            "",
            WIKIPEDIA_URL,
            "",
            "",
            "1999-01-01",
            Country.GERMANY.getNationalityKeywords().get(0),
            0,
            ""
        ));

        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mParser.parseDriversResponseToObjects(any())).thenReturn(new ErgastResponse<>(RESPONSE_HEADER, expectedReturnData));

        assertEquals(expectedReturnData, mErgastProxy.fetchAllDrivers());
    }

    @Test
    void should_not_fetch_season_data_from_ergast_if_running_mock_configuration() throws IOException {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);

        mErgastProxy.fetchAllSeasons();

        verify(mFetcher, never()).readDataAsJsonStringFromUri(anyString());
    }

    @Test
    void should_return_empty_list_of_seasons_if_running_mock_configuration() {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);
        assertEquals(emptyList(), mErgastProxy.fetchAllSeasons());
    }

    @Test
    void should_return_empty_list_if_ioexception_gets_thrown_while_fetching_seasons() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenThrow(new IOException());

        assertEquals(emptyList(), mErgastProxy.fetchAllSeasons());
    }

    @Test
    void should_log_severe_if_ioexception_gets_thrown_while_fetching_seasons() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenThrow(new IOException());

        mErgastProxy.fetchAllSeasons();

        verify(mLogger).severe(anyString(), eq(ErgastProxy.class), anyString(), any(IOException.class));
    }

    @Test
    void should_return_formatted_data_from_parser_when_fetching_seasons() throws IOException {
        final List<SeasonData> expectedReturnData = List.of(new SeasonData(1950, WIKIPEDIA_URL));

        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mParser.parseSeasonsResponseToObjects(any())).thenReturn(new ErgastResponse<>(RESPONSE_HEADER, expectedReturnData));

        assertEquals(expectedReturnData, mErgastProxy.fetchAllSeasons());
    }

    @Test
    void should_not_fetch_circuit_data_from_ergast_if_running_mock_configuration() throws IOException {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);

        mErgastProxy.fetchAllCircuits();

        verify(mFetcher, never()).readDataAsJsonStringFromUri(anyString());
    }

    @Test
    void should_return_empty_list_of_circuits_if_running_mock_configuration() {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);
        assertEquals(emptyList(), mErgastProxy.fetchAllCircuits());
    }

    @Test
    void should_return_empty_list_if_ioexception_gets_thrown_while_fetching_circuits() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenThrow(new IOException());

        assertEquals(emptyList(), mErgastProxy.fetchAllCircuits());
    }

    @Test
    void should_log_severe_if_ioexception_gets_thrown_while_fetching_circuits() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenThrow(new IOException());

        mErgastProxy.fetchAllCircuits();

        verify(mLogger).severe(anyString(), eq(ErgastProxy.class), anyString(), any(IOException.class));
    }

    @Test
    void should_return_formatted_data_from_parser_when_fetching_circuits() throws IOException {
        final List<CircuitData> expectedReturnData = List.of(
            new CircuitData("", WIKIPEDIA_URL, "", new LocationData(BigDecimal.ZERO, BigDecimal.ZERO, "", Country.GERMANY.getNames().get(0)))
        );

        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mParser.parseCircuitsResponseToObjects(any())).thenReturn(new ErgastResponse<>(RESPONSE_HEADER, expectedReturnData));

        assertEquals(expectedReturnData, mErgastProxy.fetchAllCircuits());
    }

    @Test
    void should_not_fetch_race_data_from_ergast_if_running_mock_configuration() throws IOException {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);

        mErgastProxy.fetchRacesFromYear(1998);

        verify(mFetcher, never()).readDataAsJsonStringFromUri(anyString());
    }

    @Test
    void should_return_empty_list_of_races_if_running_mock_configuration() {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);
        assertEquals(emptyList(), mErgastProxy.fetchRacesFromYear(1998));
    }

    @Test
    void should_return_empty_list_if_ioexception_gets_thrown_while_fetching_races() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenThrow(new IOException());

        assertEquals(emptyList(), mErgastProxy.fetchRacesFromYear(1998));
    }

    @Test
    void should_log_severe_if_ioexception_gets_thrown_while_fetching_races() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenThrow(new IOException());

        mErgastProxy.fetchRacesFromYear(1998);

        verify(mLogger).severe(anyString(), eq(ErgastProxy.class), anyString(), any(IOException.class));
    }

    @Test
    void should_return_formatted_data_from_parser_when_fetching_races() throws IOException, ParseException {
        final List<RaceData> expectedReturnData = List.of(
            new RaceData(1998, 1, WIKIPEDIA_URL, "race", null, "1998-01-01", null, null, null, null, null, new CircuitData(
                "circuit",
                WIKIPEDIA_URL,
                "circuit",
                new LocationData(BigDecimal.ZERO, BigDecimal.ZERO, "location", Country.GERMANY.getNames().get(0))
            ))
        );

        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mParser.parseRacesResponseToObjects(any())).thenReturn(new ErgastResponse<>(RESPONSE_HEADER, expectedReturnData));

        assertEquals(expectedReturnData, mErgastProxy.fetchRacesFromYear(1998));
    }

    @Test
    void should_not_fetch_finish_status_data_from_ergast_if_running_mock_configuration() throws IOException {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);

        mErgastProxy.fetchAllFinishStatuses();

        verify(mFetcher, never()).readDataAsJsonStringFromUri(anyString());
    }

    @Test
    void should_return_empty_list_of_finish_status_if_running_mock_configuration() {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);
        assertEquals(emptyList(), mErgastProxy.fetchAllFinishStatuses());
    }

    @Test
    void should_return_empty_list_if_ioexception_gets_thrown_while_fetching_finish_status() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenThrow(new IOException());

        assertEquals(emptyList(), mErgastProxy.fetchAllFinishStatuses());
    }

    @Test
    void should_log_severe_if_ioexception_gets_thrown_while_fetching_finish_status() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenThrow(new IOException());

        mErgastProxy.fetchAllFinishStatuses();

        verify(mLogger).severe(anyString(), eq(ErgastProxy.class), anyString(), any(IOException.class));
    }

    @Test
    void should_return_formatted_data_from_parser_when_fetching_finish_status() throws IOException {
        final List<FinishStatusData> expectedReturnData = List.of(new FinishStatusData(1, "status"));

        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mParser.parseFinishStatusResponseToObjects(any())).thenReturn(new ErgastResponse<>(RESPONSE_HEADER, expectedReturnData));

        assertEquals(expectedReturnData, mErgastProxy.fetchAllFinishStatuses());
    }

    @Test
    void should_not_fetch_pitstop_data_from_ergast_if_running_mock_configuration() throws IOException {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);

        mErgastProxy.fetchPitStopsFromRoundAndSeason(FETCH_INFORMATION_RECORD);

        verify(mFetcher, never()).readDataAsJsonStringFromUri(anyString());
    }

    @Test
    void should_return_empty_list_of_pitstop_data_if_running_mock_configuration() {
        when(mConfiguration.getRules()).thenReturn(MOCK_CONFIGURATION);
        assertEquals(emptyList(), mErgastProxy.fetchPitStopsFromRoundAndSeason(FETCH_INFORMATION_RECORD));
    }

    @Test
    void should_return_empty_list_if_ioexception_gets_thrown_while_fetching_pitstops() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenThrow(new IOException());

        assertEquals(emptyList(), mErgastProxy.fetchPitStopsFromRoundAndSeason(FETCH_INFORMATION_RECORD));
    }

    @Test
    void should_log_severe_if_ioexception_gets_thrown_while_fetching_pitstops() throws IOException {
        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mFetcher.readDataAsJsonStringFromUri(anyString())).thenThrow(new IOException());

        mErgastProxy.fetchPitStopsFromRoundAndSeason(FETCH_INFORMATION_RECORD);

        verify(mLogger).severe(anyString(), eq(ErgastProxy.class), anyString(), any(IOException.class));
    }

    @Test
    void should_return_formatted_data_from_parser_when_fetching_pitstops() throws IOException, ParseException {
        final List<PitStopData> expectedReturnData = singletonList(new PitStopData("dId", 43, 2, "12:21:35Z", BigDecimal.TEN));

        when(mConfiguration.getRules()).thenReturn(LIVE_CONFIGURATION);
        when(mParser.parsePitStopResponseToObjects(any())).thenReturn(new ErgastResponse<>(
            RESPONSE_HEADER,
            singletonList(new PitStopDataHolder(expectedReturnData)))
        );

        assertEquals(expectedReturnData, mErgastProxy.fetchPitStopsFromRoundAndSeason(FETCH_INFORMATION_RECORD));
    }
}
