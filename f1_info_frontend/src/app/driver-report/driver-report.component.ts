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
    IndividualDriverReportResponse
} from '../../generated/server-responses';
import {CountryEntry} from '../reports/entry/country-entry/country-entry';
import {AllDriverReportParameters, AllDriverRow, DriverReportData, IndividualDriverReportParameters, IndividualDriverRow, RaceType} from './driver-report-data';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
    selector: 'app-driver-report',
    templateUrl: './driver-report.component.html',
    styleUrls: ['./driver-report.component.scss'],
})
export class DriverReportComponent implements OnInit {
    public seasonsOptions: DropdownOption[] = DropDownFilterProvider.createSeasonOptions();
    public raceTypeOptions: DropdownOption[] = DriverReportData.raceTypeOptions;
    public driverOptions: DropdownOption[] = [];
    public roundOptions: DropdownOption[] = [];
    public filterLoading: boolean = true;

    public allReportColumns: ReportColumn[] = DriverReportData.allReportColumns;
    public driverReportColumns: ReportColumn[] = DriverReportData.driverReportColumns;

    public allRows: AllDriverRow[] = [];
    public individualRows: IndividualDriverRow[] = [];

    public loading: boolean = false;
    public selectedRound: number = 1;

    private mSelectedSeason: number = new Date().getFullYear();
    private mSelectedRaceType: RaceType = RaceType.RACE;
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
    private mCurrentSeasonMaxRound: number = 1;
    private mSeasonHasSprints: boolean = false;

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

    public get showRaceTypeFilter(): boolean {
        return !this.showAllReport && this.mSeasonHasSprints;
    }

    public ngOnInit() {
        this.fetchAndAssignFilterValues();
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
    };

    public raceTypeFilterChanged = (newRaceType: RaceType) => {
        this.mSelectedRaceType = newRaceType;
        this.runReport();
    };

    public driverFilterChanged = (newDriver: string | null) => {
        this.mSelectedDriver = newDriver;
        this.selectedRound = this.mCurrentSeasonMaxRound;
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
                this.mSeasonHasSprints = response.seasonHasSprints;
                this.populateDriverFilter(response);
                this.populateRoundFilter(response);
                this.runReport();
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
        this.mCurrentSeasonMaxRound = response.amountOfRounds;

        const options: DropdownOption[] = [];
        for (let round = 1; round <= response.amountOfRounds; round++) {
            options.push({ displayValue: 'reports.driver.roundEntry', value: round, translateParams: { round } });
        }
        this.roundOptions = options;
        this.selectedRound = response.amountOfRounds;
    }

    private runReport() {
        if (this.showAllReport) {
            this.runAllReport({
                season: this.mSelectedSeason,
                round: this.selectedRound,
                sortColumn: this.mAllSortSetting.columnName,
                sortDirection: this.mAllSortSetting.direction,
            });
        } else {
            this.runDriverReport({
                season: this.mSelectedSeason,
                driverIdentifier: this.mSelectedDriver ?? '',
                raceType: this.mSeasonHasSprints ? this.mSelectedRaceType : RaceType.RACE,
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
                this.allRows = responses.map((response) => DriverReportData.allToView(response));
            },
            error: this.handleReportFetchingError,
        });
    }

    private runDriverReport(params: IndividualDriverReportParameters) {
        this.loading = true;
        this.mDriverReportService.getIndividualReport(params).subscribe({
            next: (responses) => {
                this.loading = false;
                this.individualRows = responses.map((response) => DriverReportData.individualToView(response));
            },
            error: this.handleReportFetchingError,
        });
    }

    private handleReportFetchingError(error: HttpErrorResponse) {
        this.loading = false;
        this.mMessageService.addHttpError(error);
    }
}
