import {Component, Input} from '@angular/core';
import {LoadingSpinnerSize} from '../loading-element/loading-element.component';

@Component({
    selector: 'app-loading-spinner',
    templateUrl: './loading-spinner.component.html',
    styleUrls: ['./loading-spinner.component.scss'],
})
export class LoadingSpinnerComponent {
    @Input() public size!: LoadingSpinnerSize;

    public get modifierCss(): string {
        return this.size === LoadingSpinnerSize.SMALL ? 'loader--small' : '';
    }
}
