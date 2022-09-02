import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoints} from '../../configuration/endpoints';
import {ChangeLogResponse} from '../../../generated/server-responses';

@Injectable({
    providedIn: 'root',
})
export class ChangeLogService {
    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public getChangeLogItems() {
        return this.mHttpClient.get<ChangeLogResponse>(Endpoints.OPEN_DEVELOPMENT.getChangeLogItems);
    }
}
