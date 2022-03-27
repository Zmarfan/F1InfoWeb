package f1_Info.ergast;

import f1_Info.configuration.Configuration;
import f1_Info.ergast.responses.circuit.CircuitData;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.ergast.responses.DriverData;
import f1_Info.ergast.responses.SeasonData;
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
    private static final int CONSTRUCTOR_LIMIT = 250;
    private static final int DRIVER_LIMIT = 1000;
    private static final int SEASON_LIMIT = 200;
    private static final int CIRCUIT_LIMIT = 200;
    private static final int RACES_LIMIT = 50;

    private final Parser mParser;
    private final Fetcher mFetcher;
    private final Configuration mConfiguration;
    private final Logger mLogger;

    public List<ConstructorData> fetchAllConstructors() {
        try {
            if (!mConfiguration.getRules().isMock()) {
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
            if (!mConfiguration.getRules().isMock()) {
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
            if (!mConfiguration.getRules().isMock()) {
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
            if (!mConfiguration.getRules().isMock()) {
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
            if (!mConfiguration.getRules().isMock()) {
                final String responseJson = mFetcher.readDataAsJsonStringFromUri(String.format(FETCH_RACES_URI, fetchYear, RACES_LIMIT));
                return mParser.parseRacesResponseToObjects(responseJson);
            }
        } catch (final Exception e) {
            mLogger.severe("fetchRacesFromYear", ErgastProxy.class, "Unable to fetch race data from ergast for the year: " + fetchYear, e);
        }
        return emptyList();
    }
}
