import {Component, OnDestroy, OnInit} from '@angular/core';
import {ChartConfiguration} from 'chart.js';
import {TranslateService} from '@ngx-translate/core';
import {Subscription, tap} from 'rxjs';
import {ThemeService} from '../../theme.service';
import {DriverProfileChartFactoryService} from './driver-profile-chart-factory.service';

@Component({
    selector: 'app-driver-profile-charts',
    templateUrl: './driver-profile-charts.component.html',
    styleUrls: ['./driver-profile-charts.component.scss'],
})
export class DriverProfileChartsComponent implements OnInit, OnDestroy {
    public pointsPerSeason: ChartConfiguration<'line'>['data'] = {
        labels: ['Race1', 'Race2', 'Race3', 'Race4', 'Race5', 'Race6'],
        datasets: [
            {
                label: '2020',
                data: [0, 12, 20, 20, 21, 30],
            },
            {
                label: '2021',
                data: [0, 22, 15, 26, 10, 5],
            },
            {
                label: '2022',
                data: [10, 25, 10, 1, 4, 20],
            },
            {
                label: '2023',
                data: [0, 12, 14, 15, 15, 15],
            },
        ],
    };

    public pointsPerSeasonOptions: ChartConfiguration<'line'>['options'];

    private mIsDarkMode: boolean = false;
    private mLanguageSubscription!: Subscription;
    private mThemeSubscription!: Subscription;

    public constructor(
        private mTranslate: TranslateService,
        private mChartFactory: DriverProfileChartFactoryService
    ) {
        setTimeout(() => this.refreshCharts());
    }

    public ngOnInit() {
        this.mLanguageSubscription = this.mTranslate.onLangChange.pipe(tap(() => this.refreshCharts())).subscribe();
        this.mThemeSubscription = ThemeService.onChange().subscribe({
            next: (isDarkMode) => {
                this.mIsDarkMode = isDarkMode;
                this.refreshCharts();
            },
        });
    }

    public ngOnDestroy() {
        this.mLanguageSubscription.unsubscribe();
        this.mThemeSubscription.unsubscribe();
    }

    private refreshCharts() {
        this.pointsPerSeasonOptions = this.mChartFactory.createChartOptions(
            'driverProfile.charts.seasonPoints.title',
            'driverProfile.charts.seasonPoints.xTitle',
            'driverProfile.charts.seasonPoints.yTitle',
            this.mIsDarkMode ? '#dadada' : '#080808'
        );
    }
}
