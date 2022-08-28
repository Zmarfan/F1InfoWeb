import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {RouteItem} from '../navigation-header.component';
import {animate, keyframes, style, transition, trigger} from '@angular/animations';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {fa0} from '@fortawesome/free-solid-svg-icons';
import {Session} from '../../../../app/configuration/session';
import {Subscription} from 'rxjs';

@Component({
    selector: 'app-navigation-menu',
    templateUrl: './navigation-menu.component.html',
    styleUrls: ['./navigation-menu.component.scss'],
    animations: [
        trigger('subItemOpenClose', [
            transition(':enter', [
                animate('0.25s ease-in', keyframes([
                    style({ 'max-height': 0, opacity: 0 }),
                    style({ 'max-height': '100px', opacity: 1 }),
                ])),
            ]),
            transition(':leave', [
                style({ 'max-height': '100px', opacity: 1 }),
                animate('0.25s ease-in', style({ 'max-height': 0, opacity: 0 })),
            ]),
        ]),
    ],
})
export class NavigationMenuComponent {
    @Input() public navigationItemClickedCallback!: (item: RouteItem) => void;
    @Input() public routeItems!: RouteItem[];

    public constructor(
        private mSession: Session
    ) {
    }

    public navigationItemClicked(item: RouteItem) {
        this.navigationItemClickedCallback(item);
    }

    public canShowRouteItem(routeItem: RouteItem): boolean {
        return !routeItem.loggedIn || this.mSession.user !== null;
    }
}
