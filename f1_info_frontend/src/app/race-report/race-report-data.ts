import {ReportColumn} from '../reports/report-element/report-column';
import {CountryEntry} from '../reports/entry/country-entry/country-entry';

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

export class RaceReportData {
    public static readonly overviewReportColumns: ReportColumn[] = [
        new ReportColumn('grandPrix', 'reports.race.overview.grandPrix'),
        new ReportColumn('date', 'reports.race.overview.date'),
        new ReportColumn('winner', 'reports.race.overview.winner'),
        new ReportColumn('constructor', 'reports.race.overview.constructor'),
        new ReportColumn('laps', 'reports.race.overview.laps'),
        new ReportColumn('time', 'reports.race.overview.time'),
    ];
}
