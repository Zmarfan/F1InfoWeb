package f1_Info.ergast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import f1_Info.ergast.responses.*;
import f1_Info.ergast.responses.circuit.CircuitData;
import f1_Info.ergast.responses.race.RaceData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static f1_Info.utils.ListUtils.stringListToString;

@Component
public class Parser {
    private static final String MAIN_DATA_LABEL = "MRData";
    private static final List<String> CONSTRUCTORS_PARENTS = List.of("ConstructorTable", "Constructors");
    private static final List<String> DRIVERS_PARENTS = List.of("DriverTable", "Drivers");
    private static final List<String> SEASON_PARENTS = List.of("SeasonTable", "Seasons");
    private static final List<String> CIRCUIT_PARENTS = List.of("CircuitTable", "Circuits");
    private static final List<String> RACES_PARENTS = List.of("RaceTable", "Races");
    private final ObjectMapper mObjectMapper;

    public Parser() {
        this.mObjectMapper = new ObjectMapper();
    }

    public List<ConstructorData> parseConstructorsResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, CONSTRUCTORS_PARENTS));
        return Arrays.asList(mObjectMapper.readValue(data.getDataString(), ConstructorData[].class));
    }

    public List<DriverData> parseDriversResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, DRIVERS_PARENTS));
        return Arrays.asList(mObjectMapper.readValue(data.getDataString(), DriverData[].class));
    }

    public List<SeasonData> parseSeasonsResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, SEASON_PARENTS));
        return Arrays.asList(mObjectMapper.readValue(data.getDataString(), SeasonData[].class));
    }

    public List<CircuitData> parseCircuitsResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, CIRCUIT_PARENTS));
        return Arrays.asList(mObjectMapper.readValue(data.getDataString(), CircuitData[].class));
    }

    public List<RaceData> parseRacesResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, RACES_PARENTS));
        return Arrays.asList(mObjectMapper.readValue(data.getDataString(), RaceData[].class));
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
