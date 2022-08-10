import {ReportColumn} from '../reports/report-element/report-column';
import {CountryEntry} from '../reports/entry/country-entry/country-entry';
import {ReportParameters} from '../reports/report-element/report-element.component';
import {OverviewRaceReportResponse, RaceResultReportResponse} from '../../generated/server-responses';
import {RaceType} from '../driver-report/driver-report-data';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';

export enum RaceReport {
    OVERVIEW = 1,
    RACE_RESULT = 2,
    FASTEST_LAPS = 3,
    PIT_STOPS = 4,
    STARTING_GRID = 5,
    QUALIFYING = 6,
    SPRINT = 7,
    SPRINT_GRID = 8,
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
    nationality: CountryEntry;
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

export interface StartingGridRow {
    position: number;
    driverNumber: number;
    driver: string;
    constructor: string;
}

export interface QualifyingRow {
    position: number;
    driverNumber: number;
    driver: string;
    constructor: string;
    q1: string;
    q2: string;
    q3: string;
    laps: string;
}

export interface OverviewRaceReportParameters extends ReportParameters{
    season: number;
    raceType: RaceType;
}

export interface RaceResultReportParameters extends ReportParameters{
    season: number;
    round: number;
    type: RaceType;
}

export class RaceReportData {
    public static readonly overviewReportColumns: ReportColumn<RaceOverviewRow>[] = [
        new ReportColumn('grandPrix', 'reports.race.overview.grandPrix'),
        new ReportColumn('date', 'reports.race.overview.date'),
        new ReportColumn('winner', 'reports.race.overview.winner'),
        new ReportColumn('constructor', 'reports.race.overview.constructor'),
        new ReportColumn('laps', 'reports.race.overview.laps'),
        new ReportColumn('time', 'reports.race.overview.time', true),
    ];

    public static readonly raceResultReportColumns: ReportColumn<RaceResultRow>[] = [
        new ReportColumn('position', 'reports.race.raceResult.position'),
        new ReportColumn('driverNumber', 'reports.race.raceResult.driverNumber', true),
        new ReportColumn('driver', 'reports.race.raceResult.driver'),
        new ReportColumn('nationality', 'reports.race.raceResult.nationality', true),
        new ReportColumn('constructor', 'reports.race.raceResult.constructor'),
        new ReportColumn('laps', 'reports.race.raceResult.laps', true),
        new ReportColumn('timeRetired', 'reports.race.raceResult.timeRetired'),
        new ReportColumn('points', 'reports.race.raceResult.points'),
    ];

    public static readonly fastestLapsReportColumns: ReportColumn<FastestLapsRow>[] = [
        new ReportColumn('position', 'reports.race.fastestLaps.position'),
        new ReportColumn('driverNumber', 'reports.race.fastestLaps.driverNumber', true),
        new ReportColumn('driver', 'reports.race.fastestLaps.driver'),
        new ReportColumn('constructor', 'reports.race.fastestLaps.constructor'),
        new ReportColumn('lap', 'reports.race.fastestLaps.lap'),
        new ReportColumn('time', 'reports.race.fastestLaps.time'),
        new ReportColumn('averageSpeed', 'reports.race.fastestLaps.averageSpeed'),
    ];

    public static readonly pitStopsReportColumns: ReportColumn<PitStopsRow>[] = [
        new ReportColumn('stopNumber', 'reports.race.pitStops.stopNumber'),
        new ReportColumn('driverNumber', 'reports.race.pitStops.driverNumber', true),
        new ReportColumn('driver', 'reports.race.pitStops.driver'),
        new ReportColumn('constructor', 'reports.race.pitStops.constructor'),
        new ReportColumn('lap', 'reports.race.pitStops.lap'),
        new ReportColumn('time', 'reports.race.pitStops.time'),
        new ReportColumn('total', 'reports.race.pitStops.total'),
    ];

    public static readonly startingGridReportColumns: ReportColumn<StartingGridRow>[] = [
        new ReportColumn('position', 'reports.race.startingGrid.position'),
        new ReportColumn('driverNumber', 'reports.race.startingGrid.driverNumber', true),
        new ReportColumn('driver', 'reports.race.startingGrid.driver'),
        new ReportColumn('constructor', 'reports.race.startingGrid.constructor'),
    ];

    public static readonly qualifyingReportColumns: ReportColumn<QualifyingRow>[] = [
        new ReportColumn('position', 'reports.race.qualifying.position'),
        new ReportColumn('driverNumber', 'reports.race.qualifying.driverNumber', true),
        new ReportColumn('driver', 'reports.race.qualifying.driver'),
        new ReportColumn('constructor', 'reports.race.qualifying.constructor'),
        new ReportColumn('q1', 'reports.race.qualifying.q1'),
        new ReportColumn('q2', 'reports.race.qualifying.q2'),
        new ReportColumn('q3', 'reports.race.qualifying.q3'),
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

    public static raceResultToView(response: RaceResultReportResponse): RaceResultRow {
        return {
            position: response.position,
            driverNumber: response.driverNumber ?? '-',
            driver: response.driver,
            nationality: new CountryEntry({ displayValue: response.countryCodes.icoCode, isoCode: response.countryCodes.isoCode }),
            constructor: response.constructor,
            laps: response.laps,
            timeRetired: response.timeRetired,
            points: response.points,
        };
    }
}
