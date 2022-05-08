import { Component } from '@angular/core';
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
    public email: FormControl = new FormControl('', [Validators.required, Validators.email]);
    public password: FormControl = new FormControl('', [Validators.required, Validators.minLength(8)]);

    public registrationForm: FormGroup = new FormGroup({
        email: this.email,
        password: this.password,
    });

    public constructor(
        private mRouter: Router
    ) {
    }

    public submitRegistrationForm(formData: RegistrationData) {
        console.log(formData);
    }

    public routeToLogin() {
        console.log("asd");
        this.mRouter.navigateByUrl(RouteHolder.LOGIN_PAGE).then();
    }
}
