import {Component, Input} from '@angular/core';

export type LoadingSpinnerSize = 'small' | 'normal';
export type LoadingElementOffset = 'center' | 'top';

@Component({
    selector: 'app-loading-element',
    templateUrl: './loading-element.component.html',
    styleUrls: ['./loading-element.component.scss'],
})
export class LoadingElementComponent {
    @Input() public loading: any = false;
    @Input() public size: LoadingSpinnerSize = 'normal';
    @Input() public offset: LoadingElementOffset = 'center';

    public get offsetModifier(): string {
        return this.offset === 'center' ? 'loading--center' : '';
    }
}
