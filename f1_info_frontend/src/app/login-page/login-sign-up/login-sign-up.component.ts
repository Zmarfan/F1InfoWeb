import {Component, Input} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {RouteHolder} from '../../routing/routeHolder';
import {Router} from '@angular/router';

interface RegistrationData {
    email: string;
    password: string;
}

@Component({
    selector: 'app-login-sign-up',
    templateUrl: './login-sign-up.component.html',
    styleUrls: ['./login-sign-up.component.scss'],
})
export class LoginSignUpComponent {
    @Input() public isSignUp: boolean = true;

    public email: FormControl = new FormControl('', [Validators.required, Validators.email]);
    public password: FormControl = new FormControl('', [Validators.required, Validators.minLength(8)]);

    public formData: FormGroup = new FormGroup({ email: this.email, password: this.password });

    public constructor(
        private mRouter: Router
    ) {
    }

    public get titleKey(): string {
        return this.isSignUp ? 'signUpPage.signUp' : 'loginPage.login';
    }

    public get emailHintKey(): string {
        return this.isSignUp ? 'signUpPage.emailTip' : 'loginPage.emailTip';
    }

    public get passwordHintKey(): string {
        return this.isSignUp ? 'signUpPage.passwordTip' : 'loginPage.passwordTip';
    }

    public get submitKey(): string {
        return this.isSignUp ? 'signUpPage.signUp' : 'loginPage.login';
    }

    public get endText(): string {
        return this.isSignUp ? 'signUpPage.alreadyAUser' : 'loginPage.needAnAccount';
    }

    public get routeText(): string {
        return this.isSignUp ? 'signUpPage.loginHere' : 'loginPage.signUpHere';
    }

    public submitForm(formData: RegistrationData) {
        console.log(formData);
    }

    public route() {
        this.mRouter.navigateByUrl(this.isSignUp ? RouteHolder.LOGIN_PAGE : RouteHolder.SIGN_UP_PAGE).then();
    }
}
