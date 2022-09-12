import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {RouteHolder} from '../../routing/route-holder';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {LoginResponse, UserLoginResponse} from '../../../generated/server-responses';
import {LoginSignUpService} from '../login-sign-up.service';
import {Session} from '../../configuration/session';
import {SignUpComponentType} from '../sign-up/sign-up.component';
import {finalize} from 'rxjs';
import {CsrfTokenHolder} from '../../configuration/csrf-token-holder';

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
    styleUrls: ['../login-form-block.scss'],
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

    public loading: boolean = false;
    public email: FormControl = new FormControl('', [Validators.required, Validators.email]);
    public password: FormControl = new FormControl('', [Validators.required, Validators.minLength(8)]);

    public formData: FormGroup = new FormGroup({ email: this.email, password: this.password });

    public errorKeys = {
        invalidCredentials: 'loginPage.invalidCredentials',
        tooManyRequests: 'loginPage.tooManyRequests',
        disabledAccount: 'loginPage.disabledAccount',
        failedRegistration: 'signUpPage.failedRegistration',
        unexpectedError: 'unexpectedError',
    };
    public config: LoginSignUpConfig = LoginSignUpComponent.SIGN_UP_CONFIG;

    public constructor(
        private mRouter: Router,
        private mSession: Session,
        private mLoginSignUpService: LoginSignUpService,
        private mCsrfTokenHolder: CsrfTokenHolder
    ) {
    }

    public ngOnInit() {
        this.config = this.isSignUp ? LoginSignUpComponent.SIGN_UP_CONFIG : LoginSignUpComponent.LOGIN_CONFIG;
    }

    public submitForm(formData: UserDetails) {
        this.loading = true;
        if (this.isSignUp) {
            this.signUp(formData);
        } else {
            this.login(formData);
        }
    }

    public route() {
        this.mRouter.navigateByUrl(this.isSignUp ? RouteHolder.LOGIN_PAGE : RouteHolder.SIGN_UP_PAGE).then();
    }

    public forgotPassword() {
        this.mRouter.navigateByUrl(RouteHolder.FORGOT_PASSWORD_PAGE).then();
    }

    private login(formData: UserDetails) {
        this.mLoginSignUpService.login(formData)
            .pipe(finalize(() => {
                this.loading = false;
            }))
            .subscribe({
                next: (loginResponse) => this.loginClient(loginResponse as LoginResponse),
                error: (error: HttpErrorResponse) => this.handleFailedLogin(error.error),
            });
    }

    private signUp(formData: UserDetails) {
        this.mLoginSignUpService.register(formData)
            .pipe(finalize(() => {
                this.loading = false;
            }))
            .subscribe({
                next: (_) => this.navigateToVerifyRegistrationMessage(formData.email),
                error: (_) => this.handleFailedRegister(),
            });
    }

    private handleFailedLogin(response: UserLoginResponse) {
        this.password.setValue('');

        if (response.responseType === 'INVALID_CREDENTIALS') {
            this.password.setErrors({ invalidCredentials: true });
        } else if (response.responseType === 'DISABLED') {
            this.password.setErrors({ disabledAccount: true });
        } else if (response.responseType === 'TOO_MANY_REQUESTS') {
            this.password.setErrors({ tooManyRequests: true });
        } else {
            this.password.setErrors({ unexpectedError: true });
        }
    }

    private navigateToVerifyRegistrationMessage(email: string) {
        this.loading = false;
        this.mRouter.navigate([RouteHolder.SIGN_UP_PAGE], { queryParams: { type: SignUpComponentType.VERIFY, email } }).then();
    }

    private handleFailedRegister() {
        this.email.setErrors({ failedRegistration: true });
    }

    private loginClient(loginResponse: LoginResponse) {
        this.mCsrfTokenHolder.setToken(loginResponse.csrfToken);
        this.mSession.login();
        this.mRouter.navigateByUrl(RouteHolder.HOMEPAGE).then();
    }
}
