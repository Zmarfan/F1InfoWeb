import { Component } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {ShowOnDirtyErrorStateMatcher} from '@angular/material/core';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
    public emailFormControl = new FormControl('', [Validators.required, Validators.email]);
    public passwordFormControl = new FormControl('', [Validators.required, Validators.minLength(8)]);
    public matcher = new ShowOnDirtyErrorStateMatcher();

    public get emailFormatError(): boolean {
        return this.emailFormControl.hasError('email') && !this.emailFormControl.hasError('required');
    }
}
