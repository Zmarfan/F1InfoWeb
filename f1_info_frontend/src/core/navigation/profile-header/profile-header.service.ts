import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BellNotificationResponse} from '../../../generated/server-responses';
import {Endpoints} from '../../../app/configuration/endpoints';

@Injectable({
    providedIn: 'root',
})
export class ProfileHeaderService {

    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public getBellNotificationsToDisplay() {
        return this.mHttpClient.get<BellNotificationResponse[]>(Endpoints.USER.getBellNotificationsToDisplay);
    }

    public markBellNotificationsAsOpened() {
        return this.mHttpClient.put<void>(Endpoints.USER.markBellNotificationsAsOpened, {});
    }
}
