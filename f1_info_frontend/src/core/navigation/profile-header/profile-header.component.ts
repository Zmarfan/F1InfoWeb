import {Component, ElementRef, HostListener} from '@angular/core';
import {animate, style, transition, trigger} from '@angular/animations';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faCircleHalfStroke, faEarthAfrica, faRightToBracket} from '@fortawesome/free-solid-svg-icons';
import {MatDialog} from '@angular/material/dialog';
import {LanguageSelectorComponent} from './language-selector/language-selector.component';
import {Router} from '@angular/router';
import {RouteHolder} from '../../../app/routing/routeHolder';
import {DialogResult} from '../../dialog/dialog';
import {Language} from '../../../common/constants/language';
import {TranslateService} from '@ngx-translate/core';

interface MenuItem {
    icon: IconDefinition;
    translationKey: string;
    clickCallback: () => void;
    cssClass?: string;
}

@Component({
    selector: 'app-profile-header',
    templateUrl: './profile-header.component.html',
    styleUrls: ['./profile-header.component.scss'],
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
export class ProfileHeaderComponent {
    private static readonly DARK_THEME_CLASS = 'dark-theme';
    private static readonly ANIMATION_LENGTH_MILLISECONDS = 250;

    public menuOpen: boolean = false;
    public userName: string = 'Lord_Zmarfan';
    public displayName: string = 'Anonymous User';
    public menuItems: MenuItem[] = [
        { icon: faEarthAfrica, translationKey: 'navigation.profile.language', clickCallback: () => this.openLanguageDialog() },
        { icon: faCircleHalfStroke, translationKey: 'navigation.profile.darkMode', clickCallback: () => ProfileHeaderComponent.toggleDarkMode() },
        { icon: faRightToBracket, translationKey: 'navigation.profile.loginOrSignUp', clickCallback: () => this.routeToLogin() },
    ];

    private mLastTimeStamp: number = 0;

    public constructor(
        private mRouter: Router,
        private mDialog: MatDialog,
        private mElement: ElementRef,
        private mTranslateService: TranslateService
    ) {
    }

    private static toggleDarkMode() {
        document.body.classList.toggle(ProfileHeaderComponent.DARK_THEME_CLASS);
    }

    @HostListener('document:click', ['$event'])
    public mouseClick(event: MouseEvent) {
        if (!this.mElement.nativeElement.contains(event.target)) {
            this.menuOpen = false;
            this.mLastTimeStamp = event.timeStamp;
        }
    }

    public menuToggle(event: MouseEvent) {
        if (this.mLastTimeStamp + ProfileHeaderComponent.ANIMATION_LENGTH_MILLISECONDS < event.timeStamp) {
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
        this.mRouter.navigateByUrl(RouteHolder.SIGN_UP_PAGE).then();
        this.menuOpen = false;
    }
}
