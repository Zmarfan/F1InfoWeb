import {Component, ElementRef, OnDestroy, OnInit} from '@angular/core';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {
    faCircleHalfStroke,
    faEarthAfrica,
    faFaceLaughBeam,
    faPeopleGroup,
    faPersonCircleQuestion,
    faRightToBracket,
    faUserGear,
} from '@fortawesome/free-solid-svg-icons';
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
import {BellNotificationIcon, BellNotificationResponse} from '../../../generated/server-responses';
import {StorageHandler} from '../../../app/storage-handler';
import {FriendsComponent} from '../friends/friends.component';
import {BellNotificationData} from './bell-notification-data';

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
    onClick: () => void;
    keyParams?: any;
}

@Component({
    selector: 'app-profile-header',
    templateUrl: './profile-header.component.html',
})
export class ProfileHeaderComponent implements OnInit, OnDestroy {
    public bellItems: BellItem[] = [];

    public loggedIn: boolean = false;
    private mSubscription: Subscription;

    public constructor(
        private mRouter: Router,
        private mSession: Session,
        private mDialog: MatDialog,
        private mElement: ElementRef,
        private mBellNotificationData: BellNotificationData,
        private mTranslateService: TranslateService,
        private mProfileService: ProfileHeaderService,
        private mMessageService: GlobalMessageService,
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
            { icon: faPeopleGroup, translationKey: 'navigation.profile.friends', clickCallback: () => this.mDialog.open(FriendsComponent) }
        );

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

    public ngOnInit() {
        this.mDialog.open(UserSettingsComponent, { disableClose: true });

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

    private createBellConfigFromResponse(response: BellNotificationResponse): BellItem {
        return {
            key: response.translationKey,
            keyParams: response.parameters,
            opened: response.opened,
            icon: this.mBellNotificationData.getBellNotificationIcon(response),
            onClick: this.mBellNotificationData.createBellNotificationClickCallBack(response),
        };
    }

    private openUserSettingsDialog() {
        this.mDialog.open(UserSettingsComponent, { disableClose: true });
    }

    private openLanguageDialog() {
        const openedSelectedLanguage = this.mTranslateService.currentLang as Language;
        this.mDialog.open(LanguageSelectorComponent).afterClosed().subscribe((result: DialogResult) => {
            if (!result?.wasApplied) {
                this.mTranslateService.use(openedSelectedLanguage);
            } else {
                StorageHandler.modifyConfig((config) => {
                    config.language = this.mTranslateService.currentLang as Language;
                    return config;
                });
            }
        });
    }

    private routeToLogin() {
        this.mRouter.navigate([RouteHolder.LOGIN_PAGE]).then();
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
                this.bellItems = bellItems.map((response) => this.createBellConfigFromResponse(response));
            },
            error: (e) => this.mMessageService.addHttpError(e),
        });
    }
}
