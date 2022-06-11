import {Injectable, OnInit} from '@angular/core';
import {AppRoutingModule} from '../routing/app-routing.module';
import {HttpClient} from '@angular/common/http';
import {Endpoints} from './endpoints';
import {Observable, startWith, Subject} from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class Session {
    private mLoggedIn: Subject<boolean> = new Subject<boolean>();
    private readonly mLoggedIn$;

    public constructor(
        private mRoutingModule: AppRoutingModule,
        private mHttpClient: HttpClient
    ) {
        this.mLoggedIn$ = this.mLoggedIn.asObservable();

        this.mHttpClient.get<boolean>(Endpoints.AUTHENTICATION.isLoggedIn)
            .pipe(startWith(false))
            .subscribe((loggedIn) => {
                this.mLoggedIn.next(loggedIn);
            });
    }

    public get isLoggedIn(): Observable<boolean> {
        return this.mLoggedIn$;
    }

    public login() {
        this.mLoggedIn.next(true);
        this.mRoutingModule.setupLoggedInRoutes();
    }

    public logout() {
        this.mHttpClient.post(Endpoints.AUTHENTICATION.logout, {}).subscribe((_) => {
            this.mLoggedIn.next(false);
            this.mRoutingModule.setupAnonymousRoutes();
        });
    }
}
