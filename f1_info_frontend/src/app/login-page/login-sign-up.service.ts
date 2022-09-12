import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserDetails} from './login-sign-up/login-sign-up.component';
import {Endpoints} from '../configuration/endpoints';
import {Observable} from 'rxjs';
import {LoginResponse, UserLoginResponse} from '../../generated/server-responses';
import {parseTemplate} from 'url-template';

@Injectable({
    providedIn: 'root',
})
export class LoginSignUpService {

    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public login(formData: UserDetails): Observable<UserLoginResponse | LoginResponse> {
        return this.mHttpClient.post<UserLoginResponse>(Endpoints.AUTHENTICATION.login, formData);
    }

    public register(formData: UserDetails): Observable<Object> {
        return this.mHttpClient.put(Endpoints.AUTHENTICATION.register, formData);
    }

    public enableAccount(token: string): Observable<Object> {
        return this.mHttpClient.post(parseTemplate(Endpoints.AUTHENTICATION.enableAccount).expand({ token }), {});
    }

    public forgotPassword(email: string): Observable<Object> {
        return this.mHttpClient.post(Endpoints.AUTHENTICATION.forgotPassword, { email });
    }

    public resetPassword(password: string, token: string): Observable<Object> {
        return this.mHttpClient.post(parseTemplate(Endpoints.AUTHENTICATION.resetPassword).expand({ token }), { password });
    }
}
