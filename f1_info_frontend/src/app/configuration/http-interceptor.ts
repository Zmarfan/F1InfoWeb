import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {Session} from './session';
import {Router} from '@angular/router';
import {Injectable} from '@angular/core';
import {RouteHolder} from '../routing/route-holder';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    public constructor(
        private mRouter: Router
    ) {
    }

    public intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const xhr = request.clone({
            withCredentials: true,
            setHeaders: {},
        });
        return next.handle(xhr).pipe(
            catchError((error: HttpErrorResponse) => {
                if (error.status === 401) {
                    this.mRouter.navigateByUrl(RouteHolder.HOMEPAGE).then();
                }
                return throwError(() => error);
            })
        );
    }
}
