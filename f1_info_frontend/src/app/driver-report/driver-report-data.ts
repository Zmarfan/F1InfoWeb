import {ReportColumn} from '../reports/report-element/report-column';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {CountryEntry} from '../reports/entry/country-entry/country-entry';
import {ReportParameters} from '../reports/report-element/report-element.component';
import {AllDriverReportResponse, IndividualDriverReportResponse} from '../../generated/server-responses';

export interface AllDriverRow {
    position: number;
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
    public static readonly allReportColumns: ReportColumn[] = [
        new ReportColumn('position', 'reports.driver.all.position'),
        new ReportColumn('driver', 'reports.driver.all.driver'),
        new ReportColumn('nationality', 'reports.driver.all.nationality'),
        new ReportColumn('constructor', 'reports.driver.all.constructor'),
        new ReportColumn('points', 'reports.driver.all.points'),
    ];

    public static readonly driverReportColumns: ReportColumn[] = [
        new ReportColumn('grandPrix', 'reports.driver.driver.grandPrix'),
        new ReportColumn('date', 'reports.driver.driver.date'),
        new ReportColumn('constructor', 'reports.driver.driver.constructor'),
        new ReportColumn('racePosition', 'reports.driver.driver.racePosition'),
        new ReportColumn('points', 'reports.driver.driver.points'),
    ];

    public static readonly raceTypeOptions: DropdownOption[] = [
        { displayValue: 'reports.driver.race', value: RaceType.RACE },
        { displayValue: 'reports.driver.sprint', value: RaceType.SPRINT },
    ];

    public static allToView(response: AllDriverReportResponse): AllDriverRow {
        return {
            position: response.position,
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

