import { Component } from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {UserDetails} from '../../../app/login-page/login-sign-up/login-sign-up.component';
import {Session} from '../../../app/configuration/session';
import {map, Observable} from 'rxjs';
import {cancelDialog} from '../../dialog/dialog';
import {MatDialogRef} from '@angular/material/dialog';

interface UserSettings {
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

    public constructor(
        private mDialogRef: MatDialogRef<UserSettingsComponent>,
        private mSession: Session
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

    public submitForm(formData: UserDetails) {
        console.log('Hje');
    }

    public cancel() {
        cancelDialog(this.mDialogRef);
    }
}
