import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserSettings} from './user-settings.component';
import {Observable} from 'rxjs';
import {Endpoints} from '../../../app/configuration/endpoints';

@Injectable({
    providedIn: 'root',
})
export class UserSettingsService {
    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public updateUserSettings(userSettings: UserSettings): Observable<Object> {
        return this.mHttpClient.post(Endpoints.AUTHENTICATION.getUser, userSettings);
    }
}
