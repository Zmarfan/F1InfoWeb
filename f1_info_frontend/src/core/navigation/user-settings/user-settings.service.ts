import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {forkJoin, Observable} from 'rxjs';
import {Endpoints} from '../../../app/configuration/endpoints';

export interface UpdateUserSettingsData {
    shouldUpdateDisplayName: boolean;
    displayName: string;
    icon: File | undefined;
}

@Injectable({
    providedIn: 'root',
})
export class UserSettingsService {
    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public updateUserSettings(userSettings: UpdateUserSettingsData): Observable<Object> {
        return forkJoin([
            ...(this.shouldUpdateSettings(userSettings) ? [this.mHttpClient.put(Endpoints.USER.updateSettings, { displayName: userSettings.displayName})] : []),
            ...(userSettings.icon !== undefined ? [this.mHttpClient.put(Endpoints.USER.uploadUserProfileIcon, this.createIconBody(userSettings.icon))] : []),
        ]);
    }

    private shouldUpdateSettings(userSettings: UpdateUserSettingsData) {
        return userSettings.shouldUpdateDisplayName;
    }

    private createIconBody(file: File) {
        const formData: FormData = new FormData();
        formData.append('file', file);
        return formData;
    }
}
