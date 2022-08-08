import {Component, OnInit} from '@angular/core';
import {ReportColumn} from '../reports/report-element/report-column';
import {ReportParameters, ReportSortConfig, SortDirection, SortSetting} from '../reports/report-element/report-element.component';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {DropDownFilterProvider} from '../reports/filters/drop-down-filter/drop-down-filter-provider';
import {DriverReportService} from './driver-report.service';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {
    AllDriverReportResponse,
    DriverReportFilterResponse,
    IndividualDriverReportResponse,
} from '../../generated/server-responses';
import {CountryEntry} from '../reports/entry/country-entry/country-entry';

interface AllDriverRow {
    position: number;
    driver: string;
    nationality: CountryEntry;
    constructor: string;
    points: number;
}

interface IndividualDriverRow {
    grandPrix: CountryEntry;
    date: string;
    constructor: string;
    racePosition: string;
    points: number;
}

export interface AllDriverReportParameters extends ReportParameters{
    season: number;
}

export interface IndividualDriverReportParameters extends ReportParameters{
    season: number;
    driverIdentifier: string;
}

@Component({
    selector: 'app-driver-report',
    templateUrl: './driver-report.component.html',
    styleUrls: ['./driver-report.component.scss'],
})
export class DriverReportComponent implements OnInit {
    public seasonsOptions: DropdownOption[] = DropDownFilterProvider.createSeasonOptions();
    public driverOptions: DropdownOption[] = [];
    public roundOptions: DropdownOption[] = [];
    public filterLoading: boolean = true;

    public allReportColumns: ReportColumn[] = [
        new ReportColumn('position', 'reports.driver.all.position'),
        new ReportColumn('driver', 'reports.driver.all.driver'),
        new ReportColumn('nationality', 'reports.driver.all.nationality'),
        new ReportColumn('constructor', 'reports.driver.all.constructor'),
        new ReportColumn('points', 'reports.driver.all.points'),
    ];

    public driverReportColumns: ReportColumn[] = [
        new ReportColumn('grandPrix', 'reports.driver.driver.grandPrix'),
        new ReportColumn('date', 'reports.driver.driver.date'),
        new ReportColumn('constructor', 'reports.driver.driver.constructor'),
        new ReportColumn('racePosition', 'reports.driver.driver.racePosition'),
        new ReportColumn('points', 'reports.driver.driver.points'),
    ];

    public allRows: AllDriverRow[] = [];
    public individualRows: IndividualDriverRow[] = [];

    public loading: boolean = false;

    public selectedRound: number = 1;
    private mSelectedSeason: number = new Date().getFullYear();
    private mSelectedDriver: string | null = null;
    private mAllSortSetting: SortSetting = { columnName: 'position', direction: SortDirection.ASCENDING };
    private mDriverSortSetting: SortSetting = { columnName: 'date', direction: SortDirection.DESCENDING };
    private mAllSortConfig: ReportSortConfig = {
        sortCallback: (sortObject: SortSetting) => this.sort(sortObject),
        defaultSortSetting: this.mAllSortSetting,
    };
    private mDriverSortConfig: ReportSortConfig = {
        sortCallback: (sortObject: SortSetting) => this.sort(sortObject),
        defaultSortSetting: this.mDriverSortSetting,
    };

    public constructor(
        private mDriverReportService: DriverReportService,
        private mMessageService: GlobalMessageService
    ) {
    }

    public get showAllReport(): boolean {
        return this.mSelectedDriver === null;
    }

    public get allSortConfig(): ReportSortConfig {
        return this.mAllSortConfig;
    }

    public get driverSortConfig(): ReportSortConfig {
        return this.mDriverSortConfig;
    }

    private static allToView(response: AllDriverReportResponse): AllDriverRow {
        return {
            position: response.position,
            driver: response.driverFullName,
            nationality: new CountryEntry({ displayValue: response.countryCodes.icoCode, isoCode: response.countryCodes.isoCode }),
            constructor: response.constructor,
            points: response.points,
        };
    }

    private static individualToView(response: IndividualDriverReportResponse): IndividualDriverRow {
        return {
            grandPrix: new CountryEntry({ displayValue: response.circuitName, isoCode: response.circuitIsoCode }),
            date: response.date,
            constructor: response.constructor,
            racePosition: response.racePosition,
            points: response.points,
        };
    }

    public ngOnInit() {
        this.fetchAndAssignFilterValues();
        this.runReport();
    }

    public sort(sortSetting: SortSetting) {
        if (this.showAllReport) {
            this.mAllSortSetting = sortSetting;
        } else {
            this.mDriverSortSetting = sortSetting;
        }

        this.runReport();
    }

    public seasonFilterChanged = (newSeason: number) => {
        this.mSelectedSeason = newSeason;
        this.fetchAndAssignFilterValues();
        this.runReport();
    };

    public driverFilterChanged = (newDriver: string | null) => {
        this.mSelectedDriver = newDriver;
        this.runReport();
    };

    public roundFilterChanged = (newRound: number) => {
        this.selectedRound = newRound;
        this.runReport();
    };

    private fetchAndAssignFilterValues() {
        this.mSelectedDriver = null;
        this.filterLoading = true;
        this.mDriverReportService.getFilterValues(this.mSelectedSeason).subscribe({
            next: (response) => {
                this.filterLoading = false;
                this.populateDriverFilter(response);
                this.populateRoundFilter(response);
            },
            error: (error) => {
                this.filterLoading = false;
                this.mMessageService.addHttpError(error);
            },
        });
    }

    private populateDriverFilter(response: DriverReportFilterResponse) {
        this.driverOptions = response.drivers
            .sort((d1, d2) => d1.fullName.localeCompare(d2.fullName))
            .map((driver) => ({ displayValue: driver.fullName, value: driver.driverIdentifier }));
    }

    private populateRoundFilter(response: DriverReportFilterResponse) {
        const options: DropdownOption[] = [];
        for (let round = 1; round <= response.amountOfRounds; round++) {
            options.push({ displayValue: 'reports.driver.roundEntry', value: round, translateParams: { round } });
        }
        this.roundOptions = options;
        this.selectedRound = response.amountOfRounds;
    }

    private runReport() {
        if (this.showAllReport) {
            this.runAllReport({ season: this.mSelectedSeason, sortColumn: this.mAllSortSetting.columnName, sortDirection: this.mAllSortSetting.direction });
        } else {
            this.runDriverReport({
                season: this.mSelectedSeason,
                driverIdentifier: this.mSelectedDriver ?? '',
                sortColumn: this.mDriverSortSetting.columnName,
                sortDirection: this.mDriverSortSetting.direction,
            });
        }
    }

    private runAllReport(params: AllDriverReportParameters) {
        this.loading = true;
        this.mDriverReportService.getAllReport(params).subscribe({
            next: (responses) => {
                this.loading = false;
                this.allRows = responses.map((response) => DriverReportComponent.allToView(response));
            },
            error: (error) => {
                this.loading = false;
                this.mMessageService.addHttpError(error);
            },
        });
    }

    private runDriverReport(params: IndividualDriverReportParameters) {
        this.loading = true;
        this.mDriverReportService.getIndividualReport(params).subscribe({
            next: (responses) => {
                this.loading = false;
                this.individualRows = responses.map((response) => DriverReportComponent.individualToView(response));
            },
            error: (error) => {
                this.loading = false;
                this.mMessageService.addHttpError(error);
            },
        });
    }
}
