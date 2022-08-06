import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {Endpoints} from '../configuration/endpoints';
import {catchError, map, of, tap} from 'rxjs';
import {Session} from '../configuration/session';
import {RouteHolder} from './route-holder';
import {GetUserResponse} from '../../generated/server-responses';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {TranslateService} from '@ngx-translate/core';

@Injectable({
    providedIn: 'root',
})
export class AnonymousGuard implements CanActivate {
    public constructor(
        private mHttpClient: HttpClient,
        private mMessageService: GlobalMessageService,
        private mTranslate: TranslateService,
        private mSession: Session,
        private mRouter: Router
    ) {
    }

    public canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.mHttpClient.get<GetUserResponse>(Endpoints.AUTHENTICATION.getUser)
            .pipe(
                catchError(() => {
                    this.mMessageService.addError(this.mTranslate.instant('errors.unableToReachServer'));
                    return of(false);
                }),
                map((userResponse) => ({ loggedIn: userResponse !== null, stateChanged: userResponse !== this.mSession.user})),
                tap((state) => {
                    if (state.stateChanged) {
                        if (state.loggedIn) {
                            this.mSession.login();
                            this.mRouter.navigateByUrl(RouteHolder.HOMEPAGE).then();
                        } else {
                            this.mSession.logout();
                        }
                    }
                }),
                map((state) => !state.loggedIn)
            );
    }
}
