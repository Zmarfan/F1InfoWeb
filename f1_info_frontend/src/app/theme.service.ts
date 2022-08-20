import {BehaviorSubject, Observable} from 'rxjs';

export class ThemeService {
    private static readonly DARK_THEME_CLASS = 'dark-theme';
    private static THEME_STATUS = new BehaviorSubject<boolean>(false);
    private static THEME_STATUS$ = ThemeService.THEME_STATUS.asObservable();
    private static CURRENT_THEME: boolean = false;

    public static toggleDarkMode() {
        ThemeService.CURRENT_THEME = !ThemeService.CURRENT_THEME;
        document.body.classList.toggle(ThemeService.DARK_THEME_CLASS);
        ThemeService.THEME_STATUS.next(ThemeService.CURRENT_THEME);
    }

    public static onChange(): Observable<boolean> {
        return ThemeService.THEME_STATUS$;
    }
}
