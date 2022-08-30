import {Component, ElementRef, OnDestroy} from '@angular/core';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faCircleHalfStroke, faEarthAfrica, faFaceLaughBeam, faFaceSadCry, faRightToBracket, faUserGear} from '@fortawesome/free-solid-svg-icons';
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
import {Subscription} from 'rxjs';
import {UserSettingsComponent} from '../user-settings/user-settings.component';
import {ThemeService} from '../../../app/theme.service';
import {WebsiteInfoComponent} from './website-info/website-info.component';

export interface MenuItem {
    icon: IconDefinition;
    translationKey: string;
    clickCallback: () => void;
    cssClass?: string;
}

export interface BellItem {
    icon: IconDefinition;
    key: string;
    opened: boolean;
    keyParams?: any;
}

@Component({
    selector: 'app-profile-header',
    templateUrl: './profile-header.component.html',
})
export class ProfileHeaderComponent implements OnDestroy {
    public bellItems: BellItem[] = [
        {
            icon: faFaceLaughBeam,
            key: 'bellMessages.completeFeedback',
            opened: false,
            keyParams: {
                feedback: 'More padding between rows please :)',
            },
        },
        {
            icon: faFaceSadCry,
            key: 'bellMessages.completeFeedback',
            opened: true,
            keyParams: {
                feedback: 'Asså du suger verkligen',
            },
        },
    ];

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
            { icon: faCircleHalfStroke, translationKey: 'navigation.profile.darkMode', clickCallback: () => ThemeService.toggleDarkMode() },
        ];

        pushIfTrue(
            items,
            this.mLoggedIn,
            { icon: faUserGear, translationKey: 'navigation.profile.userSettings', clickCallback: () => this.openUserSettingsDialog() }
        );

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

    public ngOnDestroy() {
        this.mSubscription.unsubscribe();
    }

    public infoButtonCallback = () => {
        this.mDialog.open(WebsiteInfoComponent);
    };

    public bellItemsOpenedCallback = () => {
        this.bellItems.forEach((item) => {
            item.opened = true;
        });
    };

    private openUserSettingsDialog() {
        this.mDialog.open(UserSettingsComponent, { disableClose: true });
    }

    private openLanguageDialog() {
        const openedSelectedLanguage = this.mTranslateService.currentLang as Language;
        this.mDialog.open(LanguageSelectorComponent).afterClosed().subscribe((result: DialogResult) => {
            if (!result?.wasApplied) {
                this.mTranslateService.use(openedSelectedLanguage);
            }
        });
    }

    private routeToLogin() {
        this.mRouter.navigate([RouteHolder.SIGN_UP_PAGE], { queryParams: { type: SignUpComponentType.SIGN_UP } }).then();
    }

    private logout() {
        this.mSession.logout();
        this.mRouter.navigateByUrl(RouteHolder.HOMEPAGE).then();
    }
}
