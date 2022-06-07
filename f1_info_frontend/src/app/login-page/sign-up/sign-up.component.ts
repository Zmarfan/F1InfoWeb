import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {finalize, Subscription} from 'rxjs';
import {exists} from '../../../core/helper/app-util';
import {LoginSignUpService} from '../login-sign-up.service';
import {Session} from '../../configuration/session';
import {PageInformationConfig, PageInformationType} from '../../../core/information/page-information/page-information.component';

export enum SignUpComponentType {
    SIGN_UP = 1,
    VERIFY = 2,
    VERIFIED = 3,
    ERROR = 4
}

@Component({
    selector: 'app-sign-up',
    templateUrl: './sign-up.component.html',
})
export class SignUpComponent implements OnInit, OnDestroy {
    public loading: boolean = false;
    private mRegistrationEmail: string = '';
    private mType: SignUpComponentType = SignUpComponentType.SIGN_UP;
    private mToken: string = '';
    private mSubscription!: Subscription;

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
        if (this.mType === SignUpComponentType.VERIFY) {
            return {
                type: PageInformationType.SUCCESS,
                titleKey: 'signUpPage.verifyAccount.title',
                titleParameters: { email: this.mRegistrationEmail },
                paragraphKeys: ['signUpPage.verifyAccount.paragraph1'],
            };
        }
        if (this.mType === SignUpComponentType.VERIFIED) {
            return {
                type: PageInformationType.SUCCESS,
                titleKey: 'signUpPage.verifiedAccount.title',
                paragraphKeys: ['signUpPage.verifiedAccount.paragraph1'],
            };
        }
        return {
            type: PageInformationType.ERROR,
            titleKey: 'signUpPage.errorEnabling.title',
            paragraphKeys: ['signUpPage.errorEnabling.paragraph1'],
        };
    }

    public ngOnInit() {
        this.mSubscription = this.mRoute.queryParams.subscribe((params: any) => {
            this.assignVariables(params);
            if (this.mType === SignUpComponentType.VERIFIED) {
                this.enableAccount();
            }
        });
    }

    public ngOnDestroy() {
        this.mSubscription.unsubscribe();
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
                next: (_) => this.login(),
                error: (_) => {
                    this.mType = SignUpComponentType.ERROR;
                },
            });
    }

    private login() {
        this.mSession.login();
    }
}
