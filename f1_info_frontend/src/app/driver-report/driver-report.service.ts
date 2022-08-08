import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoints} from '../configuration/endpoints';
import {parseTemplate} from 'url-template';
import {AllDriverReportResponse, DriverReportDriverResponse, IndividualDriverReportResponse} from '../../generated/server-responses';
import {AllDriverReportParameters, IndividualDriverReportParameters} from './driver-report.component';

@Injectable({
    providedIn: 'root',
})
export class DriverReportService {
    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public getDriversFromSeason(season: number) {
        return this.mHttpClient.get<DriverReportDriverResponse[]>(parseTemplate(Endpoints.REPORTS.driversFromSeason).expand({ season }));
    }

    public getAllReport(params: AllDriverReportParameters) {
        return this.mHttpClient.get<AllDriverReportResponse[]>(parseTemplate(Endpoints.REPORTS.getAllDriverReport).expand({ ...params }));
    }

    public getIndividualReport(params: IndividualDriverReportParameters) {
        return this.mHttpClient.get<IndividualDriverReportResponse[]>(parseTemplate(Endpoints.REPORTS.getIndividualDriverReport).expand({ ...params }));
    }
}
