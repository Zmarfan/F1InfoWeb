import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {parseTemplate} from 'url-template';
import {Endpoints} from '../configuration/endpoints';

interface RaceReportFilterResponse {

}

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
}
