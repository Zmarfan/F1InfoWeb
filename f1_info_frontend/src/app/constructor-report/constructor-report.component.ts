import {Component, OnInit} from '@angular/core';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {DropDownFilterProvider, RaceType} from '../reports/filters/drop-down-filter/drop-down-filter-provider';
import {ReportColumn} from '../reports/report-element/report-column';
import {ConstructorReportData, IndividualConstructorRow, OverviewConstructorRow} from './constructor-report-data';
import {ReportSortConfig, SortDirection, SortSetting} from '../reports/report-element/report-element.component';
import {ReportHelperService} from '../reports/report-helper.service';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {ConstructorReportService} from './constructor-report.service';
import {ConstructorReportFilterResponse} from '../../generated/server-responses';

@Component({
    selector: 'app-constructor-report',
    templateUrl: './constructor-report.component.html',
    styleUrls: ['./../reports/report-styling.scss'],
})
export class ConstructorReportComponent implements OnInit {
    public seasonsOptions: DropdownOption[] = DropDownFilterProvider.createSeasonOptions(DropDownFilterProvider.FIRST_CONSTRUCTOR_STANDINGS_F1_SEASON);
    public raceTypeOptions: DropdownOption[] = DropDownFilterProvider.createRaceTypeOptions();
    public constructorOptions: DropdownOption[] = [];
    public racesOptions: DropdownOption[] = [];
    public filterLoading: boolean = true;

    public overviewReportColumns: ReportColumn<OverviewConstructorRow>[] = ConstructorReportData.overviewReportColumns;
    public individualReportColumns: ReportColumn<IndividualConstructorRow>[] = ConstructorReportData.individualReportColumns;

    public overviewRows: OverviewConstructorRow[] = [];
    public individualRows: IndividualConstructorRow[] = [];

    public loading: boolean = false;
    public selectedRound: number = 1;

    public overviewSortSetting: SortSetting<OverviewConstructorRow> = { columnName: 'position', direction: SortDirection.ASCENDING };
    public overviewSortConfig: ReportSortConfig<OverviewConstructorRow> = {
        sortCallback: (sortObject: SortSetting<OverviewConstructorRow>) => this.sort(sortObject),
        defaultSortSetting: this.overviewSortSetting,
    };
    public individualSortSetting: SortSetting<IndividualConstructorRow> = { columnName: 'date', direction: SortDirection.DESCENDING };
    public individualSortConfig: ReportSortConfig<IndividualConstructorRow> = {
        sortCallback: (sortObject: SortSetting<IndividualConstructorRow>) => this.sort(sortObject),
        defaultSortSetting: this.individualSortSetting,
    };

    private mSelectedSeason: number = new Date().getFullYear();
    private mSelectedRaceType: RaceType = RaceType.RACE;
    private mSelectedConstructor: string | null = null;
    private mCurrentSeasonMaxRound: number = 1;
    private mSeasonHasSprints: boolean = false;

    public constructor(
        private mReportHelper: ReportHelperService,
        private mConstructorReportService: ConstructorReportService,
        private mMessageService: GlobalMessageService
    ) {
    }

    public get showOverviewReport(): boolean {
        return this.mSelectedConstructor === null;
    }

    public get showRaceTypeFilter(): boolean {
        return !this.showOverviewReport && this.mSeasonHasSprints;
    }

    public ngOnInit() {
        this.fetchAndAssignFilterValues();
    }

    public sort(sortSetting: SortSetting<any>) {
        if (this.showOverviewReport) {
            this.overviewSortSetting = sortSetting;
        } else {
            this.individualSortSetting = sortSetting;
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

    public constructorFilterChanged = (newConstructor: string | null) => {
        this.mSelectedConstructor = newConstructor;
        this.selectedRound = this.mCurrentSeasonMaxRound;
        this.runReport();
    };

    public roundFilterChanged = (newRound: number) => {
        this.selectedRound = newRound;
        this.runReport();
    };

    private fetchAndAssignFilterValues() {
        this.mSelectedConstructor = null;
        this.filterLoading = true;
        this.mConstructorReportService.getFilterValues(this.mSelectedSeason).subscribe({
            next: (response) => {
                this.filterLoading = false;
                this.mSeasonHasSprints = response.seasonHasSprints;
                this.populateConstructorFilter(response);
                this.populateRacesFilter(response);
                this.runReport();
            },
            error: (error) => {
                this.filterLoading = false;
                this.mMessageService.addHttpError(error);
            },
        });
    }

    private populateConstructorFilter(response: ConstructorReportFilterResponse) {
        this.constructorOptions = response.constructors
            .sort((c1, c2) => c1.name.localeCompare(c2.name))
            .map((constructor) => ({ displayValue: constructor.name, value: constructor.constructorIdentifier }));
    }

    private populateRacesFilter(response: ConstructorReportFilterResponse) {
        this.mCurrentSeasonMaxRound = response.races.length;
        this.selectedRound = this.mCurrentSeasonMaxRound;

        this.racesOptions = response.races.map((race) => ({ displayValue: race.name, value: race.round }));
    }

    private runReport() {
        if (this.showOverviewReport) {
            this.mReportHelper.runReport(
                (rows: OverviewConstructorRow[]) => {
                    this.overviewRows = rows;
                },
                this.loadingCallback,
                {
                    season: this.mSelectedSeason,
                    round: this.selectedRound,
                    sortColumn: this.overviewSortSetting.columnName,
                    sortDirection: this.overviewSortSetting.direction,
                },
                (params) => this.mConstructorReportService.getOverviewReport(params),
                (response) => ConstructorReportData.overviewToView(response)
            );
        } else {
            this.mReportHelper.runReport(
                (rows: IndividualConstructorRow[]) => {
                    this.individualRows = rows;
                },
                this.loadingCallback,
                {
                    season: this.mSelectedSeason,
                    constructorIdentifier: this.mSelectedConstructor ?? '',
                    raceType: this.mSeasonHasSprints ? this.mSelectedRaceType : RaceType.RACE,
                    sortColumn: this.individualSortSetting.columnName,
                    sortDirection: this.individualSortSetting.direction,
                },
                (params) => this.mConstructorReportService.getIndividualReport(params),
                (response) => ConstructorReportData.individualToView(response)
            );
        }
    }

    private loadingCallback = (loading: boolean) => {
        this.loading = loading;
    };
}
