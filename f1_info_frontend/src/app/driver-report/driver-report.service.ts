import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoints} from '../configuration/endpoints';
import {parseTemplate} from 'url-template';

interface DriverReportDriverResponse {

}

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
}
