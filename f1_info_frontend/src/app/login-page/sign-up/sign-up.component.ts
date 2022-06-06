import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {finalize, Subscription, tap} from 'rxjs';
import {exists} from '../../../core/helper/app-util';
import {LoginSignUpService} from '../login-sign-up.service';
import {Session} from '../../configuration/session';

export enum SignUpComponentType {
    SIGN_UP = 1,
    VERIFY = 2,
    VERIFIED = 3,
    ERROR = 4
}

@Component({
    selector: 'app-sign-up',
    templateUrl: './sign-up.component.html',
    styleUrls: ['./sign-up.component.scss'],
})
export class SignUpComponent implements OnInit, OnDestroy {
    public registrationEmail: string = '';
    public loading: boolean = false;
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

    public get showVerify(): boolean {
        return this.mType === SignUpComponentType.VERIFY;
    }

    public get showVerified(): boolean {
        return this.mType === SignUpComponentType.VERIFIED;
    }

    public get showError(): boolean {
        return this.mType === SignUpComponentType.ERROR;
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
        this.registrationEmail = params.email ?? this.registrationEmail;
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
