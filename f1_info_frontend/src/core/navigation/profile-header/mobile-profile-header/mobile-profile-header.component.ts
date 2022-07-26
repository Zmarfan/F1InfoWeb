import {Component, Input} from '@angular/core';
import {MenuItem} from '../profile-header.component';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faCircleUser, faTimes} from '@fortawesome/free-solid-svg-icons';
import {animate, keyframes, style, transition, trigger} from '@angular/animations';

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
    @Input() public userName!: string;
    @Input() public displayName!: string;
    @Input() public menuItems!: MenuItem[];

    public headerOpen: boolean = false;

    public get icon(): IconDefinition {
        return this.headerOpen ? faTimes : faCircleUser;
    }

    public toggleProfileHeader() {
        this.headerOpen = !this.headerOpen;
    }

    public menuItemClicked(item: MenuItem) {
        this.headerOpen = false;
        item.clickCallback();
    }
}
