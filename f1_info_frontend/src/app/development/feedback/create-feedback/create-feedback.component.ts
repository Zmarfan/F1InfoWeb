import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MatDialogRef} from '@angular/material/dialog';
import {cancelDialog, closeDialog} from '../../../../core/dialog/dialog';
import {ValidatorFactory} from '../../../../core/utils/validator-factory';

@Component({
    selector: 'app-create-feedback',
    templateUrl: './create-feedback.component.html',
    styleUrls: ['./create-feedback.component.scss'],
})
export class CreateFeedbackComponent {
    public text: FormControl = new FormControl('', [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(255),
        ValidatorFactory.noPaddingValidator,
    ]);

    public formData: FormGroup = new FormGroup({ text: this.text });

    public constructor(
        private mDialogRef: MatDialogRef<CreateFeedbackComponent>
    ) {
    }

    public submitForm(formData: { text: string }) {
        closeDialog(this.mDialogRef, formData.text);
    }

    public cancel() {
        cancelDialog(this.mDialogRef);
    }
}
