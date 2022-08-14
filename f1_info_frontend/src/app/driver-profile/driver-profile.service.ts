import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoints} from '../configuration/endpoints';
import {DriverEntry} from '../../generated/server-responses';

export interface DriverProfileFilterResponse {
    drivers: DriverEntry[];
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
}
