import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Session} from '../configuration/session';
import {tap} from 'rxjs';
import {RouteHolder} from './route-holder';
import {Endpoints} from '../configuration/endpoints';
import {HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root',
})
export class SessionGuard implements CanActivate {
    public constructor(
        private mHttpClient: HttpClient,
        private mSession: Session,
        private mRouter: Router
    ) {
    }

    public canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.mHttpClient.get<boolean>(Endpoints.AUTHENTICATION.isLoggedIn).pipe(
            tap((isLoggedIn) => {
                if (!isLoggedIn) {
                    this.mSession.logout();
                    this.mRouter.navigateByUrl(RouteHolder.LOGIN_PAGE).then();
                } else {
                    this.mSession.login();
                }
            })
        );
    }
}
