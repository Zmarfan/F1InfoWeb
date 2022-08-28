import { Component } from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {cancelDialog, closeDialog} from '../../../../../core/dialog/dialog';

@Component({
    selector: 'app-feedback-delete',
    templateUrl: './feedback-delete.component.html',
    styleUrls: ['./feedback-delete.component.scss'],
})
export class FeedbackDeleteComponent {
    public constructor(
        private mDialogRef: MatDialogRef<FeedbackDeleteComponent>
    ) {
    }

    public cancel() {
        cancelDialog(this.mDialogRef);
    }

    public apply() {
        closeDialog(this.mDialogRef);
    }
}
