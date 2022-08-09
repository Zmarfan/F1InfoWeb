import {Component, OnInit} from '@angular/core';
import {ReportColumn} from '../reports/report-element/report-column';
import {ReportSortConfig, SortDirection, SortSetting} from '../reports/report-element/report-element.component';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {DropDownFilterProvider} from '../reports/filters/drop-down-filter/drop-down-filter-provider';
import {DriverReportService} from './driver-report.service';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {DriverReportFilterResponse} from '../../generated/server-responses';
import {AllDriverReportParameters, AllDriverRow, DriverReportData, IndividualDriverReportParameters, IndividualDriverRow, RaceType} from './driver-report-data';
import {HttpErrorResponse} from '@angular/common/http';
import {ReportHelperService} from '../reports/report-helper.service';

@Component({
    selector: 'app-driver-report',
    templateUrl: './driver-report.component.html',
    styleUrls: ['./../reports/report-styling.scss'],
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
        private mReportHelper: ReportHelperService,
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
            this.mReportHelper.runAllReport(
                (rows: AllDriverRow[]) => {
                    this.allRows = rows;
                },
                this.loadingCallback,
                {
                    season: this.mSelectedSeason,
                    round: this.selectedRound,
                    sortColumn: this.mAllSortSetting.columnName,
                    sortDirection: this.mAllSortSetting.direction,
                },
                (params) => this.mDriverReportService.getAllReport(params),
                (response) => DriverReportData.allToView(response)
            );
        } else {
            this.mReportHelper.runAllReport(
                (rows: IndividualDriverRow[]) => {
                    this.individualRows = rows;
                },
                this.loadingCallback,
                {
                    season: this.mSelectedSeason,
                    driverIdentifier: this.mSelectedDriver ?? '',
                    raceType: this.mSeasonHasSprints ? this.mSelectedRaceType : RaceType.RACE,
                    sortColumn: this.mDriverSortSetting.columnName,
                    sortDirection: this.mDriverSortSetting.direction,
                },
                (params) => this.mDriverReportService.getIndividualReport(params),
                (response) => DriverReportData.individualToView(response)
            );
        }
    }

    private loadingCallback = (loading: boolean) => {
        this.loading = loading;
    };
}
