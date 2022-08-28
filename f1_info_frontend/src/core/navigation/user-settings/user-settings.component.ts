import {Component} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {Session} from '../../../app/configuration/session';
import {cancelDialog} from '../../dialog/dialog';
import {MatDialogRef} from '@angular/material/dialog';
import {UserSettingsService} from './user-settings.service';
import {HttpErrorResponse} from '@angular/common/http';
import {GlobalMessageService, GlobalMessageType} from '../../information/global-message-display/global-message.service';
import {TranslateService} from '@ngx-translate/core';
import {ValidatorFactory} from '../../utils/validator-factory';

export interface UserSettings {
    displayName: string;
}

@Component({
    selector: 'app-user-settings',
    templateUrl: './user-settings.component.html',
    styleUrls: ['./user-settings.component.scss'],
})
export class UserSettingsComponent {
    private static readonly MAX_DISPLAY_NAME_LENGTH: number = 20;

    public displayName: FormControl;

    public formData: FormGroup;
    public errorKeys = { duplicateDisplayName: 'userSettings.duplicateDisplayNameError' };
    public loading: boolean = false;

    public constructor(
        private mDialogRef: MatDialogRef<UserSettingsComponent>,
        private mSession: Session,
        private mUserSettingsService: UserSettingsService,
        private mMessageService: GlobalMessageService,
        private mTranslate: TranslateService
    ) {
        this.displayName = new FormControl(mSession.user?.displayName ?? '', [
            Validators.maxLength(UserSettingsComponent.MAX_DISPLAY_NAME_LENGTH),
            ValidatorFactory.noPaddingValidator,
            (control) => UserSettingsComponent.displayNameSameValueValidator(control, this.mSession),
        ]);
        this.formData = new FormGroup({ displayName: this.displayName });
    }

    private static displayNameSameValueValidator(control: AbstractControl, session: Session): ValidationErrors | null {
        return session.user?.displayName === control.value ? { duplicateDisplayName: true } : null;
    }

    public submitForm(formData: UserSettings) {
        this.loading = true;
        this.mUserSettingsService.updateUserSettings({ displayName: formData.displayName.trim() })
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
