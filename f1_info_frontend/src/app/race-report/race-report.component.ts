import {Component, OnInit} from '@angular/core';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {DropDownFilterProvider} from '../reports/filters/drop-down-filter/drop-down-filter-provider';
import {RaceReportService} from './race-report.service';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {FastestLapsRow, PitStopsRow, QualifyingRow, RaceOverviewRow, RaceReport, RaceReportData, RaceReportParameters, RaceResultRow} from './race-report-data';
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
    public pitStopsSortSetting: SortSetting<PitStopsRow> = { columnName: 'time', direction: SortDirection.ASCENDING };
    public pitStopsSortConfig: ReportSortConfig<PitStopsRow> = {
        sortCallback: (sortObject: SortSetting<PitStopsRow>) => this.sort(sortObject),
        defaultSortSetting: this.pitStopsSortSetting,
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
        this.populateRaceCategoryFilter();
        this.reportType = this.newReportTypeOnRaceChange();

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
        }

        this.runReport();
    }

    private runReport() {
        switch (this.reportType) {
        case RaceReport.OVERVIEW: this.runOverviewReport(); break;
        case RaceReport.RACE_RESULT: this.runResultReport(RaceType.RACE); break;
        case RaceReport.FASTEST_LAPS: this.runFastestLapsReport(); break;
        case RaceReport.PIT_STOPS: this.runPitStopsReport(); break;
        case RaceReport.QUALIFYING: this.runQualifyingReport(); break;
        case RaceReport.SPRINT: this.runResultReport(RaceType.SPRINT); break;
        }
    }

    private runOverviewReport() {
        this.mReportHelper.runReport(
            (rows: RaceOverviewRow[]) => {
                this.overviewRows = rows;
            },
            this.loadingCallback,
            this.createReportParameters(this.seasonHasSprints ? this.mSelectedRaceType : RaceType.RACE, this.overviewSortSetting),
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
            this.createReportParameters(raceType, this.raceResultSortSetting),
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
            this.createReportParameters(RaceType.RACE, this.fastestLapsSortSetting),
            (params) => this.mRaceReportService.getFastestLapsReport(params),
            (response) => RaceReportData.fastestLapsToView(response)
        );
    }

    private runPitStopsReport() {
        this.mReportHelper.runReport(
            (rows: PitStopsRow[]) => {
                this.pitStopsRows = rows;
            },
            this.loadingCallback,
            this.createReportParameters(RaceType.RACE, this.pitStopsSortSetting),
            (params) => this.mRaceReportService.getPitStopsReport(params),
            (response) => RaceReportData.pitStopsToView(response)
        );
    }

    private runQualifyingReport() {
        this.mReportHelper.runReport(
            (rows: QualifyingRow[]) => {
                this.qualifyingRows = rows;
            },
            this.loadingCallback,
            this.createReportParameters(RaceType.RACE, this.qualifyingSortSetting),
            (params) => this.mRaceReportService.getQualifyingReport(params),
            (response) => RaceReportData.qualifyingToView(response)
        );
    }

    private loadingCallback = (loading: boolean) => {
        this.loading = loading;
    };

    private createReportParameters(raceType: RaceType, sortSettings: SortSetting<any>): RaceReportParameters {
        return {
            season: this.mSelectedSeason,
            round: this.mSelectedRaceRound!,
            type: raceType,
            sortColumn: sortSettings.columnName as any,
            sortDirection: sortSettings.direction,
        };
    }

    private newReportTypeOnRaceChange(): RaceReport {
        if (this.mSelectedRaceRound === null) {
            return RaceReport.OVERVIEW;
        }

        if (this.raceCategoryOptions.filter((category) => category.value === this.reportType).length > 0) {
            return this.reportType;
        }
        return RaceReport.RACE_RESULT;
    }
}
