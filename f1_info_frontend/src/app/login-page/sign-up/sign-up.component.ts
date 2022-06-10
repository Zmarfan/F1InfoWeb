import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {finalize} from 'rxjs';
import {exists} from '../../../core/helper/app-util';
import {LoginSignUpService} from '../login-sign-up.service';
import {Session} from '../../configuration/session';
import {PageInformationConfig, PageInformationType} from '../../../core/information/page-information/page-information.component';
import {EnableUserErrorResponse} from '../../../generated/server-responses';
import {HttpErrorResponse} from '@angular/common/http';

export enum SignUpComponentType {
    SIGN_UP = 1,
    VERIFY = 2,
    VERIFIED = 3,
    TIMED_OUT_TOKEN = 4,
    ALREADY_VERIFIED = 5,
    UNEXPECTED_ERROR = 6
}

@Component({
    selector: 'app-sign-up',
    templateUrl: './sign-up.component.html',
})
export class SignUpComponent implements OnInit {
    private static readonly VERIFIED_CONFIG: PageInformationConfig = {
        type: PageInformationType.SUCCESS,
        titleKey: 'signUpPage.verifiedAccount.title',
        paragraphKeys: ['signUpPage.verifiedAccount.paragraph1'],
    };
    private static readonly TIMED_OUT_TOKEN_CONFIG: PageInformationConfig = {
        type: PageInformationType.ERROR,
        titleKey: 'signUpPage.timedOutToken.title',
        paragraphKeys: ['signUpPage.timedOutToken.paragraph1'],
    };
    private static readonly ALREADY_VERIFIED_CONFIG: PageInformationConfig = {
        type: PageInformationType.INFORMATION,
        titleKey: 'signUpPage.alreadyVerified.title',
        paragraphKeys: ['signUpPage.alreadyVerified.paragraph1'],
    };
    private static readonly UNEXPECTED_ERROR_CONFIG: PageInformationConfig = {
        type: PageInformationType.ERROR,
        titleKey: 'signUpPage.unExpectedError.title',
        paragraphKeys: ['signUpPage.unExpectedError.paragraph1'],
    };

    public loading: boolean = false;
    private mRegistrationEmail: string = '';
    private mType: SignUpComponentType = SignUpComponentType.SIGN_UP;
    private mToken: string = '';

    public constructor(
        private mRoute: ActivatedRoute,
        private mSession: Session,
        private mLoginSignUpService: LoginSignUpService
    ) {
    }

    public get showSignUp(): boolean {
        return this.mType === SignUpComponentType.SIGN_UP;
    }

    public get informationConfig(): PageInformationConfig {
        switch (this.mType) {
        case SignUpComponentType.VERIFY: return SignUpComponent.createVerifiedConfig(this.mRegistrationEmail);
        case SignUpComponentType.VERIFIED: return SignUpComponent.VERIFIED_CONFIG;
        case SignUpComponentType.ALREADY_VERIFIED: return SignUpComponent.ALREADY_VERIFIED_CONFIG;
        case SignUpComponentType.TIMED_OUT_TOKEN: return SignUpComponent.TIMED_OUT_TOKEN_CONFIG;
        default: return SignUpComponent.UNEXPECTED_ERROR_CONFIG;
        }
    }

    private static createVerifiedConfig(email: string): PageInformationConfig {
        return {
            type: PageInformationType.SUCCESS,
            titleKey: 'signUpPage.verifyAccount.title',
            titleParameters: { email },
            paragraphKeys: ['signUpPage.verifyAccount.paragraph1'],
        };
    }

    private static convertErrorResponseToMessageType(errorResponse: EnableUserErrorResponse): SignUpComponentType {
        switch (errorResponse.errorType) {
        case 'ALREADY_VERIFIED': return SignUpComponentType.ALREADY_VERIFIED;
        case 'TIMED_OUT': return SignUpComponentType.TIMED_OUT_TOKEN;
        default: return SignUpComponentType.UNEXPECTED_ERROR;
        }
    }

    public ngOnInit() {
        this.mRoute.queryParams.subscribe((params: any) => {
            this.assignVariables(params);
            if (this.mType === SignUpComponentType.VERIFIED) {
                this.enableAccount();
            }
        });
    }

    private assignVariables(params: { type: string, email: string, token: string }) {
        this.mType = exists(params.type) ? Number(params.type) : this.mType;
        this.mRegistrationEmail = params.email ?? this.mRegistrationEmail;
        this.mToken = params.token ?? this.mToken;
    }

    private enableAccount() {
        this.loading = true;
        this.mLoginSignUpService.enableAccount(this.mToken)
            .pipe(finalize(() => {
                this.loading = false;
            }))
            .subscribe({
                next: () => this.login(),
                error: (response: HttpErrorResponse) => {
                    this.mType = SignUpComponent.convertErrorResponseToMessageType(response.error);
                },
            });
    }

    private login() {
        this.mSession.login();
    }
}
