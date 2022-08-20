import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoints} from '../configuration/endpoints';
import {DriverChartInfoResponse, DriverProfileFilterResponse, DriverProfileResponse} from '../../generated/server-responses';
import {parseTemplate} from 'url-template';

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

    public getProfileInfo(driverIdentifier: string) {
        return this.mHttpClient.get<DriverProfileResponse>(parseTemplate(Endpoints.DRIVERS.getDriverProfile).expand({ driverIdentifier }));
    }

    public getChartInfo(driverIdentifier: string) {
        return this.mHttpClient.get<DriverChartInfoResponse>(parseTemplate(Endpoints.DRIVERS.getDriverChartInfo).expand({ driverIdentifier }));
    }
}
