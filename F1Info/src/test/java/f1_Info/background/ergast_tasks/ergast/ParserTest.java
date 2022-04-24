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
import f1_Info.background.ergast_tasks.ergast.responses.results.*;
import f1_Info.background.ergast_tasks.ergast.responses.standings.ConstructorStandingsData;
import f1_Info.background.ergast_tasks.ergast.responses.standings.DriverStandingsData;
import f1_Info.background.ergast_tasks.ergast.responses.standings.StandingsDataHolder;
import f1_Info.constants.Country;
import f1_Info.constants.FinishStatus;
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

    private static final String TEST_CONSTRUCTOR_STANDINGS_JSON = """
        {
            "MRData": {
                "limit": "2",
                "offset": "0",
                "total": "10",
                "StandingsTable": {
                    "season": "2018",
                    "round": "5",
                    "StandingsLists": [
                        {
                            "season": "2018",
                            "round": "5",
                            "ConstructorStandings": [
                                {
                                    "position": "1",
                                    "positionText": "1",
                                    "points": "153",
                                    "wins": "2",
                                    "Constructor": {
                                        "constructorId": "mercedes",
                                        "url": "http://en.wikipedia.org/wiki/Mercedes-Benz_in_Formula_One",
                                        "name": "Mercedes",
                                        "nationality": "German"
                                    }
                                },
                                {
                                    "position": "2",
                                    "positionText": "2",
                                    "points": "126",
                                    "wins": "2",
                                    "Constructor": {
                                        "constructorId": "ferrari",
                                        "url": "http://en.wikipedia.org/wiki/Scuderia_Ferrari",
                                        "name": "Ferrari",
                                        "nationality": "Italian"
                                    }
                                }
                            ]
                        }
                    ]
                }
            }
        }
        """;

    private static final String TEST_SPRINT_RESULTS_JSON = """
        {
            "MRData": {
                "limit": "2",
                "offset": "38",
                "total": "60",
                "RaceTable": {
                    "season": "2021",
                    "Races": [
                        {
                            "season": "2021",
                            "round": "14",
                            "url": "http://en.wikipedia.org/wiki/2021_Italian_Grand_Prix",
                            "raceName": "Italian Grand Prix",
                            "Circuit": {
                                "circuitId": "monza",
                                "url": "http://en.wikipedia.org/wiki/Autodromo_Nazionale_Monza",
                                "circuitName": "Autodromo Nazionale di Monza",
                                "Location": {
                                    "lat": "45.6156",
                                    "long": "9.28111",
                                    "locality": "Monza",
                                    "country": "Italy"
                                }
                            },
                            "date": "2021-09-12",
                            "time": "13:00:00Z",
                            "SprintResults": [
                                {
                                    "number": "47",
                                    "position": "19",
                                    "positionText": "19",
                                    "points": "0",
                                    "Driver": {
                                        "driverId": "mick_schumacher",
                                        "permanentNumber": "47",
                                        "code": "MSC",
                                        "url": "http://en.wikipedia.org/wiki/Mick_Schumacher",
                                        "givenName": "Mick",
                                        "familyName": "Schumacher",
                                        "dateOfBirth": "1999-03-22",
                                        "nationality": "German"
                                    },
                                    "Constructor": {
                                        "constructorId": "haas",
                                        "url": "http://en.wikipedia.org/wiki/Haas_F1_Team",
                                        "name": "Haas F1 Team",
                                        "nationality": "American"
                                    },
                                    "grid": "18",
                                    "laps": "18",
                                    "status": "Finished",
                                    "Time": {
                                        "millis": "1740232",
                                        "time": "+1:06.154"
                                    },
                                    "FastestLap": {
                                        "lap": "7",
                                        "Time": {
                                            "time": "1:26.819"
                                        }
                                    }
                                },
                                {
                                    "number": "10",
                                    "position": "20",
                                    "positionText": "R",
                                    "points": "0",
                                    "Driver": {
                                        "driverId": "gasly",
                                        "permanentNumber": "10",
                                        "code": "GAS",
                                        "url": "http://en.wikipedia.org/wiki/Pierre_Gasly",
                                        "givenName": "Pierre",
                                        "familyName": "Gasly",
                                        "dateOfBirth": "1996-02-07",
                                        "nationality": "French"
                                    },
                                    "Constructor": {
                                        "constructorId": "alphatauri",
                                        "url": "http://en.wikipedia.org/wiki/Scuderia_AlphaTauri",
                                        "name": "AlphaTauri",
                                        "nationality": "Italian"
                                    },
                                    "grid": "6",
                                    "laps": "0",
                                    "status": "Accident"
                                }
                            ]
                        }
                    ]
                }
            }
        }
        """;

    private static final String TEST_RACE_RESULTS_JSON = """
        {
            "MRData": {
                "limit": "2",
                "offset": "0",
                "total": "60",
                "RaceTable": {
                    "season": "2022",
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
                            "Results": [
                                {
                                    "number": "16",
                                    "position": "1",
                                    "positionText": "1",
                                    "points": "26",
                                    "Driver": {
                                        "driverId": "leclerc",
                                        "permanentNumber": "16",
                                        "code": "LEC",
                                        "url": "http://en.wikipedia.org/wiki/Charles_Leclerc",
                                        "givenName": "Charles",
                                        "familyName": "Leclerc",
                                        "dateOfBirth": "1997-10-16",
                                        "nationality": "Monegasque"
                                    },
                                    "Constructor": {
                                        "constructorId": "ferrari",
                                        "url": "http://en.wikipedia.org/wiki/Scuderia_Ferrari",
                                        "name": "Ferrari",
                                        "nationality": "Italian"
                                    },
                                    "grid": "1",
                                    "laps": "57",
                                    "status": "Finished",
                                    "Time": {
                                        "millis": "5853584",
                                        "time": "1:37:33.584"
                                    },
                                    "FastestLap": {
                                        "rank": "1",
                                        "lap": "51",
                                        "Time": {
                                            "time": "1:34.570"
                                        },
                                        "AverageSpeed": {
                                            "units": "kph",
                                            "speed": "206.018"
                                        }
                                    }
                                },
                                {
                                    "number": "55",
                                    "position": "2",
                                    "positionText": "2",
                                    "points": "18",
                                    "Driver": {
                                        "driverId": "sainz",
                                        "permanentNumber": "55",
                                        "code": "SAI",
                                        "url": "http://en.wikipedia.org/wiki/Carlos_Sainz_Jr.",
                                        "givenName": "Carlos",
                                        "familyName": "Sainz",
                                        "dateOfBirth": "1994-09-01",
                                        "nationality": "Spanish"
                                    },
                                    "Constructor": {
                                        "constructorId": "ferrari",
                                        "url": "http://en.wikipedia.org/wiki/Scuderia_Ferrari",
                                        "name": "Ferrari",
                                        "nationality": "Italian"
                                    },
                                    "grid": "3",
                                    "laps": "57",
                                    "status": "Finished",
                                    "Time": {
                                        "millis": "5859182",
                                        "time": "+5.598"
                                    },
                                    "FastestLap": {
                                        "rank": "3",
                                        "lap": "52",
                                        "Time": {
                                            "time": "1:35.740"
                                        },
                                        "AverageSpeed": {
                                            "units": "kph",
                                            "speed": "203.501"
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                }
            }
        }
        """;

    private static final String TEST_QUALIFYING_RESULTS_JSON = """
        {
            "MRData": {
                "limit": "2",
                "offset": "0",
                "total": "456",
                "RaceTable": {
                    "season": "2010",
                    "Races": [
                        {
                            "season": "2010",
                            "round": "1",
                            "url": "http://en.wikipedia.org/wiki/2010_Bahrain_Grand_Prix",
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
                            "date": "2010-03-14",
                            "time": "12:00:00Z",
                            "QualifyingResults": [
                                {
                                    "number": "5",
                                    "position": "1",
                                    "Driver": {
                                        "driverId": "vettel",
                                        "permanentNumber": "5",
                                        "code": "VET",
                                        "url": "http://en.wikipedia.org/wiki/Sebastian_Vettel",
                                        "givenName": "Sebastian",
                                        "familyName": "Vettel",
                                        "dateOfBirth": "1987-07-03",
                                        "nationality": "German"
                                    },
                                    "Constructor": {
                                        "constructorId": "red_bull",
                                        "url": "http://en.wikipedia.org/wiki/Red_Bull_Racing",
                                        "name": "Red Bull",
                                        "nationality": "Austrian"
                                    },
                                    "Q1": "1:55.029",
                                    "Q2": "1:53.883",
                                    "Q3": "1:54.101"
                                },
                                {
                                    "number": "7",
                                    "position": "2",
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
                                    "Constructor": {
                                        "constructorId": "ferrari",
                                        "url": "http://en.wikipedia.org/wiki/Scuderia_Ferrari",
                                        "name": "Ferrari",
                                        "nationality": "Italian"
                                    },
                                    "Q1": "1:55.313",
                                    "Q2": "1:54.331",
                                    "Q3": "1:54.242"
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
        final List<FinishStatusData> expectedData = List.of(
            new FinishStatusData(FinishStatus.FINISHED.getStringCode()),
            new FinishStatusData(FinishStatus.ILLNESS.getStringCode())
        );
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
            new DriverStandingsData(1,"1", BigDecimal.valueOf(35), 2, new DriverData(
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
            new DriverStandingsData(2, "2", BigDecimal.valueOf(28), 2, new DriverData(
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
        final ErgastResponse<StandingsDataHolder> parsedData = new Parser().parseStandingsResponseToObjects(TEST_DRIVER_STANDINGS_JSON);
        assertEquals(singletonList(new StandingsDataHolder(expectedData, null)), parsedData.getData());
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_driver_standings() {
        assertThrows(IOException.class, () -> new Parser().parseStandingsResponseToObjects(BAD_JSON_FORMAT));
    }

    @Test
    void should_parse_valid_constructor_standings_json_to_correct_constructor_standings_object_list() throws IOException {
        final List<ConstructorStandingsData> expectedData = List.of(
            new ConstructorStandingsData(1, "2", BigDecimal.valueOf(153), 2, new ConstructorData(
                "mercedes",
                "http://en.wikipedia.org/wiki/Mercedes-Benz_in_Formula_One",
                "Mercedes",
                "German"
            )),
            new ConstructorStandingsData(2, "2", BigDecimal.valueOf(126), 2, new ConstructorData(
                "ferrari",
                "http://en.wikipedia.org/wiki/Scuderia_Ferrari",
                "Ferrari",
                "Italian"
            ))
        );
        final ErgastResponse<StandingsDataHolder> parsedData = new Parser().parseStandingsResponseToObjects(TEST_CONSTRUCTOR_STANDINGS_JSON);
        assertEquals(singletonList(new StandingsDataHolder(null, expectedData)), parsedData.getData());
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_constructor_standings() {
        assertThrows(IOException.class, () -> new Parser().parseStandingsResponseToObjects(BAD_JSON_FORMAT));
    }

    @Test
    void should_parse_valid_sprint_results_json_to_correct_sprint_results_object_list() throws IOException, ParseException {
        final List<ResultDataHolder> expectedData = singletonList(new ResultDataHolder(2021, 14, List.of(
            new ResultData(
                47,
                19,
                "19",
                BigDecimal.ZERO,
                new DriverData(
                    "mick_schumacher",
                    "http://en.wikipedia.org/wiki/Mick_Schumacher",
                    "Mick",
                    "Schumacher",
                    "1999-03-22",
                    "German",
                    47,
                    "MSC"
                ),
                new ConstructorData("haas", "http://en.wikipedia.org/wiki/Haas_F1_Team", "Haas F1 Team", "American"),
                18,
                18,
                "Finished",
                new TimeData(1740232L, "+1:06.154"),
                new FastestLapData(null, 7, new TimeData(null, "1:26.819"), null)
            ),
            new ResultData(
                10,
                20,
                "R",
                BigDecimal.ZERO,
                new DriverData(
                    "gasly",
                    "http://en.wikipedia.org/wiki/Pierre_Gasly",
                    "Pierre",
                    "Gasly",
                    "1996-02-07",
                    "French",
                    10,
                    "GAS"
                ),
                new ConstructorData("alphatauri", "http://en.wikipedia.org/wiki/Scuderia_AlphaTauri", "AlphaTauri", "Italian"),
                6,
                0,
                "Accident",
                null,
                null
            )
        ), null, null));
        final ErgastResponse<ResultDataHolder> parsedData = new Parser().parseResultsResponseToObjects(TEST_SPRINT_RESULTS_JSON);
        assertEquals(expectedData, parsedData.getData());
    }

    @Test
    void should_throw_ioexception_if_unable_to_parse_json_to_results() {
        assertThrows(IOException.class, () -> new Parser().parseResultsResponseToObjects(BAD_JSON_FORMAT));
    }

    @Test
    void should_parse_valid_race_results_json_to_correct_race_results_object_list() throws IOException, ParseException {
        final List<ResultDataHolder> expectedData = singletonList(new ResultDataHolder(2022, 1, null, List.of(
            new ResultData(
                16,
                1,
                "1",
                BigDecimal.valueOf(26),
                new DriverData(
                    "leclerc",
                    "http://en.wikipedia.org/wiki/Charles_Leclerc",
                    "Charles",
                    "Leclerc",
                    "1997-10-16",
                    "Monegasque",
                    16,
                    "LEC"
                ),
                new ConstructorData("ferrari", "http://en.wikipedia.org/wiki/Scuderia_Ferrari", "Ferrari", "Italian"),
                1,
                57,
                "Finished",
                new TimeData(5853584L, "1:37:33.584"),
                new FastestLapData(1, 51, new TimeData(null, "1:34.570"), new AverageSpeedData("kph", BigDecimal.valueOf(206.018)))
            ),
            new ResultData(
                55,
                2,
                "2",
                BigDecimal.valueOf(18),
                new DriverData(
                    "sainz",
                    "http://en.wikipedia.org/wiki/Carlos_Sainz_Jr.",
                    "Carlos",
                    "Sainz",
                    "1994-09-01",
                    "Spanish",
                    55,
                    "SAI"
                ),
                new ConstructorData("ferrari", "http://en.wikipedia.org/wiki/Scuderia_Ferrari", "Ferrari", "Italian"),
                3,
                57,
                "Finished",
                new TimeData(5859182L, "+5.598"),
                new FastestLapData(3, 52, new TimeData(null, "1:35.740"), new AverageSpeedData("kph", BigDecimal.valueOf(203.501)))
            )
        ), null));
        final ErgastResponse<ResultDataHolder> parsedData = new Parser().parseResultsResponseToObjects(TEST_RACE_RESULTS_JSON);
        assertEquals(expectedData, parsedData.getData());
    }

    @Test
    void should_parse_valid_qualifying_results_json_to_correct_qualifying_results_object_list() throws IOException, ParseException {
        final List<ResultDataHolder> expectedData = singletonList(new ResultDataHolder(2010, 1, null,null, List.of(
            new QualifyingResultData(
                5,
                1,
                new DriverData(
                    "vettel",
                    "http://en.wikipedia.org/wiki/Sebastian_Vettel",
                    "Sebastian",
                    "Vettel",
                    "1987-07-03",
                    "German",
                    5,
                    "VET"
                ),
                new ConstructorData("red_bull", "http://en.wikipedia.org/wiki/Red_Bull_Racing", "Red Bull", "Austrian"),
                "1:55.029",
                "1:53.883",
                "1:54.101"
            ),
            new QualifyingResultData(
                7,
                2,
                new DriverData(
                    "massa",
                    "http://en.wikipedia.org/wiki/Felipe_Massa",
                    "Felipe",
                    "Massa",
                    "1981-04-25",
                    "Brazilian",
                    19,
                    "MAS"
                ),
                new ConstructorData("ferrari", "http://en.wikipedia.org/wiki/Scuderia_Ferrari", "Ferrari", "Italian"),
                "1:55.313",
                "1:54.331",
                "1:54.242"
            )
        )));
        final ErgastResponse<ResultDataHolder> parsedData = new Parser().parseResultsResponseToObjects(TEST_QUALIFYING_RESULTS_JSON);
        assertEquals(expectedData, parsedData.getData());
    }
}
