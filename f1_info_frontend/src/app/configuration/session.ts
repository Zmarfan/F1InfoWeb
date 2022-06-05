import {Injectable, OnInit} from '@angular/core';
import {AppRoutingModule} from '../routing/app-routing.module';
import {HttpClient} from '@angular/common/http';
import {Endpoints} from './endpoints';

@Injectable({
    providedIn: 'root',
})
export class Session {
    private mLoggedIn: boolean = false;

    public constructor(
        private mRoutingModule: AppRoutingModule,
        private mHttpClient: HttpClient
    ) {
        this.mHttpClient.get<boolean>(Endpoints.AUTHENTICATION.isLoggedIn).subscribe((loggedIn) => {
            this.mLoggedIn = loggedIn;
        });
    }

    public get isLoggedIn(): boolean {
        return this.mLoggedIn;
    }

    public login() {
        this.mLoggedIn = true;
        this.mRoutingModule.setupLoggedInRoutes();
    }

    public logout() {
        this.mHttpClient.post(Endpoints.AUTHENTICATION.logout, {}).subscribe((_) => {
            this.mLoggedIn = false;
            this.mRoutingModule.setupAnonymousRoutes();
        });
    }
}
