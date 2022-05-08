import {AfterViewInit, Component, ElementRef, forwardRef, Input, OnInit, ViewChild} from '@angular/core';
import {AbstractControl, ControlValueAccessor, FormControl, NG_VALIDATORS, NG_VALUE_ACCESSOR, ValidationErrors, Validator, Validators} from '@angular/forms';
import {exists} from '../../../helper/app-util';
import {pushIfTrue} from '../../../utils/list-util';

export enum InputType {
    EMAIL = 'email',
    PASSWORD = 'password',
}

export interface TextInputConfig {
    formControl: FormControl;
    labelKey: string;
    placeholder?: string;
    hintKey?: string;
    value?: string;
    inputType?: InputType | string;
    idAttribute?: string;
    isRequired?: boolean;
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
export class TextInputComponent implements OnInit, ControlValueAccessor, Validator {
    @Input() public inputConfig!: TextInputConfig;
    @ViewChild('input') private mInputRef!: ElementRef<HTMLInputElement>;

    public config!: Required<TextInputConfig>;
    public isSelected: boolean = false;
    public isDisabled: boolean = false;

    public changed!: (value: any) => void;
    public touched!: () => void;

    public get hasError(): boolean {
        return this.showErrors && this.errorKeys.length > 0;
    }

    public get getPlaceholder(): string {
        return this.isSelected ? this.config.placeholder : '';
    }

    public get showErrors(): boolean {
        return this.config.formControl.invalid && (this.config.formControl.dirty || this.config.formControl.touched);
    }

    public get errorKeys(): ValidationError[] {
        const errors: ValidationError[] = [];
        const errorObject = this.config.formControl.errors as ValidationErrorParameters;

        if (this.config.inputType === InputType.EMAIL) {
            pushIfTrue(errors, errorObject.required, { key: 'input.requiredEmail' });
            pushIfTrue(errors, errorObject.email, { key: 'input.emailFormat' });
        } else if (this.config.inputType === InputType.PASSWORD) {
            pushIfTrue(errors, errorObject.required, { key: 'input.requiredPassword' });
            pushIfTrue(errors, errorObject.minlength, { key: 'input.passwordMinLength', parameters: { length: errorObject.minlength?.requiredLength }});
        }

        return errors;
    }

    public ngOnInit() {
        this.config = this.initConfigWithDefaultValues(this.inputConfig);
    }

    public selectInput() {
        this.mInputRef.nativeElement.select();
    }

    public onChange(value: string) {
        this.changed(value);
    }

    public onFocus() {
        this.isSelected = true;
    }

    public onTouch() {
        this.isSelected = false;
        this.touched();
    }

    public writeValue(value: any) {
        this.config.value = value;
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

    private initConfigWithDefaultValues(config: TextInputConfig): Required<TextInputConfig> {
        return {
            formControl: config.formControl,
            labelKey: config.labelKey,
            placeholder: config.placeholder ?? '',
            hintKey: config.placeholder ?? '',
            value: config.value ?? '',
            inputType: config.inputType ?? InputType.EMAIL,
            idAttribute: config.inputType ?? 'textInputLabel',
            isRequired: config.isRequired ?? false,
        };
    }
}

