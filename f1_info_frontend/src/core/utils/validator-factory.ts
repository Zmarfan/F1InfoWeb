import {AbstractControl, ValidationErrors} from '@angular/forms';

export class ValidatorFactory {
    public static notOnlySpaces(control: AbstractControl): ValidationErrors | null {
        const onlySpaces = (control.value || '').trim().length === 0;
        return onlySpaces ? { required: true } : null;
    }
}
