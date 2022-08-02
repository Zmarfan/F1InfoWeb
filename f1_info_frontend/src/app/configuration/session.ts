import {Injectable, OnInit} from '@angular/core';
import {AppRoutingModule} from '../routing/app-routing.module';
import {HttpClient} from '@angular/common/http';
import {Endpoints} from './endpoints';
import {map, Observable, startWith, Subject, tap} from 'rxjs';
import {Router} from '@angular/router';
import {RouteHolder} from '../routing/route-holder';
import {GetUserResponse} from '../../generated/server-responses';

export type User = GetUserResponse;

@Injectable({
    providedIn: 'root',
})
export class Session {
    private mUser: Subject<User | null> = new Subject<User | null>();
    private readonly mUser$: Observable<User | null>;
    private mClientLoggedInStatus: boolean = false;

    public constructor(
        private mRouter: Router,
        private mHttpClient: HttpClient
    ) {
        this.mUser$ = this.mUser.asObservable();
        this.fetchUser();
    }

    public get isLoggedIn(): Observable<boolean> {
        return this.mUser$.pipe(map((user) => user !== null));
    }

    public get user(): Observable<User> {
        return this.mUser$.pipe(map((user) => user === null ? {} as User : user));
    }

    public login() {
        if (!this.mClientLoggedInStatus) {
            this.fetchUser();
        }
    }

    public logout() {
        this.mHttpClient.post(Endpoints.AUTHENTICATION.logout, {}).subscribe((_) => {
            this.mClientLoggedInStatus = false;
            this.mUser.next(null);
        });
    }

    private fetchUser() {
        this.mHttpClient.get<GetUserResponse>(Endpoints.AUTHENTICATION.getUser)
            .pipe(startWith(null))
            .subscribe((userResponse) => {
                this.mClientLoggedInStatus = userResponse !== null;
                this.mUser.next(userResponse);
            });
    }
}
