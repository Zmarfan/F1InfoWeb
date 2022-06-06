import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {LoginSignUpService} from '../login-sign-up.service';
import {UserDetails} from '../login-sign-up/login-sign-up.component';
import {RouteHolder} from '../../routing/routeHolder';

@Component({
    selector: 'app-forgot-password',
    templateUrl: './forgot-password.component.html',
    styleUrls: ['../login-sign-up/login-sign-up.component.scss'],
})
export class ForgotPasswordComponent {
    public loading: boolean = false;
    public email: FormControl = new FormControl('', [Validators.required, Validators.email]);

    public formData: FormGroup = new FormGroup({ email: this.email });

    public constructor(
        private mRouter: Router,
        private mLoginSignUpService: LoginSignUpService
    ) {
    }

    public submitForm(formData: UserDetails) {
        this.loading = true;
    }

    public routeBackToLogin() {
        this.mRouter.navigateByUrl(RouteHolder.LOGIN_PAGE).then();
    }
}
