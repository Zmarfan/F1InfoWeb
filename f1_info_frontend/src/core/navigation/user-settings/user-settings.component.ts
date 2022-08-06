import { Component } from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors} from '@angular/forms';
import {Session} from '../../../app/configuration/session';
import {cancelDialog} from '../../dialog/dialog';
import {MatDialogRef} from '@angular/material/dialog';
import {UserSettingsService} from './user-settings.service';
import {catchError, EMPTY, throwError} from 'rxjs';
import {HttpErrorResponse} from '@angular/common/http';

export interface UserSettings {
    displayName: string;
}

@Component({
    selector: 'app-user-settings',
    templateUrl: './user-settings.component.html',
    styleUrls: ['./user-settings.component.scss'],
})
export class UserSettingsComponent {
    private static readonly DISPLAY_NAME_MIN_LENGTH: number = 3;

    public displayName: FormControl;

    public formData: FormGroup;
    public errorKeys = { duplicateDisplayName: 'userSettings.duplicateDisplayNameError' };
    public loading: boolean = false;

    public constructor(
        private mDialogRef: MatDialogRef<UserSettingsComponent>,
        private mSession: Session,
        private mUserSettingsService: UserSettingsService
    ) {
        this.displayName = new FormControl(mSession.getUser?.displayName ?? '', [
            UserSettingsComponent.displayNamePaddingValidator,
            (control) => UserSettingsComponent.displayNameSameValueValidator(control, this.mSession),
        ]);
        this.formData = new FormGroup({ displayName: this.displayName });
    }

    private static displayNamePaddingValidator(control: AbstractControl): ValidationErrors | null {
        const onlySpaces = (control.value || '').trim().length === 0;
        return onlySpaces ? { required: true } : null;
    }

    private static displayNameSameValueValidator(control: AbstractControl, session: Session): ValidationErrors | null {
        return session.getUser?.displayName === control.value ? { duplicateDisplayName: true } : null;
    }

    public submitForm(formData: UserSettings) {
        this.mUserSettingsService.updateUserSettings({ displayName: formData.displayName.trim() })
            .subscribe({
                next: () => this.cancel(),
                error: (error: HttpErrorResponse) => console.log(error),
            });
    }

    public cancel() {
        cancelDialog(this.mDialogRef);
    }
}
