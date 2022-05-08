import {AfterViewInit, Component, ElementRef, forwardRef, Input, ViewChild} from '@angular/core';
import {AbstractControl, ControlValueAccessor, FormControl, NG_VALIDATORS, NG_VALUE_ACCESSOR, ValidationErrors, Validator, Validators} from '@angular/forms';
import {exists} from '../../../helper/app-util';
import {pushIfTrue} from '../../../utils/list-util';

export enum InputType {
    TEXT = 'text',
    PASSWORD = 'password',
}

interface ValidationErrorParameters {
    required?: boolean;
    email?: boolean;
    minlength?: { requiredLength: number, actualLength: number };
}

interface ValidationError {
    key: string;
    parameters?: { [key: string]: any };
}

@Component({
    selector: 'app-text-input',
    templateUrl: './text-input.component.html',
    styleUrls: ['./text-input.component.scss'],
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            // eslint-disable-next-line no-use-before-define
            useExisting: forwardRef(() => TextInputComponent),
            multi: true,
        },
        {
            provide: NG_VALIDATORS,
            // eslint-disable-next-line no-use-before-define
            useExisting: forwardRef(() => TextInputComponent),
            multi: true,
        },
    ],
})
export class TextInputComponent implements ControlValueAccessor, Validator {
    @Input() public formControl!: FormControl;
    @Input() public value: string = '';
    @Input() public inputType: InputType | string = InputType.TEXT;
    @Input() public idAttribute: string = 'textInputId';
    @Input() public label!: string;
    @Input() public inputPlaceholder: string = '';
    @Input() public isRequired: boolean = false;

    @ViewChild('input') private mInputRef!: ElementRef;

    public isDisabled: boolean = false;
    public errors: string[] = [];

    public changed!: (value: any) => void;
    public touched!: () => void;

    public get showErrors(): boolean {
        return this.formControl.invalid && (this.formControl.dirty || this.formControl.touched);
    }

    public get errorKeys(): ValidationError[] {
        const errors: ValidationError[] = [];
        const errorObject = this.formControl.errors as ValidationErrorParameters;

        if (this.inputType === InputType.TEXT) {
            pushIfTrue(errors, errorObject.required, { key: 'input.requiredEmail' });
            pushIfTrue(errors, errorObject.email, { key: 'input.emailFormat' });
        } else if (this.inputType === InputType.PASSWORD) {
            pushIfTrue(errors, errorObject.required, { key: 'input.requiredPassword' });
            pushIfTrue(errors, errorObject.minlength, { key: 'input.passwordMinLength', parameters: { length: errorObject.minlength?.requiredLength }});
        }

        return errors;
    }

    public onChange(value: string) {
        this.changed(value);
    }

    public writeValue(value: any) {
        this.value = value;
    }

    public registerOnChange(callbackFunction: any) {
        this.changed = callbackFunction;
    }

    public registerOnTouched(callbackFunction: any) {
        this.touched = callbackFunction;
    }

    public setDisabledState(isDisabled: boolean) {
        this.isDisabled = isDisabled;
    }

    public validate(control: AbstractControl): ValidationErrors | null {
        return null;
    }
}

