package f1_Info.ergast;

import f1_Info.background.fetch_pitstops_task.PitStopFetchInformation;
import f1_Info.configuration.Configuration;
import f1_Info.ergast.responses.*;
import f1_Info.ergast.responses.circuit.CircuitData;
import f1_Info.ergast.responses.pit_stop.PitStopData;
import f1_Info.ergast.responses.pit_stop.PitStopDataHolder;
import f1_Info.ergast.responses.race.RaceData;
import f1_Info.logger.Logger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class ErgastProxy {
    private static final String FETCH_ALL_CONSTRUCTORS_URI = "http://ergast.com/api/f1/constructors.json?limit=%d";
    private static final String FETCH_ALL_DRIVERS_URI = "http://ergast.com/api/f1/drivers.json?limit=%d";
    private static final String FETCH_ALL_SEASONS_URI = "https://ergast.com/api/f1/seasons.json?limit=%d";
    private static final String FETCH_ALL_CIRCUITS_URI = "http://ergast.com/api/f1/circuits.json?limit=%d";
    private static final String FETCH_RACES_URI = "https://ergast.com/api/f1/%d.json?limit=%d";
    private static final String FETCH_FINISH_STATUS_URI = "https://ergast.com/api/f1/status.json?limit=%d";
    private static final String FETCH_PIT_STOPS_URI = "https://ergast.com/api/f1/%d/%d/pitstops.json?limit=%d";
    private static final int CONSTRUCTOR_LIMIT = 250;
    private static final int DRIVER_LIMIT = 1000;
    private static final int SEASON_LIMIT = 200;
    private static final int CIRCUIT_LIMIT = 200;
    private static final int RACES_LIMIT = 50;
    private static final int FINISH_STATUS_LIMIT = 200;
    private static final int PIT_STOPS_STATUS_LIMIT = 200;

    private final Parser mParser;
    private final Fetcher mFetcher;
    private final Configuration mConfiguration;
    private final Logger mLogger;

    public List<ConstructorData> fetchAllConstructors() {
        try {
            if (isProduction()) {
                final String responseJson = mFetcher.readDataAsJsonStringFromUri(String.format(FETCH_ALL_CONSTRUCTORS_URI, CONSTRUCTOR_LIMIT));
                return mParser.parseConstructorsResponseToObjects(responseJson);
            }
        } catch (final Exception e) {
            mLogger.severe("fetchAllConstructors", ErgastProxy.class, "Unable to fetch constructor data from ergast", e);
        }
        return emptyList();
    }

    public List<DriverData> fetchAllDrivers() {
        try {
            if (isProduction()) {
                final String responseJson = mFetcher.readDataAsJsonStringFromUri(String.format(FETCH_ALL_DRIVERS_URI, DRIVER_LIMIT));
                return mParser.parseDriversResponseToObjects(responseJson);
            }
        } catch (final Exception e) {
            mLogger.severe("fetchAllDrivers", ErgastProxy.class, "Unable to fetch driver data from ergast", e);
        }
        return emptyList();
    }

    public List<SeasonData> fetchAllSeasons() {
        try {
            if (isProduction()) {
                final String responseJson = mFetcher.readDataAsJsonStringFromUri(String.format(FETCH_ALL_SEASONS_URI, SEASON_LIMIT));
                return mParser.parseSeasonsResponseToObjects(responseJson);
            }
        } catch (final Exception e) {
            mLogger.severe("fetchAllSeasons", ErgastProxy.class, "Unable to fetch season data from ergast", e);
        }
        return emptyList();
    }

    public List<CircuitData> fetchAllCircuits() {
        try {
            if (isProduction()) {
                final String responseJson = mFetcher.readDataAsJsonStringFromUri(String.format(FETCH_ALL_CIRCUITS_URI, CIRCUIT_LIMIT));
                return mParser.parseCircuitsResponseToObjects(responseJson);
            }
        } catch (final Exception e) {
            mLogger.severe("fetchAllCircuits", ErgastProxy.class, "Unable to fetch circuit data from ergast", e);
        }
        return emptyList();
    }

    public List<RaceData> fetchRacesFromYear(final int fetchYear) {
        try {
            if (isProduction()) {
                final String responseJson = mFetcher.readDataAsJsonStringFromUri(String.format(FETCH_RACES_URI, fetchYear, RACES_LIMIT));
                return mParser.parseRacesResponseToObjects(responseJson);
            }
        } catch (final Exception e) {
            mLogger.severe("fetchRacesFromYear", ErgastProxy.class, "Unable to fetch race data from ergast for the year: " + fetchYear, e);
        }
        return emptyList();
    }

    public List<FinishStatusData> fetchAllFinishStatuses() {
        try {
            if (isProduction()) {
                final String responseJson = mFetcher.readDataAsJsonStringFromUri(String.format(FETCH_FINISH_STATUS_URI, FINISH_STATUS_LIMIT));
                return mParser.parseFinishStatusResponseToObjects(responseJson);
            }
        } catch (final Exception e) {
            mLogger.severe("fetchRacesFromYear", ErgastProxy.class, "Unable to fetch finish status data from ergast", e);
        }
        return emptyList();
    }

    public List<PitStopData> fetchPitStopsFromRoundAndSeason(final PitStopFetchInformation fetchInformation) {
        try {
            if (isProduction()) {
                final String responseJson = mFetcher.readDataAsJsonStringFromUri(
                    String.format(FETCH_PIT_STOPS_URI, fetchInformation.getSeason(), fetchInformation.getRound(), PIT_STOPS_STATUS_LIMIT)
                );
                final List<PitStopDataHolder> dataHolder = mParser.parsePitStopResponseToObjects(responseJson);
                return dataHolder.isEmpty() ? emptyList() : dataHolder.get(0).getPitStopData();
            }
        } catch (final Exception e) {
            mLogger.severe(
                "fetchPitStopsFromRoundAndSeason",
                ErgastProxy.class,
                String.format("Unable to fetch pit stop data from ergast for season: %d, round: %d", fetchInformation.getSeason(), fetchInformation.getRound()),
                e
            );
        }
        return emptyList();
    }

    private boolean isProduction() {
        return !mConfiguration.getRules().getIsMock();
    }
}
