import {MatDialogRef} from '@angular/material/dialog';

export interface DialogResult<T = any> {
    result?: T;
    wasApplied: boolean | undefined;
}

export function cancelDialog<T, R = any>(dialogRef: MatDialogRef<T>, result?: R) {
    return dialogRef.close({ wasApplied: false, result: result } as DialogResult);
}

export function closeDialog<T, R = any>(dialogRef: MatDialogRef<T>, result?: R) {
    return dialogRef.close({ wasApplied: true, result: result } as DialogResult);
}
