import {Component, OnDestroy, OnInit} from '@angular/core';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faCircleHalfStroke} from '@fortawesome/free-solid-svg-icons/faCircleHalfStroke';
import {faHouseChimney} from '@fortawesome/free-solid-svg-icons';
import {RouteHolder} from '../../../../app/routing/route-holder';
import {NavigationEnd, Route, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {Session} from '../../../../app/configuration/session';

interface RouteItem {
    route: string;
    key: string;
    icon: IconDefinition;
    selected: boolean;
    loggedIn: boolean;
}

@Component({
    selector: 'app-navigation-header',
    templateUrl: './desktop-navigation-header.component.html',
    styleUrls: ['./desktop-navigation-header.component.scss'],
})
export class DesktopNavigationHeaderComponent implements OnInit, OnDestroy {
    public routeItems: RouteItem[] = [
        { route: RouteHolder.HOMEPAGE, key: 'key.homepage', icon: faHouseChimney, selected: true, loggedIn: false },
        { route: 'test', key: 'key.test', icon: faCircleHalfStroke, selected: false, loggedIn: true },
    ];

    private mLoggedIn: boolean = false;
    private mRouteChangeSubscription!: Subscription;
    private mLoggedInSubscription!: Subscription;

    public constructor(
        private mRouter: Router,
        private mSession: Session
    ) {
    }

    public ngOnInit() {
        this.mRouteChangeSubscription = this.mRouter.events.subscribe((val) => {
            if (val instanceof NavigationEnd) {
                this.setNewRoute(this.routeItems.find((item) => val.urlAfterRedirects.endsWith(item.route)));
            }
        });

        this.mLoggedInSubscription = this.mSession.isLoggedIn.subscribe((loggedIn) => {
            this.mLoggedIn = loggedIn;
        });
    }

    public ngOnDestroy() {
        this.mRouteChangeSubscription.unsubscribe();
        this.mLoggedInSubscription.unsubscribe();
    }

    public shouldDisplayItem(item: RouteItem): boolean {
        return !item.loggedIn || this.mLoggedIn;
    }

    public navigationItemClicked(item: RouteItem) {
        this.mRouter.navigateByUrl(item.route).then();
    }

    private setNewRoute(item: RouteItem | undefined) {
        this.routeItems.forEach((routeItem) => {
            routeItem.selected = false;
        });
        if (item !== undefined) {
            item.selected = true;
        }
    }
}
