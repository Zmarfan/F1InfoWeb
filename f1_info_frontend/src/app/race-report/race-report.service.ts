import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {parseTemplate} from 'url-template';
import {Endpoints} from '../configuration/endpoints';
import {FastestLapsReportResponse, OverviewRaceReportResponse, RaceReportFilterResponse, RaceResultReportResponse} from '../../generated/server-responses';
import {RaceReportParameters} from './race-report-data';

@Injectable({
    providedIn: 'root',
})
export class RaceReportService {
    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public getFilterValues(season: number) {
        return this.mHttpClient.get<RaceReportFilterResponse>(parseTemplate(Endpoints.REPORTS.getRaceReportFilterValues).expand({ season }));
    }

    public getOverviewReport(params: RaceReportParameters) {
        return this.mHttpClient.get<OverviewRaceReportResponse[]>(parseTemplate(Endpoints.REPORTS.getOverviewRaceReport).expand({ ...params }));
    }

    public getRaceResultReport(params: RaceReportParameters) {
        return this.mHttpClient.get<RaceResultReportResponse[]>(parseTemplate(Endpoints.REPORTS.getRaceResultReport).expand({ ...params }));
    }

    public getFastestLapsReport(params: RaceReportParameters) {
        return this.mHttpClient.get<FastestLapsReportResponse[]>(parseTemplate(Endpoints.REPORTS.getFastestLapsReport).expand({ ...params }));
    }
}
