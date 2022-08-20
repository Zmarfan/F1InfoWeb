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
                    }
                },
            },
        };
    }
}
