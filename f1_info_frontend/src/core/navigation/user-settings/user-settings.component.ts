import {Component, OnDestroy, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {Session} from '../../../app/configuration/session';
import {cancelDialog} from '../../dialog/dialog';
import {MatDialogRef} from '@angular/material/dialog';
import {UserSettingsService} from './user-settings.service';
import {HttpErrorResponse} from '@angular/common/http';
import {GlobalMessageService} from '../../information/global-message-display/global-message.service';
import {TranslateService} from '@ngx-translate/core';
import {ValidatorFactory} from '../../utils/validator-factory';
import {Subscription} from 'rxjs';

export interface UserSettings {
    displayName: string;
}

interface ImageFileData {
    src: string;
    name?: string;
}

@Component({
    selector: 'app-user-settings',
    templateUrl: './user-settings.component.html',
    styleUrls: ['./user-settings.component.scss'],
})
export class UserSettingsComponent implements OnInit, OnDestroy {
    private static readonly MAX_DISPLAY_NAME_LENGTH: number = 20;

    public displayName: FormControl;

    public formData: FormGroup;
    public errorKeys = { duplicateDisplayName: 'userSettings.duplicateDisplayNameError' };
    public loading: boolean = false;

    public profileIconData!: ImageFileData;
    private mSelectedFile?: File;
    private mSubscription!: Subscription;

    public constructor(
        private mDialogRef: MatDialogRef<UserSettingsComponent>,
        private mSession: Session,
        private mUserSettingsService: UserSettingsService,
        private mMessageService: GlobalMessageService,
        private mTranslate: TranslateService
    ) {
        this.displayName = new FormControl(mSession.user?.displayName ?? '', [
            Validators.maxLength(UserSettingsComponent.MAX_DISPLAY_NAME_LENGTH),
            ValidatorFactory.notOnlySpaces,
            (control) => UserSettingsComponent.displayNameSameValueValidator(control, this.mSession),
        ]);
        this.formData = new FormGroup({ displayName: this.displayName });
    }

    public get canSaveChanges(): boolean {
        return this.displayName.valid || this.mSelectedFile !== undefined;
    }

    private static displayNameSameValueValidator(control: AbstractControl, session: Session): ValidationErrors | null {
        return session.user?.displayName === control.value ? { duplicateDisplayName: true } : null;
    }

    public ngOnInit() {
        this.profileIconData = { src: this.mSession.userLogoSrc };
        this.mSubscription = this.mSession.isLoggedIn.subscribe(() => {
            this.profileIconData = { src: this.mSession.userLogoSrc };
        });
    }

    public ngOnDestroy() {
        this.mSubscription.unsubscribe();
    }

    public selectProfilePicture(event: any): void {
        const files: FileList = event.target.files;
        if (!files || files.length === 0) {
            return;
        }
        const upload: File = files[0];
        const reader = new FileReader();
        reader.readAsDataURL(upload);
        reader.onload = (_) => {
            this.profileIconData = {
                name: upload.name,
                src: reader.result as string,
            };
        };
        this.mSelectedFile = upload;
    }

    public submitForm(formData: UserSettings) {
        this.loading = true;
        this.mUserSettingsService.updateUserSettings({
            shouldUpdateDisplayName: this.displayName.valid,
            displayName: this.displayName.valid ? formData.displayName.trim() : '',
            icon: this.mSelectedFile,
        })
            .subscribe({
                next: () => this.successfullyUpdated(),
                error: (error: HttpErrorResponse) => this.handleUpdateError(error),
            });
    }

    public cancel() {
        cancelDialog(this.mDialogRef);
    }

    private handleUpdateError(error: HttpErrorResponse) {
        this.loading = false;
        this.mMessageService.addHttpError(error);
    }

    private successfullyUpdated() {
        this.mSession.fetchUser();
        this.mMessageService.addSuccess(this.mTranslate.instant('userSettings.successUpdate'));
        this.cancel();
    }
}
