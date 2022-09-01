import {BehaviorSubject, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {StorageService} from './storage.service';

@Injectable({
    providedIn: 'root',
})
export class ThemeService {
    private static readonly DARK_THEME_CLASS = 'dark-theme';
    private static readonly DISABLE_BODY_SCROLL_CLASS: string = 'disable-body-scroll';
    private mThemeStatus = new BehaviorSubject<boolean>(false);
    private mThemeStatus$ = this.mThemeStatus.asObservable();
    private mIsDarkMode: boolean = false;

    public constructor(
        private mStorageService: StorageService
    ) {
        if (this.mIsDarkMode !== mStorageService.getSavedIsDarkMode()) {
            this.toggleDarkMode();
        }
    }

    public toggleDarkMode() {
        this.mIsDarkMode = !this.mIsDarkMode;
        document.body.classList.toggle(ThemeService.DARK_THEME_CLASS);
        this.mThemeStatus.next(this.mIsDarkMode);
        this.mStorageService.storeSavedIsDarkMode(this.mIsDarkMode);
    }

    public onChange(): Observable<boolean> {
        return this.mThemeStatus$;
    }

    public toggleBodyScroll(shouldScroll: boolean) {
        if (shouldScroll) {
            document.body.classList.remove(ThemeService.DISABLE_BODY_SCROLL_CLASS);
        } else {
            document.body.classList.add(ThemeService.DISABLE_BODY_SCROLL_CLASS);
        }
    }
}
