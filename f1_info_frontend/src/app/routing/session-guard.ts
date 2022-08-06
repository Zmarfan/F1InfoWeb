import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Session} from '../configuration/session';
import {map, tap} from 'rxjs';
import {RouteHolder} from './route-holder';
import {Endpoints} from '../configuration/endpoints';
import {HttpClient} from '@angular/common/http';
import {GetUserResponse} from '../../generated/server-responses';

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
        return this.mHttpClient.get<GetUserResponse>(Endpoints.AUTHENTICATION.getUser).pipe(
            map((userResponse) => ({ loggedIn: userResponse !== null, changedState: userResponse !== this.mSession.user })),
            tap((state) => {
                if (state.changedState) {
                    if (!state.loggedIn) {
                        this.mSession.logout();
                        this.mRouter.navigateByUrl(RouteHolder.LOGIN_PAGE).then();
                    } else {
                        this.mSession.login();
                    }
                }
            }),
            map((state) => state.loggedIn)
        );
    }
}
