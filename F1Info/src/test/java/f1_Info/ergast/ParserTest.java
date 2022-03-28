package f1_Info.ergast;

import f1_Info.constants.Country;
import f1_Info.ergast.responses.*;
import f1_Info.ergast.responses.circuit.CircuitData;
import f1_Info.ergast.responses.circuit.LocationData;
import f1_Info.ergast.responses.race.ErgastSessionTimes;
import f1_Info.ergast.responses.race.RaceData;
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

    private static final String TEST_RACES_JSON = "{\n" +
        "    \"MRData\": {\n" +
        "        \"limit\": \"2\",\n" +
        "        \"offset\": \"0\",\n" +
        "        \"total\": \"22\",\n" +
        "        \"RaceTable\": {\n" +
        "            \"season\": \"2021\",\n" +
        "            \"Races\": [\n" +
        "                {\n" +
        "                    \"season\": \"2021\",\n" +
        "                    \"round\": \"1\",\n" +
        "                    \"url\": \"http://en.wikipedia.org/wiki/2021_Bahrain_Grand_Prix\",\n" +
        "                    \"raceName\": \"Bahrain Grand Prix\",\n" +
        "                    \"Circuit\": {\n" +
        "                        \"circuitId\": \"bahrain\",\n" +
        "                        \"url\": \"http://en.wikipedia.org/wiki/Bahrain_International_Circuit\",\n" +
        "                        \"circuitName\": \"Bahrain International Circuit\",\n" +
        "                        \"Location\": {\n" +
        "                            \"lat\": \"26.0325\",\n" +
        "                            \"long\": \"50.5106\",\n" +
        "                            \"locality\": \"Sakhir\",\n" +
        "                            \"country\": \"Bahrain\"\n" +
        "                        }\n" +
        "                    },\n" +
        "                    \"date\": \"2021-03-28\",\n" +
        "                    \"time\": \"15:00:00Z\",\n" +
        "                    \"FirstPractice\": {\n" +
        "                        \"date\": \"2021-03-26\",\n" +
        "                        \"time\": \"12:00:00Z\"\n" +
        "                    },\n" +
        "                    \"SecondPractice\": {\n" +
        "                        \"date\": \"2021-03-26\",\n" +
        "                        \"time\": \"15:00:00Z\"\n" +
        "                    },\n" +
        "                    \"ThirdPractice\": {\n" +
        "                        \"date\": \"2021-03-27\",\n" +
        "                        \"time\": \"10:00:00Z\"\n" +
        "                    },\n" +
        "                    \"Qualifying\": {\n" +
        "                        \"date\": \"2021-03-27\",\n" +
        "                        \"time\": \"17:00:00Z\"\n" +
        "                    }\n" +
        "                },\n" +
        "                {\n" +
        "                    \"season\": \"2021\",\n" +
        "                    \"round\": \"2\",\n" +
        "                    \"url\": \"http://en.wikipedia.org/wiki/2021_Emilia_Romagna_Grand_Prix\",\n" +
        "                    \"raceName\": \"Emilia Romagna Grand Prix\",\n" +
        "                    \"Circuit\": {\n" +
        "                        \"circuitId\": \"imola\",\n" +
        "                        \"url\": \"http://en.wikipedia.org/wiki/Autodromo_Enzo_e_Dino_Ferrari\",\n" +
        "                        \"circuitName\": \"Autodromo Enzo e Dino Ferrari\",\n" +
        "                        \"Location\": {\n" +
        "                            \"lat\": \"44.3439\",\n" +
        "                            \"long\": \"11.7167\",\n" +
        "                            \"locality\": \"Imola\",\n" +
        "                            \"country\": \"Italy\"\n" +
        "                        }\n" +
        "                    },\n" +
        "                    \"date\": \"2021-04-18\",\n" +
        "                    \"time\": \"13:00:00Z\",\n" +
        "                    \"FirstPractice\": {\n" +
        "                        \"date\": \"2021-04-16\",\n" +
        "                        \"time\": \"07:00:00Z\"\n" +
        "                    },\n" +
        "                    \"SecondPractice\": {\n" +
        "                        \"date\": \"2021-04-16\",\n" +
        "                        \"time\": \"11:00:00Z\"\n" +
        "                    },\n" +
        "                    \"ThirdPractice\": {\n" +
        "                        \"date\": \"2021-04-17\",\n" +
        "                        \"time\": \"15:00:00Z\"\n" +
        "                    },\n" +
        "                    \"Qualifying\": {\n" +
        "                        \"date\": \"2021-04-17\",\n" +
        "                        \"time\": \"17:00:00Z\"\n" +
        "                    }\n" +
        "                }\n" +
        "            ]\n" +
        "        }\n" +
        "    }\n" +
        "}";

    private static final String TEST_FINISH_STATUS_JSON = "{\n" +
        "  \"MRData\": {\n" +
        "    \"limit\": \"150\",\n" +
        "    \"offset\": \"0\",\n" +
        "    \"total\": \"135\",\n" +
        "    \"StatusTable\": {\n" +
        "      \"Status\": [\n" +
        "        {\n" +
        "          \"statusId\": \"1\",\n" +
        "          \"count\": \"6833\",\n" +
        "          \"status\": \"Finished\"\n" +
        "        },\n" +
        "        {\n" +
        "          \"statusId\": \"139\",\n" +
        "          \"count\": \"2\",\n" +
        "          \"status\": \"Illness\"\n" +
        "        }\n" +
        "      ]\n" +
        "    }\n" +
        "  }\n" +
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

    @Test
    void should_parse_valid_races_json_to_correct_race_object_list() throws IOException, ParseException {
        final List<RaceData> expectedData = List.of(
            new RaceData(
                2021,
                1,
                "http://en.wikipedia.org/wiki/2021_Bahrain_Grand_Prix",
                "Bahrain Grand Prix",
                "15:00:00Z",
                "2021-03-28",
                new ErgastSessionTimes("2021-03-27", "17:00:00Z"),
                null,
                new ErgastSessionTimes("2021-03-26", "12:00:00Z"),
                new ErgastSessionTimes("2021-03-26", "15:00:00Z"),
                new ErgastSessionTimes("2021-03-27", "10:00:00Z"),
                new CircuitData(
                    "bahrain",
                    "http://en.wikipedia.org/wiki/Bahrain_International_Circuit",
                    "Bahrain International Circuit",
                    new LocationData(BigDecimal.valueOf(26.0325), BigDecimal.valueOf(50.5106), "Sakhir", Country.BAHRAIN.getNames().get(0))
                )
            ),
            new RaceData(
                2021,
                2,
                "http://en.wikipedia.org/wiki/2021_Emilia_Romagna_Grand_Prix",
                "Emilia Romagna Grand Prix",
                "13:00:00Z",
                "2021-04-18",
                new ErgastSessionTimes("2021-04-17", "17:00:00Z"),
                null,
                new ErgastSessionTimes("2021-04-16", "07:00:00Z"),
                new ErgastSessionTimes("2021-04-16", "11:00:00Z"),
                new ErgastSessionTimes("2021-04-17", "15:00:00Z"),
                new CircuitData(
                    "imola",
                    "http://en.wikipedia.org/wiki/Autodromo_Enzo_e_Dino_Ferrari",
                    "Autodromo Enzo e Dino Ferrari",
                    new LocationData(BigDecimal.valueOf(44.3439), BigDecimal.valueOf(11.7167), "Imola", Country.ITALY.getNames().get(0))
                )
            )
        );
        final List<RaceData> parsedData = new Parser().parseRacesResponseToObjects(TEST_RACES_JSON);

        assertEquals(expectedData, parsedData);
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_races() {
        assertThrows(IOException.class, () -> new Parser().parseRacesResponseToObjects(BAD_JSON_FORMAT));
    }

    @Test
    void should_parse_valid_finish_status_json_to_correct_race_object_list() throws IOException {
        final List<FinishStatusData> expectedData = List.of(new FinishStatusData(1, "Finished"), new FinishStatusData(139, "Illness"));
        final List<FinishStatusData> parsedData = new Parser().parseFinishStatusResponseToObjects(TEST_FINISH_STATUS_JSON);
        assertEquals(expectedData, parsedData);
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_finish_status() {
        assertThrows(IOException.class, () -> new Parser().parseFinishStatusResponseToObjects(BAD_JSON_FORMAT));
    }
}
