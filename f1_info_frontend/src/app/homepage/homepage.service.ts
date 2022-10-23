import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {NextRaceInfoResponse} from '../../generated/server-responses';
import {Endpoints} from '../configuration/endpoints';

@Injectable({
    providedIn: 'root',
})
export class HomepageService {
    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public getNextRaceInfo() {
        return this.mHttpClient.get<NextRaceInfoResponse>(Endpoints.HOMEPAGE.nextRaceInfo);
    }
}
