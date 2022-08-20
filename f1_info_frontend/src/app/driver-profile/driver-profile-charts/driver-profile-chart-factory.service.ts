import {ChartConfiguration, ChartDataset} from 'chart.js';
import {TranslateService} from '@ngx-translate/core';
import {Injectable} from '@angular/core';
import {DriverChartInfoResponse, StartPosition} from '../../../generated/server-responses';

interface ChartColorScheme {
    color: string;
    altColor: string;
}

@Injectable({
    providedIn: 'root',
})
export class DriverProfileChartFactoryService {
    private static readonly CHART_LINE_COLOR_THEMES: ChartColorScheme[] = [
        { color: '#b55851', altColor: '#d7a5a1' },
        { color: '#5179b5', altColor: '#a9c2da' },
        { color: '#67b551', altColor: '#b3d7a9' },
        { color: '#9c51b5', altColor: '#d0b6da' },
        { color: '#51a9b5', altColor: '#a5cbd3' },
        { color: '#b58a51', altColor: '#d5c5af' },
        { color: '#b2b551', altColor: '#c9c3a6' },
        { color: '#b5517b', altColor: '#dab0cd' },
    ];

    public constructor(
        private mTranslate: TranslateService
    ) {
    }

    private static getColorTheme(index: number): ChartColorScheme {
        return DriverProfileChartFactoryService.CHART_LINE_COLOR_THEMES[index % DriverProfileChartFactoryService.CHART_LINE_COLOR_THEMES.length];
    }

    private static createLineDataSetEntry(index: number, label: string, data: number[]): ChartDataset<'line'> {
        const colorScheme: ChartColorScheme = DriverProfileChartFactoryService.getColorTheme(index);
        return {
            label,
            data,
            borderColor: colorScheme.color,
            pointBackgroundColor: colorScheme.color,
            backgroundColor: colorScheme.altColor,
        };
    }

    public createPointsPerSeasonData(chartInfo: DriverChartInfoResponse): ChartConfiguration<'line'>['data'] {
        const years: string[] = Object.keys(chartInfo.pointsPerSeasons).sort();
        const datasets: ChartDataset<'line'>[] = [];
        let mostRoundsInAnySeason: number = 0;

        years.forEach((year, index) => {
            const pointsPerRound: number[] = chartInfo.pointsPerSeasons[year];
            mostRoundsInAnySeason = pointsPerRound.length > mostRoundsInAnySeason ? pointsPerRound.length : mostRoundsInAnySeason;
            datasets.push(DriverProfileChartFactoryService.createLineDataSetEntry(index, year, pointsPerRound));
        });

        const labels: string[] = [];
        for (let i = 1; i < mostRoundsInAnySeason; i++) {
            labels.push(i.toString());
        }

        return { labels, datasets };
    }

    public createStartData(chartInfo: DriverChartInfoResponse): ChartConfiguration<'bar'>['data'] {
        const positions: StartPosition[] = chartInfo.startPositions.sort((p1, p2) => p1.position < p2.position ? 1 : -1);

        return {
            labels: positions.map((position) => position.position === 0 ? 'Pit Lane' : position.position),
            datasets: [
                {
                    label: 'Test Label',
                    data: chartInfo.startPositions.map((position) => position.amount),
                },
            ],
        };
    }

    public createChartOptions(titleKey: string, xTextKey: string, yTextKey: string, textColor: string): ChartConfiguration<any>['options'] {
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
                        autoSkip: true,
                        color: textColor,
                    },
                },
                y: {
                    min: 0,
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
