import {Injectable, OnInit} from '@angular/core';
import {AppRoutingModule} from '../routing/app-routing.module';
import {HttpClient} from '@angular/common/http';
import {Endpoints} from './endpoints';
import {forkJoin, map, Observable, startWith, Subject, tap} from 'rxjs';
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
    private mLastUser: User | null = null;
    private mLastLogoSrc: string | null = null;

    public constructor(
        private mHttpClient: HttpClient
    ) {
        this.mUser$ = this.mUser.asObservable();
        this.fetchUser();
    }

    public get isLoggedIn(): Observable<boolean> {
        return this.mUser$.pipe(map((user) => user !== null));
    }

    public get user(): User | null {
        return this.mLastUser;
    }

    public get userLogoSrc(): string {
        return this.mLastUser !== null ? Endpoints.USER.getProfileIcon : 'https://picsum.photos/100/100';
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
            this.mLastUser = null;
        });
    }

    public fetchUser() {
        this.mHttpClient.get<GetUserResponse>(Endpoints.AUTHENTICATION.getUser)
            .pipe(startWith(null))
            .subscribe((userResponse) => {
                this.mClientLoggedInStatus = userResponse !== null;
                this.mUser.next(userResponse);
                this.mLastUser = userResponse;
            });
    }
}
