import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {LoginSignUpService} from '../../login-sign-up.service';
import {UserDetails} from '../../login-sign-up/login-sign-up.component';
import {RouteHolder} from '../../../routing/routeHolder';

@Component({
    selector: 'app-reset-password',
    templateUrl: './reset-password.component.html',
    styleUrls: ['../../login-form-block.scss'],
})
export class ResetPasswordComponent {
    public loading: boolean = false;
    public password: FormControl = new FormControl('', [Validators.required, Validators.minLength(8)]);

    public formData: FormGroup = new FormGroup({ password: this.password });

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
