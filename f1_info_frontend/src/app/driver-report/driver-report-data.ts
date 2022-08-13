import {ReportColumn} from '../reports/report-element/report-column';
import {CountryEntry} from '../reports/entry/country-entry/country-entry';
import {ReportParameters} from '../reports/report-element/report-element.component';
import {AllDriverReportResponse, IndividualDriverReportResponse} from '../../generated/server-responses';
import {PositionMoveEntry} from '../reports/entry/position-move-entry/position-move-entry';

export interface AllDriverRow {
    position: PositionMoveEntry;
    driverNumber: number;
    driver: string;
    nationality: CountryEntry;
    constructor: string;
    points: number;
}

export interface IndividualDriverRow {
    grandPrix: CountryEntry;
    date: string;
    constructor: string;
    racePosition: string;
    points: number;
}

export enum RaceType {
    RACE = 'race',
    SPRINT = 'sprint',
}

export interface AllDriverReportParameters extends ReportParameters{
    season: number;
    round: number;
}

export interface IndividualDriverReportParameters extends ReportParameters{
    season: number;
    driverIdentifier: string;
    raceType: RaceType;
}

export class DriverReportData {
    public static readonly allReportColumns: ReportColumn<AllDriverRow>[] = [
        new ReportColumn('position', 'reports.driver.all.position'),
        new ReportColumn('driverNumber', 'reports.driver.all.driverNumber', true),
        new ReportColumn('driver', 'reports.driver.all.driver'),
        new ReportColumn('nationality', 'reports.driver.all.nationality'),
        new ReportColumn('constructor', 'reports.driver.all.constructor'),
        new ReportColumn('points', 'reports.driver.all.points'),
    ];

    public static readonly driverReportColumns: ReportColumn<IndividualDriverRow>[] = [
        new ReportColumn('grandPrix', 'reports.driver.driver.grandPrix'),
        new ReportColumn('date', 'reports.driver.driver.date'),
        new ReportColumn('constructor', 'reports.driver.driver.constructor'),
        new ReportColumn('racePosition', 'reports.driver.driver.racePosition'),
        new ReportColumn('points', 'reports.driver.driver.points'),
    ];

    public static allToView(response: AllDriverReportResponse): AllDriverRow {
        return {
            position: new PositionMoveEntry({ position: response.position, move: response.positionMove }),
            driverNumber: response.driverNumber,
            driver: response.driverFullName,
            nationality: new CountryEntry({ displayValue: response.countryCodes.icoCode, isoCode: response.countryCodes.isoCode }),
            constructor: response.constructor,
            points: response.points,
        };
    }

    public static individualToView(response: IndividualDriverReportResponse): IndividualDriverRow {
        return {
            grandPrix: new CountryEntry({ displayValue: response.raceName, isoCode: response.raceIsoCode }),
            date: response.date,
            constructor: response.constructor,
            racePosition: response.racePosition,
            points: response.points,
        };
    }
}

