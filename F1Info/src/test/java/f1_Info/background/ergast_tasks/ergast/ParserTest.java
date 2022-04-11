package f1_Info.background.ergast_tasks.ergast;

import f1_Info.background.ergast_tasks.ergast.responses.*;
import f1_Info.background.ergast_tasks.ergast.responses.circuit.CircuitData;
import f1_Info.background.ergast_tasks.ergast.responses.circuit.LocationData;
import f1_Info.background.ergast_tasks.ergast.responses.lap_times.LapTimeData;
import f1_Info.background.ergast_tasks.ergast.responses.lap_times.LapTimesDataHolder;
import f1_Info.background.ergast_tasks.ergast.responses.lap_times.TimingData;
import f1_Info.background.ergast_tasks.ergast.responses.pit_stop.PitStopData;
import f1_Info.background.ergast_tasks.ergast.responses.pit_stop.PitStopDataHolder;
import f1_Info.background.ergast_tasks.ergast.responses.race.ErgastSessionTimes;
import f1_Info.background.ergast_tasks.ergast.responses.race.RaceData;
import f1_Info.background.ergast_tasks.ergast.responses.standings.DriverStandingsData;
import f1_Info.background.ergast_tasks.ergast.responses.standings.StandingsDataHolder;
import f1_Info.constants.Country;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    private static final String BAD_JSON_FORMAT = "{field: \"bara skit\"}";
    // region test jsons
    private static final String TEST_CONSTRUCTOR_JSON = """
        {
            "MRData": {
                "limit": "2",
                "offset": "0",
                "total": "211",
                "ConstructorTable": {
                    "Constructors": [
                        {
                            "constructorId": "adams",
                            "url": "http://en.wikipedia.org/wiki/Adams_(constructor)",
                            "name": "Adams",
                            "nationality": "American"
                        },
                        {
                            "constructorId": "afm",
                            "url": "http://en.wikipedia.org/wiki/Alex_von_Falkenhausen_Motorenbau",
                            "name": "AFM",
                            "nationality": "German"
                        }
                    ]
                }
            }
        }""";

    private static final String TEST_DRIVER_JSON = """
        {
            "MRData": {
                "limit": "2",
                "offset": "0",
                "total": "854",
                "DriverTable": {
                    "Drivers": [
                        {
                            "driverId": "abate",
                            "url": "http://en.wikipedia.org/wiki/Carlo_Mario_Abate",
                            "givenName": "Carlo",
                            "familyName": "Abate",
                            "dateOfBirth": "1932-07-10",
                            "nationality": "Italian"
                        },
                        {
                            "driverId": "abecassis",
                            "url": "http://en.wikipedia.org/wiki/George_Abecassis",
                            "givenName": "George",
                            "familyName": "Abecassis",
                            "dateOfBirth": "1913-03-21",
                            "nationality": "British"
                        }
                    ]
                }
            }
        }""";

    private static final String TEST_SEASONS_JSON = """
        {
          "MRData": {
            "limit": "2",
            "offset": "0",
            "total": "73",
            "SeasonTable": {
              "Seasons": [
                {
                  "season": "1950",
                  "url": "http://en.wikipedia.org/wiki/1950_Formula_One_season"
                },
                {
                  "season": "1951",
                  "url": "http://en.wikipedia.org/wiki/1951_Formula_One_season"
                }
              ]
            }
          }
        }""";

    private static final String TEST_CIRCUITS_JSON = """
        {
            "MRData": {
                "limit": "2",
                "offset": "0",
                "total": "79",
                "CircuitTable": {
                    "Circuits": [
                        {
                            "circuitId": "adelaide",
                            "url": "http://en.wikipedia.org/wiki/Adelaide_Street_Circuit",
                            "circuitName": "Adelaide Street Circuit",
                            "Location": {
                                "lat": "-34.9272",
                                "long": "138.617",
                                "locality": "Adelaide",
                                "country": "Australia"
                            }
                        },
                        {
                            "circuitId": "ain-diab",
                            "url": "http://en.wikipedia.org/wiki/Ain-Diab_Circuit",
                            "circuitName": "Ain Diab",
                            "Location": {
                                "lat": "33.5786",
                                "long": "-7.6875",
                                "locality": "Casablanca",
                                "country": "Morocco"
                            }
                        }
                    ]
                }
            }
        }""";

    private static final String TEST_RACES_JSON = """
        {
            "MRData": {
                "limit": "2",
                "offset": "0",
                "total": "22",
                "RaceTable": {
                    "season": "2021",
                    "Races": [
                        {
                            "season": "2021",
                            "round": "1",
                            "url": "http://en.wikipedia.org/wiki/2021_Bahrain_Grand_Prix",
                            "raceName": "Bahrain Grand Prix",
                            "Circuit": {
                                "circuitId": "bahrain",
                                "url": "http://en.wikipedia.org/wiki/Bahrain_International_Circuit",
                                "circuitName": "Bahrain International Circuit",
                                "Location": {
                                    "lat": "26.0325",
                                    "long": "50.5106",
                                    "locality": "Sakhir",
                                    "country": "Bahrain"
                                }
                            },
                            "date": "2021-03-28",
                            "time": "15:00:00Z",
                            "FirstPractice": {
                                "date": "2021-03-26",
                                "time": "12:00:00Z"
                            },
                            "SecondPractice": {
                                "date": "2021-03-26",
                                "time": "15:00:00Z"
                            },
                            "ThirdPractice": {
                                "date": "2021-03-27",
                                "time": "10:00:00Z"
                            },
                            "Qualifying": {
                                "date": "2021-03-27",
                                "time": "17:00:00Z"
                            }
                        },
                        {
                            "season": "2021",
                            "round": "2",
                            "url": "http://en.wikipedia.org/wiki/2021_Emilia_Romagna_Grand_Prix",
                            "raceName": "Emilia Romagna Grand Prix",
                            "Circuit": {
                                "circuitId": "imola",
                                "url": "http://en.wikipedia.org/wiki/Autodromo_Enzo_e_Dino_Ferrari",
                                "circuitName": "Autodromo Enzo e Dino Ferrari",
                                "Location": {
                                    "lat": "44.3439",
                                    "long": "11.7167",
                                    "locality": "Imola",
                                    "country": "Italy"
                                }
                            },
                            "date": "2021-04-18",
                            "time": "13:00:00Z",
                            "FirstPractice": {
                                "date": "2021-04-16",
                                "time": "07:00:00Z"
                            },
                            "SecondPractice": {
                                "date": "2021-04-16",
                                "time": "11:00:00Z"
                            },
                            "ThirdPractice": {
                                "date": "2021-04-17",
                                "time": "15:00:00Z"
                            },
                            "Qualifying": {
                                "date": "2021-04-17",
                                "time": "17:00:00Z"
                            }
                        }
                    ]
                }
            }
        }""";

    private static final String TEST_FINISH_STATUS_JSON = """
        {
          "MRData": {
            "limit": "150",
            "offset": "0",
            "total": "135",
            "StatusTable": {
              "Status": [
                {
                  "statusId": "1",
                  "count": "6833",
                  "status": "Finished"
                },
                {
                  "statusId": "139",
                  "count": "2",
                  "status": "Illness"
                }
              ]
            }
          }
        }""";

    private static final String TEST_PITSTOP_JSON = """
            {
                "MRData": {
                    "limit": "2",
                    "offset": "0",
                    "total": "58",
                    "RaceTable": {
                        "season": "2022",
                        "round": "1",
                        "Races": [
                            {
                                "season": "2022",
                                "round": "1",
                                "url": "http://en.wikipedia.org/wiki/2022_Bahrain_Grand_Prix",
                                "raceName": "Bahrain Grand Prix",
                                "Circuit": {
                                    "circuitId": "bahrain",
                                    "url": "http://en.wikipedia.org/wiki/Bahrain_International_Circuit",
                                    "circuitName": "Bahrain International Circuit",
                                    "Location": {
                                        "lat": "26.0325",
                                        "long": "50.5106",
                                        "locality": "Sakhir",
                                        "country": "Bahrain"
                                    }
                                },
                                "date": "2022-03-20",
                                "time": "15:00:00Z",
                                "PitStops": [
                                    {
                                        "driverId": "hamilton",
                                        "lap": "11",
                                        "stop": "1",
                                        "time": "18:21:54",
                                        "duration": "25.201"
                                    },
                                    {
                                        "driverId": "alonso",
                                        "lap": "11",
                                        "stop": "1",
                                        "time": "18:22:10",
                                        "duration": "25.365"
                                    }
                                ]
                            }
                        ]
                    }
                }
            }
        """;

    private static final String TEST_LAP_TIMES_JSON = """
        {
            "MRData": {
                "limit": "2",
                "offset": "0",
                "total": "812",
                "RaceTable": {
                    "season": "1996",
                    "round": "1",
                    "Races": [
                        {
                            "season": "1996",
                            "round": "1",
                            "url": "http://en.wikipedia.org/wiki/1996_Australian_Grand_Prix",
                            "raceName": "Australian Grand Prix",
                            "Circuit": {
                                "circuitId": "albert_park",
                                "url": "http://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit",
                                "circuitName": "Albert Park Grand Prix Circuit",
                                "Location": {
                                    "lat": "-37.8497",
                                    "long": "144.968",
                                    "locality": "Melbourne",
                                    "country": "Australia"
                                }
                            },
                            "date": "1996-03-10",
                            "Laps": [
                                {
                                    "number": "1",
                                    "Timings": [
                                        {
                                            "driverId": "villeneuve",
                                            "position": "1",
                                            "time": "1:43.702"
                                        },
                                        {
                                            "driverId": "damon_hill",
                                            "position": "2",
                                            "time": "1:44.243"
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                }
            }
        }
        """;

    private static final String TEST_DRIVER_STANDINGS_JSON = """
        {
            "MRData": {
                "limit": "2",
                "offset": "0",
                "total": "22",
                "StandingsTable": {
                    "season": "2008",
                    "round": "5",
                    "StandingsLists": [
                        {
                            "season": "2008",
                            "round": "5",
                            "DriverStandings": [
                                {
                                    "position": "1",
                                    "positionText": "1",
                                    "points": "35",
                                    "wins": "2",
                                    "Driver": {
                                        "driverId": "raikkonen",
                                        "permanentNumber": "7",
                                        "code": "RAI",
                                        "url": "http://en.wikipedia.org/wiki/Kimi_R%C3%A4ikk%C3%B6nen",
                                        "givenName": "Kimi",
                                        "familyName": "Räikkönen",
                                        "dateOfBirth": "1979-10-17",
                                        "nationality": "Finnish"
                                    },
                                    "Constructors": [
                                        {
                                            "constructorId": "ferrari",
                                            "url": "http://en.wikipedia.org/wiki/Scuderia_Ferrari",
                                            "name": "Ferrari",
                                            "nationality": "Italian"
                                        }
                                    ]
                                },
                                {
                                    "position": "2",
                                    "positionText": "2",
                                    "points": "28",
                                    "wins": "2",
                                    "Driver": {
                                        "driverId": "massa",
                                        "permanentNumber": "19",
                                        "code": "MAS",
                                        "url": "http://en.wikipedia.org/wiki/Felipe_Massa",
                                        "givenName": "Felipe",
                                        "familyName": "Massa",
                                        "dateOfBirth": "1981-04-25",
                                        "nationality": "Brazilian"
                                    },
                                    "Constructors": [
                                        {
                                            "constructorId": "ferrari",
                                            "url": "http://en.wikipedia.org/wiki/Scuderia_Ferrari",
                                            "name": "Ferrari",
                                            "nationality": "Italian"
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                }
            }
        }
        """;
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
        final ErgastResponse<ConstructorData> parsedData = new Parser().parseConstructorsResponseToObjects(TEST_CONSTRUCTOR_JSON);

        assertEquals(expectedData, parsedData.getData());
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
        final ErgastResponse<DriverData> parsedData = new Parser().parseDriversResponseToObjects(TEST_DRIVER_JSON);

        assertEquals(expectedData, parsedData.getData());
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
        final ErgastResponse<SeasonData> parsedData = new Parser().parseSeasonsResponseToObjects(TEST_SEASONS_JSON);

        assertEquals(expectedData, parsedData.getData());
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
        final ErgastResponse<CircuitData> parsedData = new Parser().parseCircuitsResponseToObjects(TEST_CIRCUITS_JSON);

        assertEquals(expectedData, parsedData.getData());
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
        final ErgastResponse<RaceData> parsedData = new Parser().parseRacesResponseToObjects(TEST_RACES_JSON);

        assertEquals(expectedData, parsedData.getData());
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_races() {
        assertThrows(IOException.class, () -> new Parser().parseRacesResponseToObjects(BAD_JSON_FORMAT));
    }

    @Test
    void should_parse_valid_finish_status_json_to_correct_race_object_list() throws IOException {
        final List<FinishStatusData> expectedData = List.of(new FinishStatusData(1, "Finished"), new FinishStatusData(139, "Illness"));
        final ErgastResponse<FinishStatusData> parsedData = new Parser().parseFinishStatusResponseToObjects(TEST_FINISH_STATUS_JSON);
        assertEquals(expectedData, parsedData.getData());
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_finish_status() {
        assertThrows(IOException.class, () -> new Parser().parseFinishStatusResponseToObjects(BAD_JSON_FORMAT));
    }

    @Test
    void should_parse_valid_pitstops_json_to_correct_pitstop_object_list() throws IOException, ParseException {
        final List<PitStopData> expectedData = List.of(
            new PitStopData("hamilton", 11, 1, "18:21:54", BigDecimal.valueOf(25.201)),
            new PitStopData("alonso", 11, 1, "18:22:10", BigDecimal.valueOf(25.365))
        );
        final ErgastResponse<PitStopDataHolder> parsedData = new Parser().parsePitStopResponseToObjects(TEST_PITSTOP_JSON);
        assertEquals(singletonList(new PitStopDataHolder(expectedData)), parsedData.getData());
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_pitstop() {
        assertThrows(IOException.class, () -> new Parser().parsePitStopResponseToObjects(BAD_JSON_FORMAT));
    }

    @Test
    void should_parse_valid_lap_times_json_to_correct_lap_times_object_list() throws IOException, ParseException {
        final List<LapTimeData> expectedData = List.of(new LapTimeData(1, List.of(
            new TimingData("villeneuve", 1, "1:43.702"),
            new TimingData("damon_hill", 2, "1:44.243")
        )));
        final ErgastResponse<LapTimesDataHolder> parsedData = new Parser().parseLapTimesResponseToObjects(TEST_LAP_TIMES_JSON);
        assertEquals(singletonList(new LapTimesDataHolder(expectedData)), parsedData.getData());
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_lap_times() {
        assertThrows(IOException.class, () -> new Parser().parseLapTimesResponseToObjects(BAD_JSON_FORMAT));
    }

    @Test
    void should_parse_valid_driver_standings_json_to_correct_driver_standings_object_list() throws IOException, ParseException {
        final List<DriverStandingsData> expectedData = List.of(
            new DriverStandingsData(1, BigDecimal.valueOf(35), 2, new DriverData(
                "raikkonen",
                "http://en.wikipedia.org/wiki/Kimi_R%C3%A4ikk%C3%B6nen",
                "Kimi",
                "Räikkönen",
                "1979-10-17",
                "Finnish",
                7,
                "RAI"
            ), singletonList(new ConstructorData(
                "ferrari",
                "http://en.wikipedia.org/wiki/Scuderia_Ferrari",
                "Ferrari",
                "Italian"
            ))),
            new DriverStandingsData(2, BigDecimal.valueOf(28), 2, new DriverData(
                "massa",
                "http://en.wikipedia.org/wiki/Felipe_Massa",
                "Felipe",
                "Massa",
                "1981-04-25",
                "Brazilian",
                19,
                "MAS"
            ), singletonList(new ConstructorData(
                "ferrari",
                "http://en.wikipedia.org/wiki/Scuderia_Ferrari",
                "Ferrari",
                "Italian"
            )))
        );
        final ErgastResponse<StandingsDataHolder> parsedData = new Parser().parseDriverStandingsResponseToObjects(TEST_DRIVER_STANDINGS_JSON);
        assertEquals(singletonList(new StandingsDataHolder(expectedData, null)), parsedData.getData());
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_driver_standings() {
        assertThrows(IOException.class, () -> new Parser().parseDriverStandingsResponseToObjects(BAD_JSON_FORMAT));
    }
}
