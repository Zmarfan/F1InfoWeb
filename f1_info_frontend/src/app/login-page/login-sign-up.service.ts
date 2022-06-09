import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {UserDetails} from './login-sign-up/login-sign-up.component';
import {Endpoints} from '../configuration/endpoints';
import {Observable} from 'rxjs';
import {EnableUserErrorResponse, UserLoginResponse} from '../../generated/server-responses';
import {parseTemplate} from 'url-template';

@Injectable({
    providedIn: 'root',
})
export class LoginSignUpService {

    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public login(formData: UserDetails): Observable<UserLoginResponse> {
        return this.mHttpClient.post<UserLoginResponse>(Endpoints.AUTHENTICATION.login, formData);
    }

    public register(formData: UserDetails): Observable<Object> {
        return this.mHttpClient.post(Endpoints.AUTHENTICATION.register, formData);
    }

    public enableAccount(token: string): Observable<Object> {
        return this.mHttpClient.post(parseTemplate(Endpoints.AUTHENTICATION.enableAccount).expand({ token }), {});
    }
}
