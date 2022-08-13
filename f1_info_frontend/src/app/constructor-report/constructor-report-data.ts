import {PositionMoveEntry} from '../reports/entry/position-move-entry/position-move-entry';
import {CountryEntry} from '../reports/entry/country-entry/country-entry';
import {ReportParameters} from '../reports/report-element/report-element.component';
import {RaceType} from '../reports/filters/drop-down-filter/drop-down-filter-provider';
import {ReportColumn} from '../reports/report-element/report-column';
import {IndividualConstructorReportResponse, IndividualDriverReportResponse, OverviewConstructorReportResponse} from '../../generated/server-responses';
import {IndividualDriverRow} from '../driver-report/driver-report-data';

export interface OverviewConstructorRow {
    position: PositionMoveEntry;
    constructor: string;
    nationality: CountryEntry;
    points: number;
}

export interface IndividualConstructorRow {
    grandPrix: CountryEntry;
    date: string;
    points: number;
}

export interface OverviewConstructorReportParameters extends ReportParameters{
    season: number;
    round: number;
}

export interface IndividualConstructorReportParameters extends ReportParameters{
    season: number;
    constructorIdentifier: string;
    raceType: RaceType;
}

export class ConstructorReportData {
    public static overviewReportColumns: ReportColumn<OverviewConstructorRow>[] = [
        new ReportColumn('position', 'reports.constructor.overview.position'),
        new ReportColumn('nationality', 'reports.constructor.overview.nationality'),
        new ReportColumn('constructor', 'reports.constructor.overview.constructor'),
        new ReportColumn('points', 'reports.constructor.overview.points'),
    ];
    public static individualReportColumns: ReportColumn<IndividualConstructorRow>[] = [
        new ReportColumn('grandPrix', 'reports.constructor.individual.grandPrix'),
        new ReportColumn('date', 'reports.constructor.individual.date'),
        new ReportColumn('points', 'reports.constructor.individual.points'),
    ];

    public static overviewToView(response: OverviewConstructorReportResponse): OverviewConstructorRow {
        return {
            position: new PositionMoveEntry({ position: response.position, move: response.positionMove }),
            nationality: new CountryEntry({ displayValue: response.countryCodes.icoCode, isoCode: response.countryCodes.isoCode }),
            constructor: response.name,
            points: response.points,
        };
    }

    public static individualToView(response: IndividualConstructorReportResponse): IndividualConstructorRow {
        return {
            grandPrix: new CountryEntry({ displayValue: response.raceName, isoCode: response.raceIsoCode }),
            date: response.date,
            points: response.points,
        };
    }
}

