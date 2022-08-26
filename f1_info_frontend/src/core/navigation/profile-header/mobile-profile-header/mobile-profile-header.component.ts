import {Component, Input} from '@angular/core';
import {MenuItem} from '../profile-header.component';
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
    ],
})
export class MobileProfileHeaderComponent {
    @Input() public menuItems!: MenuItem[];

    public navigationState = NavigationStateService;
    public headerOpen: boolean = false;

    public constructor(
        public session: Session
    ) {
    }

    public get icon(): IconDefinition {
        return this.headerOpen ? faTimes : faCircleUser;
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

    private toggleOpen(isOpen: boolean) {
        this.headerOpen = isOpen;
        NavigationStateService.PROFILE_MENU_OPEN = isOpen;
        ThemeService.toggleBodyScroll(!isOpen);
    }
}
