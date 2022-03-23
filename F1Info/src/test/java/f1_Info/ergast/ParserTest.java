package f1_Info.ergast;

import f1_Info.constants.Country;
import f1_Info.ergast.responses.ConstructorData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    // region test jsons
    private static final String TEST_CONSTRUCTOR_JSON = "{\n" +
            "    \"MRData\": {\n" +
            "        \"limit\": \"2\",\n" +
            "        \"offset\": \"0\",\n" +
            "        \"total\": \"211\",\n" +
            "        \"ConstructorTable\": {\n" +
            "            \"Constructors\": [\n" +
            "                {\n" +
            "                    \"constructorId\": \"adams\",\n" +
            "                    \"url\": \"http://en.wikipedia.org/wiki/Adams_(constructor)\",\n" +
            "                    \"name\": \"Adams\",\n" +
            "                    \"nationality\": \"American\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"constructorId\": \"afm\",\n" +
            "                    \"url\": \"http://en.wikipedia.org/wiki/Alex_von_Falkenhausen_Motorenbau\",\n" +
            "                    \"name\": \"AFM\",\n" +
            "                    \"nationality\": \"German\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    }\n" +
            "}";
    // endregion

    @Test
    void should_parse_valid_constructors_json_to_correct_constructor_object_list() throws IOException {
        final List<ConstructorData> expectedData = List.of(
            new ConstructorData("adams", "http://en.wikipedia.org/wiki/Adams_(constructor)", "Adams", Country.UNITED_STATES.getNationalityKeywords().stream().toList().get(0)),
            new ConstructorData("afm", "http://en.wikipedia.org/wiki/Alex_von_Falkenhausen_Motorenbau", "AFM", Country.GERMANY.getNationalityKeywords().stream().toList().get(0))
        );
        final List<ConstructorData> parsedData = new Parser().parseConstructorsResponseToObjects(TEST_CONSTRUCTOR_JSON);

        assertEquals(expectedData, parsedData);
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_constructors() {
        assertThrows(IOException.class, () -> new Parser().parseConstructorsResponseToObjects("{field: \"bara skit\"}"));
    }
}
