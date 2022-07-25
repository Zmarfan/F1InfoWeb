import {Component, ElementRef, HostListener, OnDestroy} from '@angular/core';
import {animate, style, transition, trigger} from '@angular/animations';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faCircleHalfStroke, faEarthAfrica, faRightToBracket} from '@fortawesome/free-solid-svg-icons';
import {MatDialog} from '@angular/material/dialog';
import {LanguageSelectorComponent} from '../../language-selector/language-selector.component';
import {Router} from '@angular/router';
import {RouteHolder} from '../../../../app/routing/route-holder';
import {DialogResult} from '../../../dialog/dialog';
import {Language} from '../../../../common/constants/language';
import {TranslateService} from '@ngx-translate/core';
import {Session} from '../../../../app/configuration/session';
import {pushIfTrue} from '../../../utils/list-util';
import {SignUpComponentType} from '../../../../app/login-page/sign-up/sign-up.component';
import {Subscription} from 'rxjs';

interface MenuItem {
    icon: IconDefinition;
    translationKey: string;
    clickCallback: () => void;
    cssClass?: string;
}

@Component({
    selector: 'app-desktop-profile-header',
    templateUrl: './desktop-profile-header.component.html',
    styleUrls: ['./desktop-profile-header.component.scss'],
    animations: [
        trigger('inOutAnimation', [
            transition(':enter', [
                style({ transform: 'translateY(-5px) scaleY(0.85)', opacity: 0 }),
                animate('0.25s ease-out', style({ transform: 'translateY(0) scaleY(1)', opacity: 1 })),
            ]),
            transition(':leave', [
                style({ transform: 'translateY(0) scaleY(1)', opacity: 1 }),
                animate('0.25s ease-in', style({ transform: 'translateY(-5px) scaleY(0.85)', opacity: 0 })),
            ]),
        ]),
    ],
})
export class DesktopProfileHeaderComponent implements OnDestroy {
    private static readonly DARK_THEME_CLASS = 'dark-theme';
    private static readonly ANIMATION_LENGTH_MILLISECONDS = 250;

    public menuOpen: boolean = false;
    public userName: string = 'Lord_Zmarfan';
    public displayName: string = 'Anonymous User';

    private mLastTimeStamp: number = 0;
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
    }

    public get menuItems(): MenuItem[] {
        const items: MenuItem[] = [
            { icon: faEarthAfrica, translationKey: 'navigation.profile.language', clickCallback: () => this.openLanguageDialog() },
            { icon: faCircleHalfStroke, translationKey: 'navigation.profile.darkMode', clickCallback: () => DesktopProfileHeaderComponent.toggleDarkMode() },
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
        document.body.classList.toggle(DesktopProfileHeaderComponent.DARK_THEME_CLASS);
    }

    @HostListener('document:click', ['$event'])
    public mouseClick(event: MouseEvent) {
        if (!this.mElement.nativeElement.contains(event.target)) {
            this.menuOpen = false;
            this.mLastTimeStamp = event.timeStamp;
        }
    }

    public ngOnDestroy() {
        this.mSubscription.unsubscribe();
    }

    public menuToggle(event: MouseEvent) {
        if (this.mLastTimeStamp + DesktopProfileHeaderComponent.ANIMATION_LENGTH_MILLISECONDS < event.timeStamp) {
            this.menuOpen = !this.menuOpen;
            this.mLastTimeStamp = event.timeStamp;
        }
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
