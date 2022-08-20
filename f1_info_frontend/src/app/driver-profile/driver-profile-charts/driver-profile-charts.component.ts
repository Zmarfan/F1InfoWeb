import {Component, OnDestroy, OnInit} from '@angular/core';
import {ChartConfiguration} from 'chart.js';
import {TranslateService} from '@ngx-translate/core';
import {Subscription, tap} from 'rxjs';
import {ThemeService} from '../../theme.service';

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

    private mLanguageSubscription!: Subscription;
    private mThemeSubscription!: Subscription;

    public constructor(
        private mTranslate: TranslateService
    ) {
        setTimeout(() => this.refreshCharts());
    }

    public ngOnInit() {
        this.mLanguageSubscription = this.mTranslate.onLangChange.pipe(tap(() => this.refreshCharts())).subscribe();
        this.mThemeSubscription = ThemeService.onChange().pipe(tap((isDarkMode) => console.log(isDarkMode))).subscribe();
    }

    public ngOnDestroy() {
        this.mLanguageSubscription.unsubscribe();
        this.mThemeSubscription.unsubscribe();
    }

    private refreshCharts() {
        this.pointsPerSeasonOptions = this.createChartOptions('unexpectedError', 'test2', 'test3');
    }

    private createChartOptions(titleKey: string, xTextKey: string, yTextKey: string): ChartConfiguration<'line'>['options'] {
        return {
            plugins: {
                title: {
                    text: this.mTranslate.instant(titleKey),
                    display: true,
                },
            },
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                x: {
                    title: {
                        text: xTextKey,
                        display: true,
                    },
                    ticks: {
                        maxTicksLimit: 3,
                        autoSkip: true,
                    },
                },
                y: {
                    title: {
                        display: true,
                        text: yTextKey,
                    },
                },
            },
        };
    }
}
