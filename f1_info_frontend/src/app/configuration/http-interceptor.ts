import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

export class TokenInterceptor implements HttpInterceptor {
    public intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log('asdsad');
        const xhr = request.clone({
            withCredentials: true,
            setHeaders: {},
        });
        return next.handle(xhr);
    }
}
