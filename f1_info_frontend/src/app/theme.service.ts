import {BehaviorSubject, Observable} from 'rxjs';

export class ThemeService {
    private static readonly DARK_THEME_CLASS = 'dark-theme';
    private static THEME_STATUS = new BehaviorSubject<boolean>(false);
    private static THEME_STATUS$ = ThemeService.THEME_STATUS.asObservable();
    private static CURRENT_THEME: boolean = false;
    private static readonly DISABLE_BODY_SCROLL_CLASS: string = 'disable-body-scroll';

    public static toggleDarkMode() {
        ThemeService.CURRENT_THEME = !ThemeService.CURRENT_THEME;
        document.body.classList.toggle(ThemeService.DARK_THEME_CLASS);
        ThemeService.THEME_STATUS.next(ThemeService.CURRENT_THEME);
    }

    public static onChange(): Observable<boolean> {
        return ThemeService.THEME_STATUS$;
    }

    public static toggleBodyScroll(shouldScroll: boolean) {
        if (shouldScroll) {
            document.body.classList.remove(ThemeService.DISABLE_BODY_SCROLL_CLASS);
        } else {
            document.body.classList.add(ThemeService.DISABLE_BODY_SCROLL_CLASS);
        }
    }
}
