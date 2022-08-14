import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoints} from '../configuration/endpoints';
import {DriverProfileFilterResponse} from '../../generated/server-responses';
import {Observable, of} from 'rxjs';

export interface DriverProfileResponse {
    wikipediaTitle: string;
}

@Injectable({
    providedIn: 'root',
})
export class DriverProfileService {
    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public getAllDrivers() {
        return this.mHttpClient.get<DriverProfileFilterResponse>(Endpoints.DRIVERS.getAllDrivers);
    }

    public getProfileInfo(): Observable<DriverProfileResponse> {
        return of({
            wikipediaTitle: 'Alex_Albon',
        });
    }
}
