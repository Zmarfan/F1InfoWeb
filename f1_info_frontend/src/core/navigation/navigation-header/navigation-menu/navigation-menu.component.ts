import {Component, Input} from '@angular/core';
import {RouteItem} from '../navigation-header.component';

@Component({
    selector: 'app-navigation-menu',
    templateUrl: './navigation-menu.component.html',
    styleUrls: ['./navigation-menu.component.scss'],
})
export class NavigationMenuComponent {
    @Input() public navigationItemClickedCallback!: (item: RouteItem) => void;
    @Input() public routeItems!: RouteItem[];

    public navigationItemClicked(item: RouteItem) {
        this.navigationItemClickedCallback(item);
    }
}
