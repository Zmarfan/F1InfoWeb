package f1_Info.ergast;

import f1_Info.background.fetch_pitstops_task.PitStopFetchInformationRecord;
import f1_Info.configuration.Configuration;
import f1_Info.ergast.responses.*;
import f1_Info.ergast.responses.circuit.CircuitData;
import f1_Info.ergast.responses.pit_stop.PitStopData;
import f1_Info.ergast.responses.race.RaceData;
import f1_Info.logger.Logger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static f1_Info.utils.ThrowingFunction.wrapper;
import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class ErgastProxy {
    private static final String QUERY_PARAMETERS = "?limit=%d&offset=%d";
    private static final int MAX_LIMIT = 1000;

    private static final String FETCH_ALL_CONSTRUCTORS_URI = "http://ergast.com/api/f1/constructors.json";
    private static final String FETCH_ALL_DRIVERS_URI = "http://ergast.com/api/f1/drivers.json";
    private static final String FETCH_ALL_SEASONS_URI = "https://ergast.com/api/f1/seasons.json";
    private static final String FETCH_ALL_CIRCUITS_URI = "http://ergast.com/api/f1/circuits.json";
    private static final String FETCH_RACES_URI = "https://ergast.com/api/f1/%d.json";
    private static final String FETCH_FINISH_STATUS_URI = "https://ergast.com/api/f1/status.json";
    private static final String FETCH_PIT_STOPS_URI = "https://ergast.com/api/f1/%d/%d/pitstops.json";

    private final Parser mParser;
    private final Fetcher mFetcher;
    private final Configuration mConfiguration;
    private final Logger mLogger;

    public List<ConstructorData> fetchAllConstructors() {
        try {
            return isProduction() ? getData(FETCH_ALL_CONSTRUCTORS_URI, wrapper(mParser::parseConstructorsResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchAllConstructors", ErgastProxy.class, "Unable to fetch constructor data from ergast", e);
            return emptyList();
        }
    }

    public List<DriverData> fetchAllDrivers() {
        try {
            return isProduction() ? getData(FETCH_ALL_DRIVERS_URI, wrapper(mParser::parseDriversResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchAllDrivers", ErgastProxy.class, "Unable to fetch driver data from ergast", e);
            return emptyList();
        }
    }

    public List<SeasonData> fetchAllSeasons() {
        try {
            return isProduction() ? getData(FETCH_ALL_SEASONS_URI, wrapper(mParser::parseSeasonsResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchAllSeasons", ErgastProxy.class, "Unable to fetch season data from ergast", e);
            return emptyList();
        }
    }

    public List<CircuitData> fetchAllCircuits() {
        try {
            return isProduction() ? getData(FETCH_ALL_CIRCUITS_URI, wrapper(mParser::parseCircuitsResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchAllCircuits", ErgastProxy.class, "Unable to fetch circuit data from ergast", e);
            return emptyList();
        }
    }

    public List<RaceData> fetchRacesFromYear(final int fetchYear) {
        try {
            return isProduction() ? getData(String.format(FETCH_RACES_URI, fetchYear), wrapper(mParser::parseRacesResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchRacesFromYear", ErgastProxy.class, "Unable to fetch race data from ergast for the year: " + fetchYear, e);
            return emptyList();
        }
    }

    public List<FinishStatusData> fetchAllFinishStatuses() {
        try {
            return isProduction() ? getData(FETCH_FINISH_STATUS_URI, wrapper(mParser::parseFinishStatusResponseToObjects)) : emptyList();
        } catch (final Exception e) {
            mLogger.severe("fetchRacesFromYear", ErgastProxy.class, "Unable to fetch finish status data from ergast", e);
            return emptyList();
        }
    }

    public List<PitStopData> fetchPitStopsFromRoundAndSeason(final PitStopFetchInformationRecord fetchInformation) {
        try {
            if (isProduction()) {
                return getData(
                    String.format(FETCH_PIT_STOPS_URI, fetchInformation.getSeason(), fetchInformation.getRound()),
                    wrapper(mParser::parsePitStopResponseToObjects)
                ).get(0).getPitStopData();
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

    private <T> List<T> getData(final String uri, final Function<String, ErgastResponse<T>> parserFunction) throws IOException {
        return readErgastData(uri, 0, parserFunction);
    }

    private <T> List<T> readErgastData(final String uri, final int offset, final Function<String, ErgastResponse<T>> parserFunction) throws IOException {
        try {
            final String url = uri + String.format(QUERY_PARAMETERS, MAX_LIMIT, offset);
            final ErgastResponse<T> ergastResponse = parserFunction.apply(mFetcher.readDataAsJsonStringFromUri(url));
            final List<T> responseData = new ArrayList<>(ergastResponse.getData());
            if (canFetchMoreData(ergastResponse, offset)) {
                responseData.addAll(readErgastData(uri, offset + MAX_LIMIT, parserFunction));
            }
            return responseData;
        } catch (final IOException e) {
            throw new IOException(String.format("Unable to fetch or parse data for uri: %s with offset: %d", uri, offset));
        }
    }

    private <T> boolean canFetchMoreData(final ErgastResponse<T> responseData, final int offset) {
        return responseData.getResponseHeader().getTotal() > (MAX_LIMIT + offset);
    }

    private boolean isProduction() {
        return !mConfiguration.getRules().getIsMock();
    }
}
