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

    private static createDataSetEntry(index: number, label: string, data: number[], hidden: boolean): ChartDataset<any> {
        const colorScheme: ChartColorScheme = DriverProfileChartFactoryService.getColorTheme(index);
        return {
            hidden,
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
            datasets.push(DriverProfileChartFactoryService.createDataSetEntry(index, year, pointsPerRound, index !== years.length - 1));
        });

        const labels: string[] = [];
        for (let i = 1; i < mostRoundsInAnySeason; i++) {
            labels.push(i.toString());
        }

        return { labels, datasets };
    }

    public createStartData(chartInfo: DriverChartInfoResponse): ChartConfiguration<'bar'>['data'] {
        const years: string[] = Object.keys(chartInfo.startPositionsPerSeason).sort();
        const worstStartPosition: number = this.findWorstStartPositionsAcrossAllSeasons(years, chartInfo);
        const datasets: ChartDataset<'bar'>[] = this.calculateDatasetsForStartingPositions(years, worstStartPosition, chartInfo);
        const labels: string[] = this.calculateStartPositionLabels(worstStartPosition);

        return { labels, datasets };
    }

    public createChartOptions(titleKey: string, xTextKey: string, yTextKey: string, textColor: string, yStepSize: number): ChartConfiguration<any>['options'] {
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
                        stepSize: yStepSize,
                        color: textColor,
                    },
                },
            },
        };
    }

    private findWorstStartPositionsAcrossAllSeasons(years: string[], chartInfo: DriverChartInfoResponse): number {
        let worstQualifyingPosition: number = 0;

        years.forEach((year) => {
            const startPositions: StartPosition[] = this.getSortedPositionsForSeason(year, chartInfo);
            worstQualifyingPosition = startPositions[0].position > worstQualifyingPosition ? startPositions[0].position : worstQualifyingPosition;
        });
        return worstQualifyingPosition;
    }

    private getSortedPositionsForSeason(year: string, chartInfo: DriverChartInfoResponse): StartPosition[] {
        return chartInfo.startPositionsPerSeason[year].sort((p1, p2) => p1.position < p2.position ? 1 : -1);
    }

    private calculateDatasetsForStartingPositions(years: string[], worstStartPosition: number, chartInfo: DriverChartInfoResponse) {
        const datasets: ChartDataset<'bar'>[] = [];

        years.forEach((year, index) => {
            const startPositions: StartPosition[] = this.getSortedPositionsForSeason(year, chartInfo);

            const positionAmounts: number[] = [];
            for (let i = worstStartPosition; i >= 0; i--) {
                positionAmounts.push(startPositions.find((position) => position.position === i)?.amount ?? 0);
            }

            datasets.push(DriverProfileChartFactoryService.createDataSetEntry(index, year, positionAmounts, index !== years.length - 1));
        });

        return datasets;
    }

    private calculateStartPositionLabels(worstStartPosition: number) {
        const labels: string[] = [];
        for (let i = worstStartPosition; i > 0; i--) {
            labels.push(i.toString());
        }
        labels.push('Pit Lane');
        return labels;
    }
}
