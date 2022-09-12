import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {Router} from '@angular/router';
import {Injectable} from '@angular/core';
import {RouteHolder} from '../routing/route-holder';
import {CsrfTokenHolder} from './csrf-token-holder';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    public constructor(
        private mRouter: Router,
        private mCsrfTokenHolder: CsrfTokenHolder
    ) {
    }

    public intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const modifiedRequest = request.clone({
            withCredentials: true,
            headers: request.headers.set('X-XSRF-TOKEN', this.mCsrfTokenHolder.getToken()),
        });
        return next.handle(modifiedRequest).pipe(
            catchError((error: HttpErrorResponse) => {
                if (error.status === 401) {
                    this.mRouter.navigateByUrl(RouteHolder.HOMEPAGE).then();
                }
                return throwError(() => error);
            })
        );
    }
}
