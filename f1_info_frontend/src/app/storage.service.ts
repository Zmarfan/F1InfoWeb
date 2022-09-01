import { Injectable } from '@angular/core';
import {Language} from '../common/constants/language';

@Injectable({
    providedIn: 'root',
})
export class StorageService {
    private static SAVED_LANGUAGE_KEY: string = 'saved_language';
    private static SAVED_IS_DARK_MODE_KEY: string = 'saved_is_dark_mode';

    public getSavedLanguage(): Language | undefined {
        return localStorage.getItem(StorageService.SAVED_LANGUAGE_KEY) as Language;
    }

    public storeSavedLanguage(language: Language) {
        return localStorage.setItem(StorageService.SAVED_LANGUAGE_KEY, language);
    }

    public getSavedIsDarkMode(): boolean {
        return localStorage.getItem(StorageService.SAVED_IS_DARK_MODE_KEY) === 'Y';
    }

    public storeSavedIsDarkMode(darkMode: boolean) {
        return localStorage.setItem(StorageService.SAVED_IS_DARK_MODE_KEY, darkMode ? 'Y' : 'N');
    }
}
