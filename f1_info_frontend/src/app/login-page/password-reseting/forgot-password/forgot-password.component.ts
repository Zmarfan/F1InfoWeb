import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {LoginSignUpService} from '../../login-sign-up.service';
import {UserDetails} from '../../login-sign-up/login-sign-up.component';
import {RouteHolder} from '../../../routing/routeHolder';
import {PageInformationConfig, PageInformationType} from '../../../../core/information/page-information/page-information.component';
import {finalize} from 'rxjs';

@Component({
    selector: 'app-forgot-password',
    templateUrl: './forgot-password.component.html',
    styleUrls: ['../../login-form-block.scss'],
})
export class ForgotPasswordComponent {
    public loading: boolean = false;
    public sentPasswordResetEmail: boolean = false;
    public messageConfig: PageInformationConfig = {
        type: PageInformationType.SUCCESS,
        titleKey: 'forgotPasswordPage.message.title',
        paragraphKeys: ['forgotPasswordPage.message.paragraph1'],
    };

    public email: FormControl = new FormControl('', [Validators.required, Validators.email]);

    public formData: FormGroup = new FormGroup({ email: this.email });
    public errorKeys = { unableToResetPassword: 'forgotPasswordPage.unableToReset' };

    public constructor(
        private mRouter: Router,
        private mLoginSignUpService: LoginSignUpService
    ) {
    }

    public submitForm(formData: UserDetails) {
        this.loading = true;
        this.mLoginSignUpService.forgotPassword(formData.email)
            .pipe(finalize(() => {
                this.loading = false;
            }))
            .subscribe({
                next: () => this.displaySuccessMessage(formData.email),
                error: () => this.handleFailedPasswordReset(),
            });
    }

    public routeBackToLogin() {
        this.mRouter.navigateByUrl(RouteHolder.LOGIN_PAGE).then();
    }

    private handleFailedPasswordReset() {
        this.email.setErrors({ unableToResetPassword: true });
    }

    private displaySuccessMessage(email: string) {
        this.sentPasswordResetEmail = true;
        this.messageConfig.titleParameters = { email };
    }
}
