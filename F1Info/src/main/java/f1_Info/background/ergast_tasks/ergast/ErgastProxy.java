package f1_Info.background.ergast_tasks.ergast;

import f1_Info.background.ergast_tasks.ergast.responses.*;
import f1_Info.background.ergast_tasks.ergast.responses.circuit.CircuitData;
import f1_Info.background.ergast_tasks.ergast.responses.lap_times.LapTimesDataHolder;
import f1_Info.background.ergast_tasks.ergast.responses.pit_stop.PitStopDataHolder;
import f1_Info.background.ergast_tasks.ergast.responses.results.ResultDataHolder;
import f1_Info.background.ergast_tasks.ergast.responses.standings.ConstructorStandingsData;
import f1_Info.background.ergast_tasks.ergast.responses.standings.DriverStandingsData;
import f1_Info.background.ergast_tasks.ergast.responses.lap_times.LapTimeData;
import f1_Info.background.ergast_tasks.ergast.responses.pit_stop.PitStopData;
import f1_Info.background.ergast_tasks.ergast.responses.race.RaceData;
import f1_Info.background.ergast_tasks.RaceRecord;
import common.configuration.Configuration;
import common.logger.Logger;
import f1_Info.background.ergast_tasks.ergast.responses.standings.StandingsDataHolder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static common.wrappers.ThrowingFunction.wrapper;
import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class ErgastProxy {
    static final String REQUEST_FORMAT = "https://ergast.com/api/f1/%s.json?limit=%d&offset=%d";
    static final int MAX_LIMIT = 1000;

    static final String FETCH_CONSTRUCTORS_PART = "constructors";
    static final String FETCH_DRIVERS_PART = "drivers";
    static final String FETCH_SEASONS_PART = "seasons";
    static final String FETCH_CIRCUITS_PART = "circuits";
    static final String FETCH_RACES_PART = "%d";
    static final String FETCH_FINISH_STATUS_PART = "status";
    static final String FETCH_PIT_STOPS_PART = "%d/%d/pitstops";
    static final String FETCH_LAP_TIMES_PART = "%d/%d/laps";
    static final String FETCH_DRIVER_STANDINGS_PART = "%d/%d/driverStandings";
    static final String FETCH_CONSTRUCTOR_STANDINGS_PART = "%d/%d/constructorStandings";
    static final String FETCH_SPRINT_RESULTS_PART = "%d/sprint";
    static final String FETCH_RACE_RESULTS_PART = "%d/results";
    static final String FETCH_QUALIFYING_RESULTS_PART = "%d/qualifying";

    private final Parser mParser;
    private final Fetcher mFetcher;
    private final Configuration mConfiguration;
    private final Logger mLogger;

    public List<ConstructorData> fetchAllConstructors() {
        try {
            return isProduction() ? getData(FETCH_CONSTRUCTORS_PART, wrapper(mParser::parseConstructorsResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchAllConstructors", ErgastProxy.class, "Unable to fetch constructor data from ergast", e);
            return emptyList();
        }
    }

    public List<DriverData> fetchAllDrivers() {
        try {
            return isProduction() ? getData(FETCH_DRIVERS_PART, wrapper(mParser::parseDriversResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchAllDrivers", ErgastProxy.class, "Unable to fetch driver data from ergast", e);
            return emptyList();
        }
    }

    public List<SeasonData> fetchAllSeasons() {
        try {
            return isProduction() ? getData(FETCH_SEASONS_PART, wrapper(mParser::parseSeasonsResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchAllSeasons", ErgastProxy.class, "Unable to fetch season data from ergast", e);
            return emptyList();
        }
    }

    public List<CircuitData> fetchAllCircuits() {
        try {
            return isProduction() ? getData(FETCH_CIRCUITS_PART, wrapper(mParser::parseCircuitsResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchAllCircuits", ErgastProxy.class, "Unable to fetch circuit data from ergast", e);
            return emptyList();
        }
    }

    public List<RaceData> fetchRacesFromYear(final int fetchYear) {
        try {
            return isProduction() ? getData(String.format(FETCH_RACES_PART, fetchYear), wrapper(mParser::parseRacesResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchRacesFromYear", ErgastProxy.class, "Unable to fetch race data from ergast for the year: " + fetchYear, e);
            return emptyList();
        }
    }

    public List<FinishStatusData> fetchAllFinishStatuses() {
        try {
            return isProduction() ? getData(FETCH_FINISH_STATUS_PART, wrapper(mParser::parseFinishStatusResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchRacesFromYear", ErgastProxy.class, "Unable to fetch finish status data from ergast", e);
            return emptyList();
        }
    }

    public List<PitStopData> fetchPitStopsForRace(final RaceRecord raceRecord) throws NoDataAvailableYetException {
        try {
            if (isProduction()) {
                final List<PitStopDataHolder> pitStopHolder = getData(
                    String.format(FETCH_PIT_STOPS_PART, raceRecord.getSeason(), raceRecord.getRound()),
                    wrapper(mParser::parsePitStopResponseToObjects)
                );

                if (pitStopHolder.isEmpty()) {
                    throw new NoDataAvailableYetException();
                }
                return pitStopHolder.get(0).getPitStopData();
            }
        } catch (final NoDataAvailableYetException e) {
            throw e;
        } catch (final Exception e) {
            mLogger.severe(
                "fetchPitStopsFromRoundAndSeason",
                ErgastProxy.class,
                String.format("Unable to fetch pit stop data from ergast for season: %d, round: %d", raceRecord.getSeason(), raceRecord.getRound()),
                e
            );
        }
        return emptyList();
    }

    public List<LapTimeData> fetchLapTimesForRace(final RaceRecord raceRecord) throws NoDataAvailableYetException {
        try {
            if (isProduction()) {
                final List<LapTimesDataHolder> lapTimeHolder = getData(
                    String.format(FETCH_LAP_TIMES_PART, raceRecord.getSeason(), raceRecord.getRound()),
                    wrapper(mParser::parseLapTimesResponseToObjects)
                );
                if (lapTimeHolder.isEmpty()) {
                    throw new NoDataAvailableYetException();
                }
                return lapTimeHolder.get(0).getLapTimeData();
            }
        } catch (final NoDataAvailableYetException e) {
            throw e;
        } catch (final Exception e) {
            mLogger.severe(
                "fetchLapTimesFromRoundAndSeason",
                ErgastProxy.class,
                String.format("Unable to fetch lap times data from ergast for season: %d, round: %d", raceRecord.getSeason(), raceRecord.getRound()),
                e
            );
        }
        return emptyList();
    }

    public List<DriverStandingsData> fetchDriverStandingsForRace(final RaceRecord raceRecord) throws NoDataAvailableYetException {
        try {
            if (isProduction()) {
                final List<StandingsDataHolder> standingsHolder = getData(
                    String.format(FETCH_DRIVER_STANDINGS_PART, raceRecord.getSeason(), raceRecord.getRound()),
                    wrapper(mParser::parseStandingsResponseToObjects)
                );
                if (standingsHolder.isEmpty()) {
                    throw new NoDataAvailableYetException();
                }
                return standingsHolder.get(0).getDriverStandingsData();
            }
        } catch (final NoDataAvailableYetException e) {
            throw e;
        } catch (final Exception e) {
            mLogger.severe(
                "fetchDriverStandingsForRace",
                ErgastProxy.class,
                String.format("Unable to fetch driver standings data from ergast for season: %d, round: %d", raceRecord.getSeason(), raceRecord.getRound()),
                e
            );
        }
        return emptyList();
    }

    public List<ConstructorStandingsData> fetchConstructorStandingsForRace(final RaceRecord raceRecord) throws NoDataAvailableYetException {
        try {
            if (isProduction()) {
                final List<StandingsDataHolder> standingsHolder = getData(
                    String.format(FETCH_CONSTRUCTOR_STANDINGS_PART, raceRecord.getSeason(), raceRecord.getRound()),
                    wrapper(mParser::parseStandingsResponseToObjects)
                );
                if (standingsHolder.isEmpty()) {
                    throw new NoDataAvailableYetException();
                }
                return standingsHolder.get(0).getConstructorStandingsData();
            }
        } catch (final NoDataAvailableYetException e) {
            throw e;
        } catch (final Exception e) {
            mLogger.severe(
                "fetchConstructorStandingsForRace",
                ErgastProxy.class,
                String.format("Unable to fetch constructor standings data from ergast for season: %d, round: %d", raceRecord.getSeason(), raceRecord.getRound()),
                e
            );
        }
        return emptyList();
    }

    public List<ResultDataHolder> fetchSprintResultsForSeason(final int season) {
        try {
            return isProduction() ? getData(String.format(FETCH_SPRINT_RESULTS_PART, season), wrapper(mParser::parseResultsResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchSprintResultsForSeason", ErgastProxy.class, String.format(
                "Unable to fetch sprint result data from ergast for season: %d", season
            ), e);
            return emptyList();
        }
    }

    public List<ResultDataHolder> fetchRaceResultsForSeason(final int season) {
        try {
            return isProduction() ? getData(String.format(FETCH_RACE_RESULTS_PART, season), wrapper(mParser::parseResultsResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchRaceResultsForSeason", ErgastProxy.class, String.format(
                "Unable to fetch race result data from ergast for season: %d", season
            ), e);
            return emptyList();
        }
    }

    public List<ResultDataHolder> fetchQualifyingResultsForSeason(final int season) {
        try {
            return isProduction() ? getData(String.format(FETCH_QUALIFYING_RESULTS_PART, season), wrapper(mParser::parseResultsResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchQualifyingResultsForSeason", ErgastProxy.class, String.format(
                "Unable to fetch qualifying result data from ergast for season: %d", season
            ), e);
            return emptyList();
        }
    }

    private <T> List<T> getData(final String uri, final Function<String, ErgastResponse<T>> parserFunction) throws IOException {
        return readErgastData(uri, 0, parserFunction);
    }

    private <T> List<T> readErgastData(final String uri, final int offset, final Function<String, ErgastResponse<T>> parserFunction) throws IOException {
        try {
            final String url = String.format(REQUEST_FORMAT, uri, MAX_LIMIT, offset);
            final ErgastResponse<T> ergastResponse = parserFunction.apply(mFetcher.readDataAsJsonStringFromUri(url));
            final List<T> responseData = new ArrayList<>(ergastResponse.getData());
            if (canFetchMoreData(ergastResponse, offset)) {
                responseData.addAll(readErgastData(uri, offset + MAX_LIMIT, parserFunction));
            }
            return responseData;
        } catch (final IOException e) {
            throw new IOException(String.format("Unable to fetch or parse data for uri: %s with offset: %d with exception: %s", uri, offset, e.getMessage()));
        }
    }

    private <T> boolean canFetchMoreData(final ErgastResponse<T> responseData, final int offset) {
        return responseData.getResponseHeader().getTotal() > (MAX_LIMIT + offset);
    }

    private boolean isProduction() {
        return !mConfiguration.getRules().getIsMock();
    }
}
