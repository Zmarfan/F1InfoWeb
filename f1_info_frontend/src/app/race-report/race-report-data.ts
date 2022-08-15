import {ReportColumn} from '../reports/report-element/report-column';
import {CountryEntry} from '../reports/entry/country-entry/country-entry';
import {ReportParameters} from '../reports/report-element/report-element.component';
import {
    FastestLapsReportResponse,
    OverviewRaceReportResponse,
    PitStopsReportResponse,
    QualifyingReportResponse,
    RaceResultReportResponse
} from '../../generated/server-responses';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {RaceType} from '../reports/filters/drop-down-filter/drop-down-filter-provider';
import {DriverEntry} from '../reports/entry/driver-entry/driver-entry';

export enum RaceReport {
    OVERVIEW = 1,
    RACE_RESULT = 2,
    FASTEST_LAPS = 3,
    PIT_STOPS = 4,
    QUALIFYING = 5,
    SPRINT = 6,
}

export interface RaceOverviewRow {
    grandPrix: CountryEntry;
    date: string;
    winner: DriverEntry;
    constructor: string;
    laps: number;
    time: string;
}

export interface RaceResultRow {
    position: number;
    startPosition: number;
    driverNumber: number;
    driver: DriverEntry;
    nationality: CountryEntry;
    constructor: string;
    laps: number;
    timeRetired: string;
    points: number;
}

export interface FastestLapsRow {
    position: number;
    driverNumber: number;
    driver: DriverEntry;
    nationality: CountryEntry;
    constructor: string;
    lap: number;
    time: string;
    averageSpeed: string;
}

export interface PitStopsRow {
    stopNumber: number;
    driverNumber: number;
    driver: DriverEntry;
    nationality: CountryEntry;
    constructor: string;
    lap: number;
    time: string;
    duration: number;
}

export interface QualifyingRow {
    position: number;
    driverNumber: number;
    driver: DriverEntry;
    nationality: CountryEntry;
    constructor: string;
    q1: string;
    q1Time: string;
    q2: string;
    q2Time: string;
    q3: string;
    q3Time: string;
}

export interface RaceReportParameters extends ReportParameters{
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
        new ReportColumn('startPosition', 'reports.race.raceResult.startPosition', true),
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
        new ReportColumn('nationality', 'reports.race.fastestLaps.nationality', true),
        new ReportColumn('constructor', 'reports.race.fastestLaps.constructor'),
        new ReportColumn('lap', 'reports.race.fastestLaps.lap', true),
        new ReportColumn('time', 'reports.race.fastestLaps.time'),
        new ReportColumn('averageSpeed', 'reports.race.fastestLaps.averageSpeed', true),
    ];

    public static readonly pitStopsReportColumns: ReportColumn<PitStopsRow>[] = [
        new ReportColumn('stopNumber', 'reports.race.pitStops.stopNumber'),
        new ReportColumn('driverNumber', 'reports.race.pitStops.driverNumber', true),
        new ReportColumn('driver', 'reports.race.pitStops.driver'),
        new ReportColumn('nationality', 'reports.race.pitStops.nationality', true),
        new ReportColumn('constructor', 'reports.race.pitStops.constructor'),
        new ReportColumn('lap', 'reports.race.pitStops.lap'),
        new ReportColumn('time', 'reports.race.pitStops.time', true),
        new ReportColumn('duration', 'reports.race.pitStops.duration'),
    ];

    public static readonly onePartQualifyingReportColumns: ReportColumn<QualifyingRow>[] = [
        new ReportColumn('position', 'reports.race.qualifying.position'),
        new ReportColumn('driverNumber', 'reports.race.qualifying.driverNumber', true),
        new ReportColumn('driver', 'reports.race.qualifying.driver'),
        new ReportColumn('nationality', 'reports.race.qualifying.nationality', true),
        new ReportColumn('constructor', 'reports.race.qualifying.constructor'),
        new ReportColumn('q1', 'reports.race.qualifying.q1'),
        new ReportColumn('q1Time', 'reports.race.qualifying.q1Time', true),
    ];

    public static readonly twoPartQualifyingReportColumns: ReportColumn<QualifyingRow>[] = [
        new ReportColumn('position', 'reports.race.qualifying.position'),
        new ReportColumn('driverNumber', 'reports.race.qualifying.driverNumber', true),
        new ReportColumn('driver', 'reports.race.qualifying.driver'),
        new ReportColumn('nationality', 'reports.race.qualifying.nationality', true),
        new ReportColumn('constructor', 'reports.race.qualifying.constructor'),
        new ReportColumn('q1', 'reports.race.qualifying.q1'),
        new ReportColumn('q1Time', 'reports.race.qualifying.q1Time', true),
        new ReportColumn('q2', 'reports.race.qualifying.q2'),
        new ReportColumn('q2Time', 'reports.race.qualifying.q2Time', true),
    ];

    public static readonly threePartQualifyingReportColumns: ReportColumn<QualifyingRow>[] = [
        new ReportColumn('position', 'reports.race.qualifying.position'),
        new ReportColumn('driverNumber', 'reports.race.qualifying.driverNumber', true),
        new ReportColumn('driver', 'reports.race.qualifying.driver'),
        new ReportColumn('nationality', 'reports.race.qualifying.nationality', true),
        new ReportColumn('constructor', 'reports.race.qualifying.constructor', true),
        new ReportColumn('q1', 'reports.race.qualifying.q1'),
        new ReportColumn('q1Time', 'reports.race.qualifying.q1Time', true),
        new ReportColumn('q2', 'reports.race.qualifying.q2'),
        new ReportColumn('q2Time', 'reports.race.qualifying.q2Time', true),
        new ReportColumn('q3', 'reports.race.qualifying.q3'),
        new ReportColumn('q3Time', 'reports.race.qualifying.q3Time', true),
    ];

    private static readonly FIRST_SEASON_FASTEST_LAPS: number = 2004;

    public static getRaceCategoryOptions(raceHasSprint: boolean, season: number): DropdownOption[] {
        return [
            { displayValue: 'reports.race.raceCategory.raceResult', value: RaceReport.RACE_RESULT },
            ...(season >= RaceReportData.FIRST_SEASON_FASTEST_LAPS
                ? [{ displayValue: 'reports.race.raceCategory.fastestLaps', value: RaceReport.FASTEST_LAPS }]
                : []),
            { displayValue: 'reports.race.raceCategory.pitStops', value: RaceReport.PIT_STOPS },
            ...(raceHasSprint ? [{ displayValue: 'reports.race.raceCategory.sprint', value: RaceReport.SPRINT }] : []),
            { displayValue: 'reports.race.raceCategory.qualifying', value: RaceReport.QUALIFYING },
        ];
    }

    public static getQualifyingColumnsBySeasonAndRound(season: number, round: number | null) {
        if (round === null) {
            return [];
        }

        if (season === 2005 && round <= 6) {
            return RaceReportData.twoPartQualifyingReportColumns;
        }
        if (season >= 2006) {
            return RaceReportData.threePartQualifyingReportColumns;
        }

        return RaceReportData.onePartQualifyingReportColumns;
    }

    public static overviewToView(response: OverviewRaceReportResponse): RaceOverviewRow {
        return {
            grandPrix: new CountryEntry({ displayValue: response.raceName, isoCode: response.raceIsoCode }),
            date: response.date,
            winner: new DriverEntry({ name: response.winner, driverIdentifier: response.driverIdentifier }),
            constructor: response.constructor,
            laps: response.laps,
            time: response.time,
        };
    }

    public static raceResultToView(response: RaceResultReportResponse): RaceResultRow {
        return {
            position: response.position,
            startPosition: response.startPosition,
            driverNumber: response.driverNumber,
            driver: new DriverEntry({ name: response.driver, driverIdentifier: response.driverIdentifier }),
            nationality: new CountryEntry({ displayValue: response.countryCodes.icoCode, isoCode: response.countryCodes.isoCode }),
            constructor: response.constructor,
            laps: response.laps,
            timeRetired: response.timeRetired,
            points: response.points,
        };
    }

    public static fastestLapsToView(response: FastestLapsReportResponse): FastestLapsRow {
        return {
            position: response.position,
            driverNumber: response.driverNumber,
            driver: new DriverEntry({ name: response.driver, driverIdentifier: response.driverIdentifier }),
            nationality: new CountryEntry({ displayValue: response.countryCodes.icoCode, isoCode: response.countryCodes.isoCode }),
            constructor: response.constructor,
            lap: response.lap,
            time: response.time,
            averageSpeed: response.averageSpeed,
        };
    }

    public static pitStopsToView(response: PitStopsReportResponse): PitStopsRow {
        return {
            stopNumber: response.stopNumber,
            driverNumber: response.driverNumber,
            driver: new DriverEntry({ name: response.driver, driverIdentifier: response.driverIdentifier }),
            nationality: new CountryEntry({ displayValue: response.countryCodes.icoCode, isoCode: response.countryCodes.isoCode }),
            constructor: response.constructor,
            lap: response.lap,
            time: response.time,
            duration: response.duration,
        };
    }

    public static qualifyingToView(response: QualifyingReportResponse): QualifyingRow {
        return {
            position: response.position,
            driverNumber: response.driverNumber,
            driver: new DriverEntry({ name: response.driver, driverIdentifier: response.driverIdentifier }),
            nationality: new CountryEntry({ displayValue: response.countryCodes.icoCode, isoCode: response.countryCodes.isoCode }),
            constructor: response.constructor,
            q1: response.q1,
            q1Time: response.q1Time,
            q2: response.q2,
            q2Time: response.q2Time,
            q3: response.q3,
            q3Time: response.q3Time,
        };
    }
}
