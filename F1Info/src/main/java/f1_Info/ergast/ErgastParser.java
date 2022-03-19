package f1_Info.ergast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import f1_Info.ergast.responses.ErgastConstructorData;
import f1_Info.ergast.responses.ErgastResponseData;
import f1_Info.ergast.responses.ErgastResponseHeader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
public class ErgastParser {
    private static final String MAIN_DATA_LABEL = "MRData";
    private final ObjectMapper mObjectMapper;

    public ErgastParser() {
        this.mObjectMapper = new ObjectMapper();
    }

    public List<ErgastConstructorData> parseConstructorsResponseToObjects(final String json) throws IOException {
        final ErgastResponseData data = parseToErgastResponse(json, "ConstructorTable");
        System.out.println(data.getData());
        return emptyList();
    }

    private ErgastResponseData parseToErgastResponse(final String json, final String dataGroupName) throws IOException {
        final JsonNode node = mObjectMapper.readTree(json);
        final JsonNode mainDataNode = node.get(MAIN_DATA_LABEL);
        final ErgastResponseHeader header = mObjectMapper.readValue(mObjectMapper.writeValueAsString(mainDataNode), ErgastResponseHeader.class);
        return new ErgastResponseData(header, mObjectMapper.writeValueAsString(mainDataNode.get(dataGroupName)));
    }
}
