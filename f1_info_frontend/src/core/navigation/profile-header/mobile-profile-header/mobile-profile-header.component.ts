import {Component, Input} from '@angular/core';
import {BellItem, MenuItem} from '../profile-header.component';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faCircleUser, faTimes} from '@fortawesome/free-solid-svg-icons';
import {animate, keyframes, style, transition, trigger} from '@angular/animations';
import {Observable} from 'rxjs';
import {Session, User} from '../../../../app/configuration/session';
import {ThemeService} from '../../../../app/theme.service';
import {NavigationStateService} from '../../../../app/navigation-state.service';

@Component({
    selector: 'app-mobile-profile-header',
    templateUrl: './mobile-profile-header.component.html',
    styleUrls: ['./mobile-profile-header.component.scss', '../profile-header.component.scss'],
    animations: [
        trigger('inOutAnimation', [
            transition(':enter', [
                animate('0.25s ease-in', keyframes([
                    style({ transform: 'translateX(100%)', opacity: 0, offset: 0 }),
                    style({ transform: 'translateX(0)', opacity: 1, offset: 1 }),
                ])),
            ]),
            transition(':leave', [
                style({ transform: 'translateX(0)', opacity: 1 }),
                animate('0.25s ease-in', style({ transform: 'translateX(100%)', opacity: 0 })),
            ]),
        ]),
        trigger('bellInOutAnimation', [
            transition(':enter', [
                style({ 'max-height': '0', opacity: 0, overflow: 'hidden' }),
                animate('0.5s ease-out', style({ 'max-height': '300px', opacity: 1 })),
            ]),
            transition(':leave', [
                style({ 'max-height': '300px', opacity: 1 }),
                animate('0.5s ease-in', style({ 'max-height': '0', opacity: 0 })),
            ]),
        ]),
    ],
})
export class MobileProfileHeaderComponent {
    @Input() public loggedIn!: boolean;
    @Input() public menuItems!: MenuItem[];
    @Input() public bellItems!: BellItem[];
    @Input() public infoButtonCallback!: () => void;
    @Input() public bellItemsOpenedCallback!: () => void;

    public navigationState = NavigationStateService;
    public headerOpen: boolean = false;
    public bellOpen: boolean = false;

    public constructor(
        public session: Session
    ) {
    }

    public get icon(): IconDefinition {
        return this.headerOpen ? faTimes : faCircleUser;
    }

    public get shouldShowMainIconBellNotification(): boolean {
        return !this.headerOpen && this.amountOfUnOpenedBellItems > 0;
    }

    public get amountOfUnOpenedBellItems(): number {
        return this.bellItems.filter((item) => !item.opened).length;
    }

    public toggleProfileHeader() {
        this.toggleOpen(!this.headerOpen);
    }

    public clickedOutsideOfMenu() {
        this.toggleOpen(false);
    }

    public menuItemClicked(item: MenuItem) {
        this.toggleOpen(false);
        item.clickCallback();
    }

    public toggleBellOpen() {
        this.sendBellOpenedCallbackIfNeeded();
        this.bellOpen = !this.bellOpen;
    }

    private toggleOpen(isOpen: boolean) {
        this.headerOpen = isOpen;
        this.sendBellOpenedCallbackIfNeeded();
        NavigationStateService.PROFILE_MENU_OPEN = isOpen;
        ThemeService.toggleBodyScroll(!isOpen);
    }

    private sendBellOpenedCallbackIfNeeded() {
        if (this.bellOpen && this.amountOfUnOpenedBellItems > 0) {
            this.bellItemsOpenedCallback();
        }
    }
}
