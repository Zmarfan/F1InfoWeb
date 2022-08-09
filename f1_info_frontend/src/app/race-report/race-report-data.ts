import {ReportColumn} from '../reports/report-element/report-column';
import {CountryEntry} from '../reports/entry/country-entry/country-entry';
import {ReportParameters} from '../reports/report-element/report-element.component';
import {OverviewRaceReportResponse} from '../../generated/server-responses';
import {RaceType} from '../driver-report/driver-report-data';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';

export enum RaceReport {
    OVERVIEW = 1,
    RACE_RESULT = 2,
    FASTEST_LAPS = 3,
    PIT_STOPS = 4,
    STARTING_GRID = 5,
    SPRINT = 6,
    SPRINT_GRID = 7,
    PRACTICE_1 = 8,
    PRACTICE_2 = 9,
    PRACTICE_3 = 10,
    QUALIFYING = 11,
}

export interface RaceOverviewRow {
    grandPrix: CountryEntry;
    date: string;
    winner: string;
    constructor: string;
    laps: number;
    time: string;
}

export interface RaceResultRow {
    position: number;
    driverNumber: number;
    driver: string;
    constructor: string;
    laps: number;
    timeRetired: string;
    points: number;
}

export interface FastestLapsRow {
    position: number;
    driverNumber: number;
    driver: string;
    constructor: string;
    lap: number;
    time: string;
    averageSpeed: number;
}

export interface PitStopsRow {
    stopNumber: number;
    driverNumber: number;
    driver: string;
    constructor: string;
    lap: number;
    time: string;
    total: number;
}

export interface OverviewRaceReportParameters extends ReportParameters{
    season: number;
    raceType: RaceType;
}

export class RaceReportData {
    public static readonly overviewReportColumns: ReportColumn[] = [
        new ReportColumn('grandPrix', 'reports.race.overview.grandPrix'),
        new ReportColumn('date', 'reports.race.overview.date'),
        new ReportColumn('winner', 'reports.race.overview.winner'),
        new ReportColumn('constructor', 'reports.race.overview.constructor'),
        new ReportColumn('laps', 'reports.race.overview.laps'),
        new ReportColumn('time', 'reports.race.overview.time'),
    ];

    public static readonly raceResultReportColumns: ReportColumn[] = [
        new ReportColumn('position', 'reports.race.raceResult.position'),
        new ReportColumn('driverNumber', 'reports.race.raceResult.driverNumber'),
        new ReportColumn('driver', 'reports.race.raceResult.driver'),
        new ReportColumn('constructor', 'reports.race.raceResult.constructor'),
        new ReportColumn('laps', 'reports.race.raceResult.laps'),
        new ReportColumn('timeRetired', 'reports.race.raceResult.timeRetired'),
        new ReportColumn('points', 'reports.race.raceResult.points'),
    ];

    public static readonly fastestLapsReportColumns: ReportColumn[] = [
        new ReportColumn('position', 'reports.race.fastestLaps.position'),
        new ReportColumn('driverNumber', 'reports.race.fastestLaps.driverNumber'),
        new ReportColumn('driver', 'reports.race.fastestLaps.driver'),
        new ReportColumn('constructor', 'reports.race.fastestLaps.constructor'),
        new ReportColumn('lap', 'reports.race.fastestLaps.lap'),
        new ReportColumn('time', 'reports.race.fastestLaps.time'),
        new ReportColumn('averageSpeed', 'reports.race.fastestLaps.averageSpeed'),
    ];

    public static readonly pitStopsReportColumns: ReportColumn[] = [
        new ReportColumn('stopNumber', 'reports.race.pitStops.stopNumber'),
        new ReportColumn('driverNumber', 'reports.race.pitStops.driverNumber'),
        new ReportColumn('driver', 'reports.race.pitStops.driver'),
        new ReportColumn('constructor', 'reports.race.pitStops.constructor'),
        new ReportColumn('lap', 'reports.race.pitStops.lap'),
        new ReportColumn('time', 'reports.race.pitStops.time'),
        new ReportColumn('averageSpeed', 'reports.race.pitStops.total'),
    ];

    public static getRaceCategoryOptions(raceHasSprint: boolean): DropdownOption[] {
        return [
            { displayValue: 'reports.race.raceCategory.raceResult', value: RaceReport.RACE_RESULT },
            { displayValue: 'reports.race.raceCategory.fastestLaps', value: RaceReport.FASTEST_LAPS },
            { displayValue: 'reports.race.raceCategory.pitStops', value: RaceReport.PIT_STOPS },
            { displayValue: 'reports.race.raceCategory.startingGrid', value: RaceReport.STARTING_GRID },
            { displayValue: 'reports.race.raceCategory.qualifying', value: RaceReport.QUALIFYING },
            ...(raceHasSprint ? [{ displayValue: 'reports.race.raceCategory.sprint', value: RaceReport.SPRINT }] : []),
            ...(raceHasSprint ? [{ displayValue: 'reports.race.raceCategory.sprintGrid', value: RaceReport.SPRINT_GRID }] : []),
            { displayValue: 'reports.race.raceCategory.practice', translateParams: { round: 1 }, value: RaceReport.PRACTICE_1 },
            { displayValue: 'reports.race.raceCategory.practice', translateParams: { round: 2 }, value: RaceReport.PRACTICE_2 },
            ...(!raceHasSprint ? [{ displayValue: 'reports.race.raceCategory.practice', translateParams: { round: 3 }, value: RaceReport.PRACTICE_3 }] : []),
        ];
    }

    public static overviewToView(response: OverviewRaceReportResponse): RaceOverviewRow {
        return {
            grandPrix: new CountryEntry({ displayValue: response.raceName, isoCode: response.raceIsoCode }),
            date: response.date,
            winner: response.winner,
            constructor: response.constructor,
            laps: response.laps,
            time: response.time,
        };
    }
}
