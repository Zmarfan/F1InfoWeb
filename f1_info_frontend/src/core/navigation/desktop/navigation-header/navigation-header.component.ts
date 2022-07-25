import {Component, OnDestroy, OnInit} from '@angular/core';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faCircleHalfStroke} from '@fortawesome/free-solid-svg-icons/faCircleHalfStroke';
import {faBars, faCheck, faCross, faHouseChimney, faTimes} from '@fortawesome/free-solid-svg-icons';
import {RouteHolder} from '../../../../app/routing/route-holder';
import {NavigationEnd, Router} from '@angular/router';
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
    templateUrl: './navigation-header.component.html',
    styleUrls: ['./navigation-header.component.scss', '../../../../app/app.component.scss'],
})
export class NavigationHeaderComponent implements OnInit, OnDestroy {
    public routeItems: RouteItem[] = [
        { route: RouteHolder.HOMEPAGE, key: 'key.homepage', icon: faHouseChimney, selected: true, loggedIn: false },
        { route: 'test', key: 'key.test', icon: faCircleHalfStroke, selected: false, loggedIn: true },
    ];

    public mobileNavigationOpened: boolean = false;
    private mLoggedIn: boolean = false;
    private mRouteChangeSubscription!: Subscription;
    private mLoggedInSubscription!: Subscription;

    public constructor(
        private mRouter: Router,
        private mSession: Session
    ) {
    }

    public get mobileIcon(): IconDefinition {
        return this.mobileNavigationOpened ? faTimes : faBars;
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

    public openMobileNavigation() {
        this.mobileNavigationOpened = !this.mobileNavigationOpened;
    }

    public shouldDisplayItem(item: RouteItem): boolean {
        return !item.loggedIn || this.mLoggedIn;
    }

    public navigationItemClicked(item: RouteItem) {
        this.mRouter.navigateByUrl(item.route).then();
        this.mobileNavigationOpened = false;
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
