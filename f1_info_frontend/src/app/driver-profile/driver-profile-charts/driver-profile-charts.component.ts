import {Component, Input, OnChanges, OnDestroy, OnInit} from '@angular/core';
import {ChartConfiguration} from 'chart.js';
import {TranslateService} from '@ngx-translate/core';
import {Subscription, tap} from 'rxjs';
import {ThemeService} from '../../theme.service';
import {DriverProfileChartFactoryService} from './driver-profile-chart-factory.service';
import {DriverChartInfoResponse} from '../../../generated/server-responses';

@Component({
    selector: 'app-driver-profile-charts',
    templateUrl: './driver-profile-charts.component.html',
    styleUrls: ['./driver-profile-charts.component.scss'],
})
export class DriverProfileChartsComponent implements OnInit, OnDestroy, OnChanges {
    @Input() public chartInfo!: DriverChartInfoResponse;

    public pointsPerSeasonData!: ChartConfiguration<'line'>['data'];
    public pointsPerSeasonOptions: ChartConfiguration<'line'>['options'];

    public startData!: ChartConfiguration<'bar'>['data'];
    public startOptions: ChartConfiguration<'bar'>['options'];

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

    public ngOnChanges() {
        this.refreshCharts();
    }

    private refreshCharts() {
        const textColor: string = this.mIsDarkMode ? '#dadada' : '#080808';

        this.pointsPerSeasonData = this.mChartFactory.createPointsPerSeasonData(this.chartInfo);
        this.pointsPerSeasonOptions = this.mChartFactory.createChartOptions(
            'driverProfile.charts.seasonPoints.title',
            'driverProfile.charts.seasonPoints.xTitle',
            'driverProfile.charts.seasonPoints.yTitle',
            textColor
        );

        this.startData = this.mChartFactory.createStartData(this.chartInfo);
        this.startOptions = this.mChartFactory.createChartOptions(
            'driverProfile.charts.qualifying.title',
            'driverProfile.charts.qualifying.xTitle',
            'driverProfile.charts.qualifying.yTitle',
            textColor
        );
    }
}
