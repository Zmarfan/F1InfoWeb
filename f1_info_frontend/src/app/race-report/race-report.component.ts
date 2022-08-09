import {Component, OnInit} from '@angular/core';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {DropDownFilterProvider} from '../reports/filters/drop-down-filter/drop-down-filter-provider';
import {RaceReportService} from './race-report.service';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {RaceReport} from './race-report-data';
import {RaceReportFilterResponse} from '../../generated/server-responses';

@Component({
    selector: 'app-race-report',
    templateUrl: './race-report.component.html',
    styleUrls: ['./../reports/report-styling.scss'],
})
export class RaceReportComponent implements OnInit {
    public seasonsOptions: DropdownOption[] = DropDownFilterProvider.createSeasonOptions();
    public raceOptions: DropdownOption[] = [];
    public filterLoading: boolean = true;
    public loading: boolean = true;

    private mSelectedSeason: number = new Date().getFullYear();
    private mSelectedRaceRound: number | null = null;
    private mReportType: RaceReport = RaceReport.OVERVIEW;

    public constructor(
        private mRaceReportService: RaceReportService,
        private mMessageService: GlobalMessageService
    ) {
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
        this.runReport();
    };

    private fetchAndAssignFilterValues() {
        this.filterLoading = true;
        this.mRaceReportService.getFilterValues(this.mSelectedSeason).subscribe({
            next: (response) => {
                this.filterLoading = false;
                this.populateRaceFilter(response);
                this.runReport();
            },
            error: (error) => {
                this.filterLoading = false;
                this.mMessageService.addHttpError(error);
            },
        });
    }

    private populateRaceFilter(response: RaceReportFilterResponse) {
        this.raceOptions = response.circuits
            .sort((c1, c2) => c1.round > c2.round ? 1 : 0)
            .map((circuit) => ({ displayValue: circuit.name, value: circuit.round }));
    }

    private runReport() {
        switch (this.mReportType) {
        case RaceReport.OVERVIEW: break;
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
