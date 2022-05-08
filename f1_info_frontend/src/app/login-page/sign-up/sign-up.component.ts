import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

interface RegistrationData {
    email: string;
    password: string;
}

@Component({
    selector: 'app-sign-up',
    templateUrl: './sign-up.component.html',
    styleUrls: ['./sign-up.component.scss'],
})
export class SignUpComponent {
    public email: FormControl = new FormControl('', [Validators.required, Validators.email]);
    public password: FormControl = new FormControl('', [Validators.required, Validators.minLength(8)]);

    public registrationForm: FormGroup = new FormGroup({
        email: this.email,
        password: this.password,
    });

    public submitRegistrationForm(formData: RegistrationData) {
        console.log(formData);
    }
}
