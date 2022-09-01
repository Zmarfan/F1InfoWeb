import { Injectable } from '@angular/core';
import {Language} from '../common/constants/language';

@Injectable({
    providedIn: 'root',
})
export class StorageService {
    private static SAVED_LANGUAGE_KEY: string = 'saved_language';

    public getSavedLanguage(): Language | undefined {
        return localStorage.getItem(StorageService.SAVED_LANGUAGE_KEY) as Language;
    }

    public storeSavedLanguage(language: Language) {
        return localStorage.setItem(StorageService.SAVED_LANGUAGE_KEY, language);
    }
}
