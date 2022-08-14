import {Component, Input} from '@angular/core';
import {RouteItem} from '../navigation-header.component';
import {animate, keyframes, style, transition, trigger} from '@angular/animations';

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

    public navigationItemClicked(item: RouteItem) {
        this.navigationItemClickedCallback(item);
    }
}
