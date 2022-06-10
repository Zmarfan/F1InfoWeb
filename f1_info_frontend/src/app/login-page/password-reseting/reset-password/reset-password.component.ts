import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {LoginSignUpService} from '../../login-sign-up.service';
import {UserDetails} from '../../login-sign-up/login-sign-up.component';
import {RouteHolder} from '../../../routing/routeHolder';
import {PageInformationConfig, PageInformationType} from '../../../../core/information/page-information/page-information.component';
import {finalize} from 'rxjs';
import {HttpErrorResponse} from '@angular/common/http';
import {TokenUserErrorResponse} from '../../../../generated/server-responses';

@Component({
    selector: 'app-reset-password',
    templateUrl: './reset-password.component.html',
    styleUrls: ['../../login-form-block.scss'],
})
export class ResetPasswordComponent implements OnInit {
    public loading: boolean = false;
    public showSuccessMessage: boolean = false;
    public successMessageConfig: PageInformationConfig = { type: PageInformationType.SUCCESS, titleKey: 'resetPasswordPage.messageTitle' };
    public errorKeys = { timedOutToken: 'resetPasswordPage.timedOutToken', unExpectedError: 'resetPasswordPage.unExpectedError' };

    public password: FormControl = new FormControl('', [Validators.required, Validators.minLength(8)]);

    public formData: FormGroup = new FormGroup({ password: this.password });

    private mToken!: string;

    public constructor(
        private mRouter: Router,
        private mRoute: ActivatedRoute,
        private mLoginSignUpService: LoginSignUpService
    ) {
    }

    public ngOnInit() {
        this.mRoute.queryParams.subscribe((params: any) => {
            this.mToken = params.token;
        });
    }

    public submitForm(formData: UserDetails) {
        this.loading = true;
        this.mLoginSignUpService.resetPassword(formData.password, this.mToken)
            .pipe(finalize(() => {
                this.loading = false;
            }))
            .subscribe({
                next: () => {
                    this.showSuccessMessage = true;
                },
                error: (response: HttpErrorResponse) => this.handleErrorResponse(response.error)
            });
    }

    public routeBackToLogin() {
        this.mRouter.navigateByUrl(RouteHolder.LOGIN_PAGE).then();
    }

    private handleErrorResponse(response: TokenUserErrorResponse) {
        if (response.errorType === 'TIMED_OUT') {
            this.password.setErrors({ timedOutToken: true });
        } else {
            this.password.setErrors({ unExpectedError: true });
        }
    }
}
