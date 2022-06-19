import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {Endpoints} from '../configuration/endpoints';
import {map, tap} from 'rxjs';
import {Session} from '../configuration/session';
import {RouteHolder} from './route-holder';

@Injectable({
    providedIn: 'root',
})
export class AnonymousGuard implements CanActivate {
    public constructor(
        private mHttpClient: HttpClient,
        private mSession: Session,
        private mRouter: Router
    ) {
    }

    public canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.mHttpClient.get<boolean>(Endpoints.AUTHENTICATION.isLoggedIn)
            .pipe(
                tap((loggedIn) => {
                    if (loggedIn) {
                        this.mSession.login();
                        this.mRouter.navigateByUrl(RouteHolder.HOMEPAGE).then();
                    } else {
                        this.mSession.logout();
                    }
                }),
                map((loggedIn) => !loggedIn)
            );
    }
}
