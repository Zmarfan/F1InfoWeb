import {Component, OnInit} from '@angular/core';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {DropDownFilterProvider} from '../reports/filters/drop-down-filter/drop-down-filter-provider';
import {RaceReportService} from './race-report.service';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {RaceReport} from './race-report-data';

@Component({
    selector: 'app-race-report',
    templateUrl: './race-report.component.html',
    styleUrls: ['./../reports/report-styling.scss'],
})
export class RaceReportComponent implements OnInit {
    public seasonsOptions: DropdownOption[] = DropDownFilterProvider.createSeasonOptions();
    public filterLoading: boolean = true;
    public loading: boolean = true;

    private mSelectedSeason: number = new Date().getFullYear();
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

    private fetchAndAssignFilterValues() {
        this.filterLoading = true;
        this.mRaceReportService.getFilterValues(this.mSelectedSeason).subscribe({
            next: (response) => {
                this.filterLoading = false;
                this.runReport();
            },
            error: (error) => {
                this.filterLoading = false;
                this.mMessageService.addHttpError(error);
            },
        });
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
