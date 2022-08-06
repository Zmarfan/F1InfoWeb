import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Session} from '../configuration/session';
import {catchError, map, of, tap} from 'rxjs';
import {RouteHolder} from './route-holder';
import {Endpoints} from '../configuration/endpoints';
import {HttpClient} from '@angular/common/http';
import {GetUserResponse} from '../../generated/server-responses';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {TranslateService} from '@ngx-translate/core';

@Injectable({
    providedIn: 'root',
})
export class SessionGuard implements CanActivate {
    public constructor(
        private mHttpClient: HttpClient,
        private mMessageService: GlobalMessageService,
        private mTranslate: TranslateService,
        private mSession: Session,
        private mRouter: Router
    ) {
    }

    public canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.mHttpClient.get<GetUserResponse>(Endpoints.AUTHENTICATION.getUser).pipe(
            catchError(() => {
                this.mMessageService.addError(this.mTranslate.instant('errors.unableToReachServer'));
                return of(false);
            }),
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
