package f1_Info.background.ergast_tasks.ergast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import f1_Info.background.ergast_tasks.ergast.responses.*;
import f1_Info.background.ergast_tasks.ergast.responses.circuit.CircuitData;
import f1_Info.background.ergast_tasks.ergast.responses.results.ResultDataHolder;
import f1_Info.background.ergast_tasks.ergast.responses.standings.StandingsDataHolder;
import f1_Info.background.ergast_tasks.ergast.responses.lap_times.LapTimesDataHolder;
import f1_Info.background.ergast_tasks.ergast.responses.pit_stop.PitStopDataHolder;
import f1_Info.background.ergast_tasks.ergast.responses.race.RaceData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static common.utils.ListUtils.stringListToString;

@Component
public class Parser {
    private static final String MAIN_DATA_LABEL = "MRData";
    private static final List<String> CONSTRUCTORS_PARENTS = List.of("ConstructorTable", "Constructors");
    private static final List<String> DRIVERS_PARENTS = List.of("DriverTable", "Drivers");
    private static final List<String> SEASON_PARENTS = List.of("SeasonTable", "Seasons");
    private static final List<String> CIRCUIT_PARENTS = List.of("CircuitTable", "Circuits");
    private static final List<String> RACE_DATA_PARENTS = List.of("RaceTable", "Races");
    private static final List<String> FINISH_STATUS_PARENTS = List.of("StatusTable", "Status");
    private static final List<String> STANDINGS_PARENTS = List.of("StandingsTable", "StandingsLists");
    private final ObjectMapper mObjectMapper;

    public Parser() {
        mObjectMapper = new ObjectMapper();
    }

    public ErgastResponse<ConstructorData> parseConstructorsResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, CONSTRUCTORS_PARENTS));
        return new ErgastResponse<>(data.getHeader(), Arrays.asList(mObjectMapper.readValue(data.getDataString(), ConstructorData[].class)));
    }

    public ErgastResponse<DriverData> parseDriversResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, DRIVERS_PARENTS));
        return new ErgastResponse<>(data.getHeader(), Arrays.asList(mObjectMapper.readValue(data.getDataString(), DriverData[].class)));
    }

    public ErgastResponse<SeasonData> parseSeasonsResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, SEASON_PARENTS));
        return new ErgastResponse<>(data.getHeader(), Arrays.asList(mObjectMapper.readValue(data.getDataString(), SeasonData[].class)));
    }

    public ErgastResponse<CircuitData> parseCircuitsResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, CIRCUIT_PARENTS));
        return new ErgastResponse<>(data.getHeader(), Arrays.asList(mObjectMapper.readValue(data.getDataString(), CircuitData[].class)));
    }

    public ErgastResponse<RaceData> parseRacesResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, RACE_DATA_PARENTS));
        return new ErgastResponse<>(data.getHeader(), Arrays.asList(mObjectMapper.readValue(data.getDataString(), RaceData[].class)));
    }

    public ErgastResponse<FinishStatusData> parseFinishStatusResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, FINISH_STATUS_PARENTS));
        return new ErgastResponse<>(data.getHeader(), Arrays.asList(mObjectMapper.readValue(data.getDataString(), FinishStatusData[].class)));
    }

    public ErgastResponse<PitStopDataHolder> parsePitStopResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, RACE_DATA_PARENTS));
        return new ErgastResponse<>(data.getHeader(), Arrays.asList(mObjectMapper.readValue(data.getDataString(), PitStopDataHolder[].class)));
    }

    public ErgastResponse<LapTimesDataHolder> parseLapTimesResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, RACE_DATA_PARENTS));
        return new ErgastResponse<>(data.getHeader(), Arrays.asList(mObjectMapper.readValue(data.getDataString(), LapTimesDataHolder[].class)));
    }

    public ErgastResponse<StandingsDataHolder> parseStandingsResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, STANDINGS_PARENTS));
        return new ErgastResponse<>(data.getHeader(), Arrays.asList(mObjectMapper.readValue(data.getDataString(), StandingsDataHolder[].class)));
    }

    public ErgastResponse<ResultDataHolder> parseResultsResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, RACE_DATA_PARENTS));
        return new ErgastResponse<>(data.getHeader(), Arrays.asList(mObjectMapper.readValue(data.getDataString(), ResultDataHolder[].class)));
    }

    private String dataExtractor(final JsonNode mainNode, final List<String> parents) {
        try {
            JsonNode currentParent = mainNode;
            for (final String parent : parents) {
                currentParent = currentParent.get(parent);
            }

            return mObjectMapper.writeValueAsString(currentParent);
        } catch (final JsonProcessingException e) {
            throw new UncheckedIOException(String.format("Unable to traverse parent json node with the parameters: %s", stringListToString(parents)), e);
        }
    }

    private TopResponseData parseToErgastResponse(final String json, final Function<JsonNode, String> dataExtractor) throws IOException {
        final JsonNode node = mObjectMapper.readTree(json);
        final JsonNode mainDataNode = node.get(MAIN_DATA_LABEL);
        final ResponseHeader header = mObjectMapper.readValue(mObjectMapper.writeValueAsString(mainDataNode), ResponseHeader.class);
        return new TopResponseData(header, dataExtractor.apply(mainDataNode));
    }
}
