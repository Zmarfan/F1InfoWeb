package f1_Info.ergast;

import f1_Info.constants.Country;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.ergast.responses.DriverData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    private static final String BAD_JSON_FORMAT = "{field: \"bara skit\"}";
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

    private static final String TEST_DRIVER_JSON = "{\n" +
        "    \"MRData\": {\n" +
        "        \"limit\": \"2\",\n" +
        "        \"offset\": \"0\",\n" +
        "        \"total\": \"854\",\n" +
        "        \"DriverTable\": {\n" +
        "            \"Drivers\": [\n" +
        "                {\n" +
        "                    \"driverId\": \"abate\",\n" +
        "                    \"url\": \"http://en.wikipedia.org/wiki/Carlo_Mario_Abate\",\n" +
        "                    \"givenName\": \"Carlo\",\n" +
        "                    \"familyName\": \"Abate\",\n" +
        "                    \"dateOfBirth\": \"1932-07-10\",\n" +
        "                    \"nationality\": \"Italian\"\n" +
        "                },\n" +
        "                {\n" +
        "                    \"driverId\": \"abecassis\",\n" +
        "                    \"url\": \"http://en.wikipedia.org/wiki/George_Abecassis\",\n" +
        "                    \"givenName\": \"George\",\n" +
        "                    \"familyName\": \"Abecassis\",\n" +
        "                    \"dateOfBirth\": \"1913-03-21\",\n" +
        "                    \"nationality\": \"British\"\n" +
        "                }\n" +
        "            ]\n" +
        "        }\n" +
        "    }\n" +
        "}";
    // endregion

    @Test
    void should_parse_valid_constructors_json_to_correct_constructor_object_list() throws IOException {
        final List<ConstructorData> expectedData = List.of(
            new ConstructorData(
                "adams",
                "http://en.wikipedia.org/wiki/Adams_(constructor)",
                "Adams",
                Country.UNITED_STATES.getNationalityKeywords().get(0)
            ),
            new ConstructorData(
                "afm",
                "http://en.wikipedia.org/wiki/Alex_von_Falkenhausen_Motorenbau",
                "AFM",
                Country.GERMANY.getNationalityKeywords().get(0)
            )
        );
        final List<ConstructorData> parsedData = new Parser().parseConstructorsResponseToObjects(TEST_CONSTRUCTOR_JSON);

        assertEquals(expectedData, parsedData);
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_constructors() {
        assertThrows(IOException.class, () -> new Parser().parseConstructorsResponseToObjects(BAD_JSON_FORMAT));
    }

    @Test
    void should_parse_valid_drivers_json_to_correct_driver_object_list() throws IOException, ParseException {
        final List<DriverData> expectedData = List.of(
            new DriverData(
                "abate",
                "http://en.wikipedia.org/wiki/Carlo_Mario_Abate",
                "Carlo",
                "Abate",
                "1932-07-10",
                Country.ITALY.getNationalityKeywords().get(0),
                null,
                null
            ),
            new DriverData(
                "abecassis",
                "http://en.wikipedia.org/wiki/George_Abecassis",
                "George",
                "Abecassis",
                "1913-03-21",
                Country.UNITED_KINGDOM.getNationalityKeywords().get(0),
                null,
                null
            )
        );
        final List<DriverData> parsedData = new Parser().parseDriversResponseToObjects(TEST_DRIVER_JSON);

        assertEquals(expectedData, parsedData);
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_drivers() {
        assertThrows(IOException.class, () -> new Parser().parseDriversResponseToObjects(BAD_JSON_FORMAT));
    }
}
