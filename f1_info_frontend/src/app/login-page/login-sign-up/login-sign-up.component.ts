import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {RouteHolder} from '../../routing/routeHolder';
import {Router} from '@angular/router';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Endpoints} from '../../configuration/endpoints';
import {UserLoginResponse} from '../../../generated/server-responses';
import {catchError, Observable, tap, throwError} from 'rxjs';
import {LoginSignUpService} from '../login-sign-up.service';

interface LoginSignUpConfig {
    titleKey: string;
    emailHintKey: string;
    passwordHintKey: string;
    submitKey: string;
    endTextKey: string;
    routeTextKey: string;
}

export interface UserDetails {
    email: string;
    password: string;
}

@Component({
    selector: 'app-login-sign-up',
    templateUrl: './login-sign-up.component.html',
    styleUrls: ['./login-sign-up.component.scss'],
})
export class LoginSignUpComponent implements OnInit {
    private static readonly LOGIN_CONFIG: LoginSignUpConfig = {
        titleKey: 'loginPage.login',
        emailHintKey: 'loginPage.emailTip',
        passwordHintKey: 'loginPage.passwordTip',
        submitKey: 'loginPage.login',
        endTextKey: 'loginPage.needAnAccount',
        routeTextKey: 'loginPage.signUpHere',
    };
    private static readonly SIGN_UP_CONFIG: LoginSignUpConfig = {
        titleKey: 'signUpPage.signUp',
        emailHintKey: 'signUpPage.emailTip',
        passwordHintKey: 'signUpPage.passwordTip',
        submitKey: 'signUpPage.signUp',
        endTextKey: 'signUpPage.alreadyAUser',
        routeTextKey: 'signUpPage.loginHere',
    };

    @Input() public isSignUp: boolean = true;

    public email: FormControl = new FormControl('', [Validators.required, Validators.email]);
    public password: FormControl = new FormControl('', [Validators.required, Validators.minLength(8)]);

    public formData: FormGroup = new FormGroup({ email: this.email, password: this.password });

    public errorKeys = {
        invalidCredentials: 'loginPage.invalidCredentials',
        disabledAccount: 'loginPage.disabledAccount',
        failedRegistration: 'signUpPage.failedRegistration',
        unexpectedError: 'unexpectedError',
    };
    public config: LoginSignUpConfig = LoginSignUpComponent.SIGN_UP_CONFIG;

    public constructor(
        private mRouter: Router,
        private mLoginSignUpService: LoginSignUpService
    ) {
    }

    public ngOnInit() {
        this.config = this.isSignUp ? LoginSignUpComponent.SIGN_UP_CONFIG : LoginSignUpComponent.LOGIN_CONFIG;
    }

    public submitForm(formData: UserDetails) {
        if (this.isSignUp) {
            this.signUp(formData);
        } else {
            this.login(formData);
        }
    }

    public route() {
        this.mRouter.navigateByUrl(this.isSignUp ? RouteHolder.LOGIN_PAGE : RouteHolder.SIGN_UP_PAGE).then();
    }

    private login(formData: UserDetails) {
        this.mLoginSignUpService.login(formData).subscribe({
            next: (_) => console.log('YES'),
            error: (error: HttpErrorResponse) => this.handleFailedLogin(error.error),
        });
    }

    private signUp(formData: UserDetails) {
        this.mLoginSignUpService.register(formData).subscribe({
            next: (_) => console.log('YES'),
            error: (_) => this.handleFailedRegister(),
        });
    }

    private handleFailedLogin(response: UserLoginResponse) {
        if (response.responseType === 'INVALID_CREDENTIALS') {
            this.email.setErrors({ invalidCredentials: true });
        } else if (response.responseType === 'DISABLED') {
            this.email.setErrors({ disabledAccount: true });
        } else {
            this.email.setErrors({ unexpectedError: true });
        }
    }

    private handleFailedRegister() {
        this.email.setErrors({ failedRegistration: true });
    }
}
