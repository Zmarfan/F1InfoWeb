import {Component, OnDestroy, OnInit} from '@angular/core';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faBars, faHouseChimney, faTimes} from '@fortawesome/free-solid-svg-icons';
import {animate, keyframes, style, transition, trigger} from '@angular/animations';
import {RouteHolder} from '../../../../app/routing/route-holder';
import {faCircleHalfStroke} from '@fortawesome/free-solid-svg-icons/faCircleHalfStroke';
import {Subscription} from 'rxjs';
import {NavigationEnd, Router} from '@angular/router';
import {Session} from '../../../../app/configuration/session';

export interface RouteItem {
    route: string;
    key: string;
    icon: IconDefinition;
    selected: boolean;
    loggedIn: boolean;
}

@Component({
    selector: 'app-navigation-header',
    templateUrl: './navigation-header.component.html',
    styleUrls: ['./navigation-header.component.scss'],
    animations: [
        trigger('mobileInOutAnimation', [
            transition(':enter', [
                animate('0.5s ease-in', keyframes([
                    style({ width: '0', opacity: 0, offset: 0 }),
                    style({ width: '91%', opacity: 1, offset: 0.75 }),
                    style({ width: '90%', opacity: 1, offset: 1 }),
                ])),
            ]),
            transition(':leave', [
                style({ width: '90%', opacity: 1 }),
                animate('0.25s ease-in', style({ width: 0, opacity: 0 })),
            ]),
        ]),
    ],
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

    public get routeItemsToShow(): RouteItem[] {
        return this.routeItems.filter((item) => !item.loggedIn || this.mLoggedIn);
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

    public navigationItemClicked = (item: RouteItem) => {
        this.mRouter.navigateByUrl(item.route).then();
        this.mobileNavigationOpened = false;
    };

    private setNewRoute(item: RouteItem | undefined) {
        this.routeItems.forEach((routeItem) => {
            routeItem.selected = false;
        });
        if (item !== undefined) {
            item.selected = true;
        }
    }
}
