import {Injectable, OnInit} from '@angular/core';
import {AppRoutingModule} from '../routing/app-routing.module';
import {HttpClient} from '@angular/common/http';
import {Endpoints} from './endpoints';
import {Observable, startWith, Subject, tap} from 'rxjs';
import {Router} from '@angular/router';
import {RouteHolder} from '../routing/route-holder';

@Injectable({
    providedIn: 'root',
})
export class Session {
    private mLoggedIn: Subject<boolean> = new Subject<boolean>();
    private readonly mLoggedIn$: Observable<boolean>;
    private mClientLoggedInStatus: boolean = false;

    public constructor(
        private mRouter: Router,
        private mHttpClient: HttpClient
    ) {
        this.mLoggedIn$ = this.mLoggedIn.asObservable();
        this.mHttpClient.get<boolean>(Endpoints.AUTHENTICATION.isLoggedIn)
            .pipe(startWith(false))
            .subscribe((loggedIn) => {
                this.mClientLoggedInStatus = loggedIn;
                this.mLoggedIn.next(loggedIn);
            });
    }

    public get isLoggedIn(): Observable<boolean> {
        return this.mLoggedIn$;
    }

    public login() {
        if (!this.mClientLoggedInStatus) {
            this.mLoggedIn.next(true);
        }
    }

    public logout() {
        this.mHttpClient.post(Endpoints.AUTHENTICATION.logout, {}).subscribe((_) => {
            this.mLoggedIn.next(false);
        });
    }
}
