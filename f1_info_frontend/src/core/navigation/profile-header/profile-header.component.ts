import {Component, ElementRef, HostListener, OnDestroy} from '@angular/core';
import {animate, style, transition, trigger} from '@angular/animations';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faCircleHalfStroke, faEarthAfrica, faRightToBracket} from '@fortawesome/free-solid-svg-icons';
import {MatDialog} from '@angular/material/dialog';
import {LanguageSelectorComponent} from '../language-selector/language-selector.component';
import {Router} from '@angular/router';
import {RouteHolder} from '../../../app/routing/route-holder';
import {DialogResult} from '../../dialog/dialog';
import {Language} from '../../../common/constants/language';
import {TranslateService} from '@ngx-translate/core';
import {Session} from '../../../app/configuration/session';
import {pushIfTrue} from '../../utils/list-util';
import {SignUpComponentType} from '../../../app/login-page/sign-up/sign-up.component';
import {map, Observable, shareReplay, Subscription} from 'rxjs';

export interface MenuItem {
    icon: IconDefinition;
    translationKey: string;
    clickCallback: () => void;
    cssClass?: string;
}

@Component({
    selector: 'app-profile-header',
    templateUrl: './profile-header.component.html',
})
export class ProfileHeaderComponent implements OnDestroy {
    private static readonly DARK_THEME_CLASS = 'dark-theme';

    public menuOpen: boolean = false;
    public displayName$: Observable<string | undefined>;
    public email$: Observable<string | undefined>;

    private mLoggedIn: boolean = false;
    private mSubscription: Subscription;

    public constructor(
        private mRouter: Router,
        private mSession: Session,
        private mDialog: MatDialog,
        private mElement: ElementRef,
        private mTranslateService: TranslateService
    ) {
        this.mSubscription = this.mSession.isLoggedIn.subscribe((loggedIn) => {
            this.mLoggedIn = loggedIn;
        });

        this.displayName$ = this.mSession.user.pipe(shareReplay(1), map((user) => user?.displayName));
        this.email$ = this.mSession.user.pipe(shareReplay(1), map((user) => user?.email));
    }

    public get menuItems(): MenuItem[] {
        const items: MenuItem[] = [
            { icon: faEarthAfrica, translationKey: 'navigation.profile.language', clickCallback: () => this.openLanguageDialog() },
            { icon: faCircleHalfStroke, translationKey: 'navigation.profile.darkMode', clickCallback: () => ProfileHeaderComponent.toggleDarkMode() },
        ];

        pushIfTrue(
            items,
            !this.mLoggedIn,
            { icon: faRightToBracket, translationKey: 'navigation.profile.loginOrSignUp', clickCallback: () => this.routeToLogin() }
        );
        pushIfTrue(
            items,
            this.mLoggedIn,
            { icon: faRightToBracket, translationKey: 'navigation.profile.logout', clickCallback: () => this.logout() }
        );

        return items;
    }

    private static toggleDarkMode() {
        document.body.classList.toggle(ProfileHeaderComponent.DARK_THEME_CLASS);
    }

    public ngOnDestroy() {
        this.mSubscription.unsubscribe();
    }

    private openLanguageDialog() {
        this.menuOpen = false;

        const openedSelectedLanguage = this.mTranslateService.currentLang as Language;
        this.mDialog.open(LanguageSelectorComponent).afterClosed().subscribe((result: DialogResult) => {
            if (!result?.wasApplied) {
                this.mTranslateService.use(openedSelectedLanguage);
            }
        });
    }

    private routeToLogin() {
        this.mRouter.navigate([RouteHolder.SIGN_UP_PAGE], { queryParams: { type: SignUpComponentType.SIGN_UP } }).then();
        this.menuOpen = false;
    }

    private logout() {
        this.mSession.logout();
        this.mRouter.navigateByUrl(RouteHolder.HOMEPAGE).then();
        this.menuOpen = false;
    }
}
