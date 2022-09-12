import {Injectable} from '@angular/core';
import {StorageHandler} from '../storage-handler';

@Injectable({
    providedIn: 'root',
})
export class CsrfTokenHolder {
    private mCsrfToken: string | null = null;

    public getToken(): string {
        if (!this.mCsrfToken) {
            this.mCsrfToken = StorageHandler.getConfig().csrfToken;
        }
        return this.mCsrfToken;
    }

    public setToken(token: string) {
        this.mCsrfToken = token;
        StorageHandler.modifyConfig((config) => {
            config.csrfToken = token;
            return config;
        });
    }
}
