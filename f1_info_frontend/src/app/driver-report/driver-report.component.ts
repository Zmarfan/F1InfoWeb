import {Component, OnInit} from '@angular/core';
import {ReportColumn} from '../reports/report-element/report-column';
import {ReportParameters, ReportSortConfig, SortDirection, SortSetting} from '../reports/report-element/report-element.component';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {DropDownFilterProvider} from '../reports/filters/drop-down-filter/drop-down-filter-provider';
import {DriverReportService} from './driver-report.service';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {AllDriverReportResponse, DriverReportDriverResponse, IndividualDriverReportResponse} from '../../generated/server-responses';

interface AllDriverRow {
    position: number;
    driver: string;
    nationality: string;
    constructor: string;
    points: number;
}

interface IndividualDriverRow {
    grandPrix: string;
    date: string;
    constructor: string;
    racePosition: number;
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
    public driverSelectLoading: boolean = true;

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

    private mSelectedSeason: number = new Date().getFullYear();
    private mSelectedDriver: string | null = null;
    private mAllSortSetting: SortSetting = { columnName: 'position', direction: SortDirection.ASCENDING };
    private mDriverSortSetting: SortSetting = { columnName: 'date', direction: SortDirection.ASCENDING };
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
            nationality: response.driverCountry,
            constructor: response.constructor,
            points: response.points,
        };
    }

    private static individualToView(response: IndividualDriverReportResponse): IndividualDriverRow {
        return {
            grandPrix: response.grandPrix,
            date: response.date,
            constructor: response.constructor,
            racePosition: response.racePosition,
            points: response.points,
        };
    }

    public ngOnInit() {
        this.fetchDriversFromSeason();
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
        this.fetchDriversFromSeason();
        this.runReport();
    };

    public driverFilterChanged = (newDriver: string | null) => {
        this.mSelectedDriver = newDriver;
        this.runReport();
    };

    private fetchDriversFromSeason() {
        this.mSelectedDriver = null;
        this.driverSelectLoading = true;
        this.mDriverReportService.getDriversFromSeason(this.mSelectedSeason).subscribe({
            next: (response) => {
                this.driverSelectLoading = false;
                this.populateDriverFilter(response);
            },
            error: (error) => {
                this.driverSelectLoading = false;
                this.mMessageService.addHttpError(error);
            },
        });
    }

    private populateDriverFilter(response: DriverReportDriverResponse[]) {
        this.driverOptions = response
            .sort((d1, d2) => d1.fullName.localeCompare(d2.fullName))
            .map((driver) => ({ displayValue: driver.fullName, value: driver.driverIdentifier }));
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
