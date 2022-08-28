import {Component, OnDestroy, OnInit} from '@angular/core';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faBars, faBookOpen, faCar, faCode, faHouseChimney, faPerson, faRankingStar, faTimes} from '@fortawesome/free-solid-svg-icons';
import {animate, keyframes, style, transition, trigger} from '@angular/animations';
import {RouteHolder} from '../../../app/routing/route-holder';
import {Subscription} from 'rxjs';
import {NavigationEnd, Router} from '@angular/router';
import {Session} from '../../../app/configuration/session';
import {ThemeService} from '../../../app/theme.service';
import {NavigationStateService} from '../../../app/navigation-state.service';

export interface RouteItem {
    route?: string;
    key: string;
    subItems?: RouteItem[];
    showSubItems?: boolean;
    icon?: IconDefinition;
    selected?: boolean;
    loggedIn?: boolean;
}

@Component({
    selector: 'app-navigation-header',
    templateUrl: './navigation-header.component.html',
    styleUrls: ['./navigation-header.component.scss'],
    animations: [
        trigger('mobileInOutAnimation', [
            transition(':enter', [
                animate('0.25s ease-in', keyframes([
                    style({ transform: 'translateX(-100%)', opacity: 0, offset: 0 }),
                    style({ transform: 'translateX(0)', opacity: 1, offset: 1 }),
                ])),
            ]),
            transition(':leave', [
                style({ transform: 'translateX(0)', opacity: 1 }),
                animate('0.25s ease-in', style({ transform: 'translateX(-100%)', opacity: 0 })),
            ]),
        ]),
    ],
})
export class NavigationHeaderComponent implements OnInit, OnDestroy {
    public navigationState = NavigationStateService;

    public routeItems: RouteItem[] = [
        { route: RouteHolder.HOMEPAGE, key: 'navigation.route.homepage', icon: faHouseChimney, selected: true },
        { route: RouteHolder.DRIVER_PROFILE, key: 'navigation.route.driverProfile', icon: faPerson },
        {
            key: 'navigation.route.standings',
            icon: faRankingStar,
            subItems: [
                { route: RouteHolder.DRIVER_REPORT, key: 'navigation.route.driverReport' },
                { route: RouteHolder.CONSTRUCTOR_REPORT, key: 'navigation.route.constructorReport' },
            ],
        },
        { route: RouteHolder.RACE_REPORT, key: 'navigation.route.raceReport', icon: faCar },
        {
            key: 'navigation.route.development',
            icon: faCode,
            subItems: [
                { route: RouteHolder.FEEDBACK, key: 'navigation.route.feedback' },
                { route: RouteHolder.CHANGE_LOG, key: 'navigation.route.changeLog' },
            ],
        },
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
                const subItems = this.routeItems.filter((route) => route.subItems).flatMap((route) => route.subItems);
                this.setNewRoute([...this.routeItems, ...subItems].find((item) => val.urlAfterRedirects.includes(item?.route ?? '--')));
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
        this.toggleMobileNavigation(!this.mobileNavigationOpened);
    }

    public navigationItemClicked = (item: RouteItem) => {
        if (item.route !== undefined) {
            this.mRouter.navigateByUrl(item.route).then();
            this.toggleMobileNavigation(false);
        } else {
            item.showSubItems = !item.showSubItems;
        }
    };

    public clickedOutsideOfMenu() {
        this.toggleMobileNavigation(false);
    }

    private setNewRoute(item: RouteItem | undefined) {
        this.routeItems.forEach((routeItem) => {
            routeItem.selected = false;
            if (routeItem.subItems) {
                routeItem.subItems.forEach((subItem) => {
                    subItem.selected = false;
                });
            }
        });
        if (item !== undefined) {
            item.selected = true;
            this.routeItems.forEach((holderItem) => {
                if (holderItem.subItems?.includes(item)) {
                    holderItem.selected = true;
                }
            });
        }
    }

    private toggleMobileNavigation(opened: boolean) {
        this.mobileNavigationOpened = opened;
        NavigationStateService.NAVIGATION_MENU_OPEN = opened;
        ThemeService.toggleBodyScroll(!opened);
    }
}
