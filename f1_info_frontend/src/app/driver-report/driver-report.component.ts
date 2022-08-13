import {Component, OnInit} from '@angular/core';
import {ReportColumn} from '../reports/report-element/report-column';
import {ReportSortConfig, SortDirection, SortSetting} from '../reports/report-element/report-element.component';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {DropDownFilterProvider, RaceType} from '../reports/filters/drop-down-filter/drop-down-filter-provider';
import {DriverReportService} from './driver-report.service';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {DriverReportFilterResponse} from '../../generated/server-responses';
import {AllDriverRow, DriverReportData, IndividualDriverRow} from './driver-report-data';
import {ReportHelperService} from '../reports/report-helper.service';

@Component({
    selector: 'app-driver-report',
    templateUrl: './driver-report.component.html',
    styleUrls: ['./../reports/report-styling.scss'],
})
export class DriverReportComponent implements OnInit {
    public seasonsOptions: DropdownOption[] = DropDownFilterProvider.createSeasonOptions(DropDownFilterProvider.FIRST_F1_SEASON);
    public raceTypeOptions: DropdownOption[] = DropDownFilterProvider.createRaceTypeOptions();
    public driverOptions: DropdownOption[] = [];
    public racesOptions: DropdownOption[] = [];
    public filterLoading: boolean = true;

    public allReportColumns: ReportColumn<AllDriverRow>[] = DriverReportData.allReportColumns;
    public driverReportColumns: ReportColumn<IndividualDriverRow>[] = DriverReportData.driverReportColumns;

    public allRows: AllDriverRow[] = [];
    public individualRows: IndividualDriverRow[] = [];

    public loading: boolean = false;
    public selectedRound: number = 1;

    private mSelectedSeason: number = new Date().getFullYear();
    private mSelectedRaceType: RaceType = RaceType.RACE;
    private mSelectedDriver: string | null = null;

    private mAllSortSetting: SortSetting<AllDriverRow> = { columnName: 'position', direction: SortDirection.ASCENDING };
    private mDriverSortSetting: SortSetting<IndividualDriverRow> = { columnName: 'date', direction: SortDirection.DESCENDING };
    private mAllSortConfig: ReportSortConfig<AllDriverRow> = {
        sortCallback: (sortObject: SortSetting<AllDriverRow>) => this.sort(sortObject),
        defaultSortSetting: this.mAllSortSetting,
    };
    private mDriverSortConfig: ReportSortConfig<IndividualDriverRow> = {
        sortCallback: (sortObject: SortSetting<IndividualDriverRow>) => this.sort(sortObject),
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

    public get allSortConfig(): ReportSortConfig<AllDriverRow> {
        return this.mAllSortConfig;
    }

    public get driverSortConfig(): ReportSortConfig<IndividualDriverRow> {
        return this.mDriverSortConfig;
    }

    public get showRaceTypeFilter(): boolean {
        return !this.showAllReport && this.mSeasonHasSprints;
    }

    public ngOnInit() {
        this.fetchAndAssignFilterValues();
    }

    public sort(sortSetting: SortSetting<any>) {
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
                this.populateRacesFilter(response);
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

    private populateRacesFilter(response: DriverReportFilterResponse) {
        this.mCurrentSeasonMaxRound = response.races.length;
        this.selectedRound = this.mCurrentSeasonMaxRound;

        this.racesOptions = response.races.map((race) => ({ displayValue: race.name, value: race.round }));
    }

    private runReport() {
        if (this.showAllReport) {
            this.mReportHelper.runReport(
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
            this.mReportHelper.runReport(
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
