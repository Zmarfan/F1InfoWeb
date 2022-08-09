import {Component, OnInit} from '@angular/core';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {DropDownFilterProvider} from '../reports/filters/drop-down-filter/drop-down-filter-provider';
import {RaceReportService} from './race-report.service';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {RaceOverviewRow, RaceReport, RaceReportData, RaceResultRow} from './race-report-data';
import {RaceData, RaceReportFilterResponse} from '../../generated/server-responses';
import {ReportSortConfig, SortDirection, SortSetting} from '../reports/report-element/report-element.component';
import {ReportHelperService} from '../reports/report-helper.service';
import {RaceType} from '../driver-report/driver-report-data';

@Component({
    selector: 'app-race-report',
    templateUrl: './race-report.component.html',
    styleUrls: ['./../reports/report-styling.scss'],
})
export class RaceReportComponent implements OnInit {
    public raceReportEnum = RaceReport;
    public reportData = RaceReportData;

    public reportType: RaceReport = RaceReport.OVERVIEW;

    public overviewRows: RaceOverviewRow[] = [];
    public raceResultRows: RaceResultRow[] = [];

    public seasonsOptions: DropdownOption[] = DropDownFilterProvider.createSeasonOptions();
    public raceOptions: DropdownOption[] = [];
    public raceCategoryOptions: DropdownOption[] = [];
    public raceTypeOptions: DropdownOption[] = DropDownFilterProvider.createRaceTypeOptions();

    public filterLoading: boolean = true;
    public loading: boolean = false;

    public overviewSortSetting: SortSetting = { columnName: 'date', direction: SortDirection.ASCENDING };
    public overviewSortConfig: ReportSortConfig = {
        sortCallback: (sortObject: SortSetting) => this.sort(sortObject),
        defaultSortSetting: this.overviewSortSetting,
    };
    public raceResultSortSetting: SortSetting = { columnName: 'position', direction: SortDirection.ASCENDING };
    public raceResultSortConfig: ReportSortConfig = {
        sortCallback: (sortObject: SortSetting) => this.sort(sortObject),
        defaultSortSetting: this.raceResultSortSetting,
    };

    private mSelectedSeason: number = new Date().getFullYear();
    private mSelectedRaceRound: number | null = null;
    private mSelectedRaceType: RaceType = RaceType.RACE;

    private mRacesWithSprints: RaceData[] = [];

    public constructor(
        private mReportHelper: ReportHelperService,
        private mRaceReportService: RaceReportService,
        private mMessageService: GlobalMessageService
    ) {
    }

    public get seasonHasSprints(): boolean {
        return this.mRacesWithSprints.length > 0;
    }

    public ngOnInit() {
        this.fetchAndAssignFilterValues();
    }

    public seasonFilterChanged = (newSeason: number) => {
        this.mSelectedSeason = newSeason;
        this.fetchAndAssignFilterValues();
    };

    public raceFilterChanged = (newRace: number | null) => {
        this.mSelectedRaceRound = newRace;
        this.reportType = this.mSelectedRaceRound === null ? RaceReport.OVERVIEW : RaceReport.RACE_RESULT;

        this.runReport();
    };

    public raceTypeFilterChanged = (newRaceType: RaceType) => {
        this.mSelectedRaceType = newRaceType;
        this.runReport();
    };

    public raceCategoryFilterChanged = (newRaceCategory: RaceReport) => {
        this.reportType = Number(newRaceCategory);
        this.runReport();
    };

    private fetchAndAssignFilterValues() {
        this.filterLoading = true;
        this.mRaceReportService.getFilterValues(this.mSelectedSeason).subscribe({
            next: (response) => {
                this.filterLoading = false;
                this.mRacesWithSprints = response.races.filter((race) => race.hasSprint);
                this.populateRaceFilter(response);
                this.populateRaceCategoryFilter();
                this.runReport();
            },
            error: (error) => {
                this.filterLoading = false;
                this.mMessageService.addHttpError(error);
            },
        });
    }

    private populateRaceFilter(response: RaceReportFilterResponse) {
        this.raceOptions = response.races
            .sort((r1, r2) => r1.round > r2.round ? 1 : 0)
            .map((race) => ({ displayValue: race.name, value: race.round }));
    }

    private populateRaceCategoryFilter() {
        const raceData: RaceData | undefined = this.mRacesWithSprints.find((race) => race.round === Number(this.mSelectedRaceRound));
        this.raceCategoryOptions = RaceReportData.getRaceCategoryOptions(raceData !== undefined);
    }

    private sort(sortSetting: SortSetting) {
        switch (this.reportType) {
        case RaceReport.OVERVIEW: this.overviewSortSetting = sortSetting; break;
        case RaceReport.RACE_RESULT: break;
        case RaceReport.FASTEST_LAPS: break;
        case RaceReport.PIT_STOPS: break;
        case RaceReport.STARTING_GRID: break;
        case RaceReport.SPRINT: break;
        case RaceReport.SPRINT_GRID: break;
        case RaceReport.PRACTICE_1: break;
        case RaceReport.PRACTICE_2: break;
        case RaceReport.PRACTICE_3: break;
        case RaceReport.QUALIFYING: break;
        }

        this.runReport();
    }

    private runReport() {
        switch (this.reportType) {
        case RaceReport.OVERVIEW: this.mReportHelper.runAllReport(
            (rows: RaceOverviewRow[]) => {
                this.overviewRows = rows;
            },
            this.loadingCallback,
            {
                season: this.mSelectedSeason,
                sortColumn: this.overviewSortSetting.columnName,
                sortDirection: this.overviewSortSetting.direction,
                raceType: this.seasonHasSprints ? this.mSelectedRaceType : RaceType.RACE,
            },
            (params) => this.mRaceReportService.getOverviewReport(params),
            (response) => RaceReportData.overviewToView(response)
        ); break;
        case RaceReport.RACE_RESULT: break;
        case RaceReport.FASTEST_LAPS: break;
        case RaceReport.PIT_STOPS: break;
        case RaceReport.STARTING_GRID: break;
        case RaceReport.SPRINT: break;
        case RaceReport.SPRINT_GRID: break;
        case RaceReport.PRACTICE_1: break;
        case RaceReport.PRACTICE_2: break;
        case RaceReport.PRACTICE_3: break;
        case RaceReport.QUALIFYING: break;
        }
    }

    private loadingCallback = (loading: boolean) => {
        this.loading = loading;
    };
}
