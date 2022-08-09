import {Component} from '@angular/core';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {DropDownFilterProvider} from '../reports/filters/drop-down-filter/drop-down-filter-provider';
import {RaceReportService} from './race-report.service';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {RaceType} from '../driver-report/driver-report-data';
import {RaceReport} from './race-report-data';

@Component({
    selector: 'app-race-report',
    templateUrl: './race-report.component.html',
    styleUrls: ['./../reports/report-styling.scss'],
})
export class RaceReportComponent {
    public seasonsOptions: DropdownOption[] = DropDownFilterProvider.createSeasonOptions();
    public filterLoading: boolean = true;

    private mSelectedSeason: number = new Date().getFullYear();
    private mReportType: RaceReport = RaceReport.OVERVIEW;

    public constructor(
        private mRaceReportService: RaceReportService,
        private mMessageService: GlobalMessageService
    ) {
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
        //switch (this.mReportType) {
        //case RaceReport.OVERVIEW: this.runOverviewReport(); break;
        //case RaceReport.RACE_RESULT: this.runRaceResulTReport(); break;
        //case RaceReport.FASTEST_LAPS: this.runFastestLapsReport(); break;
        //case RaceReport.PIT_STOPS: this.runPitStopSReport(); break;
        //case RaceReport.STARTING_GRID: this.runStartingGridReport(); break;
        //case RaceReport.SPRINT: this.runSprintReport(); break;
        //case RaceReport.SPRINT_GRID: this.runSprintGridReport(); break;
        //case RaceReport.PRACTICE_1: this.runPractice1Report(); break;
        //case RaceReport.PRACTICE_2: this.runPractice2Report(); break;
        //case RaceReport.PRACTICE_3: this.runPractice3Report(); break;
        //case RaceReport.QUALIFYING: this.runQualifyingReport(); break;
        //}
    }
}
