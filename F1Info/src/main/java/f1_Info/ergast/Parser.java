package f1_Info.ergast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.ergast.responses.ResponseHeader;
import f1_Info.ergast.responses.TopResponseData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Component
public class Parser {
    private static final String MAIN_DATA_LABEL = "MRData";
    private static final List<String> CONSTRUCTORS_PARENTS = List.of("ConstructorTable", "Constructors");
    private final ObjectMapper mObjectMapper;

    public Parser() {
        this.mObjectMapper = new ObjectMapper();
    }

    public List<ConstructorData> parseConstructorsResponseToObjects(final String json) throws IOException {
        final TopResponseData data = parseToErgastResponse(json, mainNode -> dataExtractor(mainNode, CONSTRUCTORS_PARENTS));
        return Arrays.asList(mObjectMapper.readValue(data.getDataString(), ConstructorData[].class));
    }

    private String dataExtractor(final JsonNode mainNode, final List<String> parents) {
        try {
            JsonNode currentParent = mainNode;
            for (final String parent : parents) {
                currentParent = currentParent.get(parent);
            }

            return mObjectMapper.writeValueAsString(currentParent);
        } catch (final JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    private TopResponseData parseToErgastResponse(final String json, final Function<JsonNode, String> dataExtractor) throws IOException {
        final JsonNode node = mObjectMapper.readTree(json);
        final JsonNode mainDataNode = node.get(MAIN_DATA_LABEL);
        final ResponseHeader header = mObjectMapper.readValue(mObjectMapper.writeValueAsString(mainDataNode), ResponseHeader.class);
        return new TopResponseData(header, dataExtractor.apply(mainDataNode));
    }
}
