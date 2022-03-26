package f1_Info.ergast;

import f1_Info.constants.Country;
import f1_Info.ergast.responses.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
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

    private static final String TEST_SEASONS_JSON = "{\n" +
        "  \"MRData\": {\n" +
        "    \"limit\": \"2\",\n" +
        "    \"offset\": \"0\",\n" +
        "    \"total\": \"73\",\n" +
        "    \"SeasonTable\": {\n" +
        "      \"Seasons\": [\n" +
        "        {\n" +
        "          \"season\": \"1950\",\n" +
        "          \"url\": \"http://en.wikipedia.org/wiki/1950_Formula_One_season\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"season\": \"1951\",\n" +
        "          \"url\": \"http://en.wikipedia.org/wiki/1951_Formula_One_season\"\n" +
        "        }\n" +
        "      ]\n" +
        "    }\n" +
        "  }\n" +
        "}";

    private static final String TEST_CIRCUITS_JSON = "{\n" +
        "    \"MRData\": {\n" +
        "        \"limit\": \"2\",\n" +
        "        \"offset\": \"0\",\n" +
        "        \"total\": \"79\",\n" +
        "        \"CircuitTable\": {\n" +
        "            \"Circuits\": [\n" +
        "                {\n" +
        "                    \"circuitId\": \"adelaide\",\n" +
        "                    \"url\": \"http://en.wikipedia.org/wiki/Adelaide_Street_Circuit\",\n" +
        "                    \"circuitName\": \"Adelaide Street Circuit\",\n" +
        "                    \"Location\": {\n" +
        "                        \"lat\": \"-34.9272\",\n" +
        "                        \"long\": \"138.617\",\n" +
        "                        \"locality\": \"Adelaide\",\n" +
        "                        \"country\": \"Australia\"\n" +
        "                    }\n" +
        "                },\n" +
        "                {\n" +
        "                    \"circuitId\": \"ain-diab\",\n" +
        "                    \"url\": \"http://en.wikipedia.org/wiki/Ain-Diab_Circuit\",\n" +
        "                    \"circuitName\": \"Ain Diab\",\n" +
        "                    \"Location\": {\n" +
        "                        \"lat\": \"33.5786\",\n" +
        "                        \"long\": \"-7.6875\",\n" +
        "                        \"locality\": \"Casablanca\",\n" +
        "                        \"country\": \"Morocco\"\n" +
        "                    }\n" +
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

    @Test
    void should_parse_valid_seasons_json_to_correct_season_object_list() throws IOException {
        final List<SeasonData> expectedData = List.of(
            new SeasonData(1950, "http://en.wikipedia.org/wiki/1950_Formula_One_season"),
            new SeasonData(1951, "http://en.wikipedia.org/wiki/1951_Formula_One_season")
        );
        final List<SeasonData> parsedData = new Parser().parseSeasonsResponseToObjects(TEST_SEASONS_JSON);

        assertEquals(expectedData, parsedData);
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_seasons() {
        assertThrows(IOException.class, () -> new Parser().parseSeasonsResponseToObjects(BAD_JSON_FORMAT));
    }

    @Test
    void should_parse_valid_circuits_json_to_correct_season_object_list() throws IOException {
        final List<CircuitData> expectedData = List.of(
            new CircuitData("adelaide", "http://en.wikipedia.org/wiki/Adelaide_Street_Circuit", "Adelaide Street Circuit", new LocationData(
                BigDecimal.valueOf(-34.9272),
                BigDecimal.valueOf(138.617),
                "Adelaide",
                Country.AUSTRALIA.getNames().get(0)
            )),
            new CircuitData("ain-diab", "http://en.wikipedia.org/wiki/Ain-Diab_Circuit", "Ain Diab", new LocationData(
                BigDecimal.valueOf(33.5786),
                BigDecimal.valueOf(-7.6875),
                "Casablanca",
                Country.MOROCCO.getNames().get(0)
            ))
        );
        final List<CircuitData> parsedData = new Parser().parseCircuitsResponseToObjects(TEST_CIRCUITS_JSON);

        assertEquals(expectedData, parsedData);
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_circuits() {
        assertThrows(IOException.class, () -> new Parser().parseCircuitsResponseToObjects(BAD_JSON_FORMAT));
    }
}
