import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {parseTemplate} from 'url-template';
import {Endpoints} from '../configuration/endpoints';
import {IndividualConstructorReportParameters, OverviewConstructorReportParameters} from './constructor-report-data';
import {ConstructorReportFilterResponse, IndividualConstructorReportResponse, OverviewConstructorReportResponse} from '../../generated/server-responses';

@Injectable({
    providedIn: 'root',
})
export class ConstructorReportService {
    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public getFilterValues(season: number) {
        return this.mHttpClient.get<ConstructorReportFilterResponse>(parseTemplate(Endpoints.REPORTS.getConstructorReportFilterValues).expand({ season }));
    }

    public getOverviewReport(params: OverviewConstructorReportParameters) {
        return this.mHttpClient.get<OverviewConstructorReportResponse[]>(parseTemplate(Endpoints.REPORTS.getOverviewConstructorReport).expand({ ...params }));
    }

    public getIndividualReport(params: IndividualConstructorReportParameters) {
        return this.mHttpClient.get<IndividualConstructorReportResponse[]>(
            parseTemplate(Endpoints.REPORTS.getIndividualConstructorReport).expand({ ...params })
        );
    }
}
