import { Component } from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';

@Component({
    selector: 'app-website-info',
    templateUrl: './website-info.component.html',
    styleUrls: ['./website-info.component.scss'],
})
export class WebsiteInfoComponent {
    public constructor(
        private mDialogRef: MatDialogRef<WebsiteInfoComponent>
    ) {
    }

    public closePopup() {
        this.mDialogRef.close();
    }
}
