import {Component, ElementRef, OnDestroy, OnInit} from '@angular/core';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faCircleHalfStroke, faEarthAfrica, faFaceLaughBeam, faRightToBracket, faUserGear} from '@fortawesome/free-solid-svg-icons';
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
import {ProfileHeaderService} from './profile-header.service';
import {GlobalMessageService} from '../../information/global-message-display/global-message.service';
import {BellNotificationResponse} from '../../../generated/server-responses';
import {StorageService} from '../../../app/storage.service';

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
export class ProfileHeaderComponent implements OnInit, OnDestroy {
    private static BELL_NOTIFICATION_ICON_MAP: Map<string, IconDefinition> = new Map<string, IconDefinition>([
        ['happy-smiley', faFaceLaughBeam],
    ]);

    public bellItems: BellItem[] = [];

    public loggedIn: boolean = false;
    private mSubscription: Subscription;

    public constructor(
        private mRouter: Router,
        private mSession: Session,
        private mDialog: MatDialog,
        private mElement: ElementRef,
        private mTranslateService: TranslateService,
        private mProfileService: ProfileHeaderService,
        private mMessageService: GlobalMessageService,
        private mStorageService: StorageService,
        private mThemeService: ThemeService
    ) {
        this.mSubscription = this.mSession.isLoggedIn.subscribe((loggedIn) => {
            this.loggedIn = loggedIn;
            this.fetchBellNotificationsIfNeeded();
        });
    }

    public get menuItems(): MenuItem[] {
        const items: MenuItem[] = [
            { icon: faEarthAfrica, translationKey: 'navigation.profile.language', clickCallback: () => this.openLanguageDialog() },
            { icon: faCircleHalfStroke, translationKey: 'navigation.profile.darkMode', clickCallback: () => this.mThemeService.toggleDarkMode() },
        ];

        pushIfTrue(
            items,
            this.loggedIn,
            { icon: faUserGear, translationKey: 'navigation.profile.userSettings', clickCallback: () => this.openUserSettingsDialog() }
        );

        pushIfTrue(
            items,
            !this.loggedIn,
            { icon: faRightToBracket, translationKey: 'navigation.profile.loginOrSignUp', clickCallback: () => this.routeToLogin() }
        );
        pushIfTrue(
            items,
            this.loggedIn,
            { icon: faRightToBracket, translationKey: 'navigation.profile.logout', clickCallback: () => this.logout() }
        );

        return items;
    }

    private static createBellConfigFromResponse(response: BellNotificationResponse): BellItem {
        return {
            key: response.translationKey,
            keyParams: response.parameters,
            opened: response.opened,
            icon: ProfileHeaderComponent.BELL_NOTIFICATION_ICON_MAP.get(response.iconType)!,
        };
    }

    public ngOnInit() {
        this.fetchBellNotificationsIfNeeded();
    }

    public ngOnDestroy() {
        this.mSubscription.unsubscribe();
    }

    public infoButtonCallback = () => {
        this.mDialog.open(WebsiteInfoComponent);
    };

    public bellItemsOpenedCallback = () => {
        this.mProfileService.markBellNotificationsAsOpened().subscribe({
            next: () => {
                this.bellItems.forEach((item) => {
                    item.opened = true;
                });
            },
            error: (e) => this.mMessageService.addHttpError(e),
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
            } else {
                this.mStorageService.storeSavedLanguage(this.mTranslateService.currentLang as Language);
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

    private fetchBellNotificationsIfNeeded() {
        if (!this.loggedIn) {
            this.bellItems = [];
            return;
        }

        this.mProfileService.getBellNotificationsToDisplay().subscribe({
            next: (bellItems) => {
                this.bellItems = bellItems.map((response) => ProfileHeaderComponent.createBellConfigFromResponse(response));
            },
            error: (e) => this.mMessageService.addHttpError(e),
        });
    }
}
