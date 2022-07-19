import { Component } from '@angular/core';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faCircleHalfStroke} from '@fortawesome/free-solid-svg-icons/faCircleHalfStroke';
import {faHouseChimney} from '@fortawesome/free-solid-svg-icons';

interface RouteItem {
    key: string;
    icon: IconDefinition;
}

@Component({
    selector: 'app-navigation-header',
    templateUrl: './navigation-header.component.html',
    styleUrls: ['./navigation-header.component.scss'],
})
export class NavigationHeaderComponent {
    public routeItems: RouteItem[] = [
        { key: 'key.homepage', icon: faHouseChimney },
        { key: 'key.test', icon: faCircleHalfStroke },
    ];
}
