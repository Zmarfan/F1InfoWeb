import {ChartConfiguration} from 'chart.js';
import {TranslateService} from '@ngx-translate/core';
import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class DriverProfileChartFactoryService {
    public constructor(
        private mTranslate: TranslateService
    ) {
    }

    public createChartData(): ChartConfiguration<'line'>['data'] {
        return {
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
    }

    public createChartOptions(titleKey: string, xTextKey: string, yTextKey: string, textColor: string): ChartConfiguration<'line'>['options'] {
        return {
            color: textColor,
            plugins: {
                title: {
                    text: this.mTranslate.instant(titleKey),
                    display: true,
                    color: textColor,
                },
            },
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                x: {
                    title: {
                        text: this.mTranslate.instant(xTextKey),
                        display: true,
                        color: textColor,
                    },
                    ticks: {
                        maxTicksLimit: 3,
                        autoSkip: true,
                        color: textColor,
                    },
                },
                y: {
                    title: {
                        display: true,
                        text: this.mTranslate.instant(yTextKey),
                        color: textColor,
                    },
                    ticks: {
                        color: textColor,
                    },
                },
            },
        };
    }
}
