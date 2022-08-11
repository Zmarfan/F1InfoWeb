import {Component, OnInit} from '@angular/core';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {DropDownFilterProvider} from '../reports/filters/drop-down-filter/drop-down-filter-provider';
import {RaceReportService} from './race-report.service';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {
    FastestLapsRow,
    PitStopsRow,
    QualifyingRow,
    RaceOverviewRow,
    RaceReport,
    RaceReportData,
    RaceResultRow,
    RaceResultReportParameters,
    StartingGridRow,
} from './race-report-data';
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
    public fastestLapsRows: FastestLapsRow[] = [];
    public pitStopsRows: PitStopsRow[] = [];
    public startingGridRows: StartingGridRow[] = [];
    public qualifyingRows: QualifyingRow[] = [];

    public seasonsOptions: DropdownOption[] = DropDownFilterProvider.createSeasonOptions();
    public raceOptions: DropdownOption[] = [];
    public raceCategoryOptions: DropdownOption[] = [];
    public raceTypeOptions: DropdownOption[] = DropDownFilterProvider.createRaceTypeOptions();

    public filterLoading: boolean = true;
    public loading: boolean = false;

    public overviewSortSetting: SortSetting<RaceOverviewRow> = { columnName: 'date', direction: SortDirection.ASCENDING };
    public overviewSortConfig: ReportSortConfig<RaceOverviewRow> = {
        sortCallback: (sortObject: SortSetting<RaceOverviewRow>) => this.sort(sortObject),
        defaultSortSetting: this.overviewSortSetting,
    };
    public raceResultSortSetting: SortSetting<RaceResultRow> = { columnName: 'position', direction: SortDirection.ASCENDING };
    public raceResultSortConfig: ReportSortConfig<RaceResultRow> = {
        sortCallback: (sortObject: SortSetting<RaceResultRow>) => this.sort(sortObject),
        defaultSortSetting: this.raceResultSortSetting,
    };
    public fastestLapsSortSetting: SortSetting<FastestLapsRow> = { columnName: 'position', direction: SortDirection.ASCENDING };
    public fastestLapsSortConfig: ReportSortConfig<FastestLapsRow> = {
        sortCallback: (sortObject: SortSetting<FastestLapsRow>) => this.sort(sortObject),
        defaultSortSetting: this.fastestLapsSortSetting,
    };
    public pitStopsSortSetting: SortSetting<PitStopsRow> = { columnName: 'lap', direction: SortDirection.ASCENDING };
    public pitStopsSortConfig: ReportSortConfig<PitStopsRow> = {
        sortCallback: (sortObject: SortSetting<PitStopsRow>) => this.sort(sortObject),
        defaultSortSetting: this.pitStopsSortSetting,
    };
    public startingGridSortSetting: SortSetting<StartingGridRow> = { columnName: 'position', direction: SortDirection.ASCENDING };
    public startingGridSortConfig: ReportSortConfig<StartingGridRow> = {
        sortCallback: (sortObject: SortSetting<StartingGridRow>) => this.sort(sortObject),
        defaultSortSetting: this.startingGridSortSetting,
    };
    public qualifyingSortSetting: SortSetting<QualifyingRow> = { columnName: 'position', direction: SortDirection.ASCENDING };
    public qualifyingSortConfig: ReportSortConfig<QualifyingRow> = {
        sortCallback: (sortObject: SortSetting<QualifyingRow>) => this.sort(sortObject),
        defaultSortSetting: this.qualifyingSortSetting,
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
        this.reportType = RaceReport.OVERVIEW;
        this.fetchAndAssignFilterValues();
    };

    public raceFilterChanged = (newRace: number | null) => {
        this.mSelectedRaceRound = newRace;
        this.reportType = this.mSelectedRaceRound === null ? RaceReport.OVERVIEW : RaceReport.RACE_RESULT;
        this.populateRaceCategoryFilter();

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
        this.raceCategoryOptions = RaceReportData.getRaceCategoryOptions(raceData !== undefined, this.mSelectedSeason);
    }

    private sort(sortSetting: SortSetting<any>) {
        switch (this.reportType) {
        case RaceReport.OVERVIEW: this.overviewSortSetting = sortSetting; break;
        case RaceReport.SPRINT:
        case RaceReport.RACE_RESULT: this.raceResultSortSetting = sortSetting; break;
        case RaceReport.FASTEST_LAPS: this.fastestLapsSortSetting = sortSetting; break;
        case RaceReport.PIT_STOPS: this.pitStopsSortSetting = sortSetting; break;
        case RaceReport.QUALIFYING: this.qualifyingSortSetting = sortSetting; break;
        case RaceReport.SPRINT_GRID:
        case RaceReport.STARTING_GRID: this.startingGridSortSetting = sortSetting; break;
        }

        this.runReport();
    }

    private runReport() {
        switch (this.reportType) {
        case RaceReport.OVERVIEW: this.runOverviewReport(); break;
        case RaceReport.RACE_RESULT: this.runResultReport(RaceType.RACE); break;
        case RaceReport.FASTEST_LAPS: this.runFastestLapsReport(); break;
        case RaceReport.PIT_STOPS: break;
        case RaceReport.STARTING_GRID: break;
        case RaceReport.QUALIFYING: break;
        case RaceReport.SPRINT: this.runResultReport(RaceType.SPRINT); break;
        case RaceReport.SPRINT_GRID: break;
        }
    }

    private runOverviewReport() {
        this.mReportHelper.runReport(
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
        );
    }

    private runResultReport(raceType: RaceType) {
        this.mReportHelper.runReport(
            (rows: RaceResultRow[]) => {
                this.raceResultRows = rows;
            },
            this.loadingCallback,
            this.createSeasonRoundTypeParameters(raceType, this.raceResultSortSetting),
            (params) => this.mRaceReportService.getRaceResultReport(params),
            (response) => RaceReportData.raceResultToView(response)
        );
    }

    private runFastestLapsReport() {
        this.mReportHelper.runReport(
            (rows: FastestLapsRow[]) => {
                this.fastestLapsRows = rows;
            },
            this.loadingCallback,
            {
                season: this.mSelectedSeason,
                round: this.mSelectedRaceRound!,
                sortColumn: this.fastestLapsSortSetting.columnName,
                sortDirection: this.fastestLapsSortSetting.direction,
            },
            (params) => this.mRaceReportService.getFastestLapsReport(params),
            (response) => RaceReportData.fastestLapsToView(response)
        );
    }

    private loadingCallback = (loading: boolean) => {
        this.loading = loading;
    };

    private createSeasonRoundTypeParameters(raceType: RaceType, sortSettings: SortSetting<RaceResultRow>): RaceResultReportParameters {
        return {
            season: this.mSelectedSeason,
            round: this.mSelectedRaceRound!,
            type: raceType,
            sortColumn: sortSettings.columnName,
            sortDirection: sortSettings.direction,
        };
    }
}
