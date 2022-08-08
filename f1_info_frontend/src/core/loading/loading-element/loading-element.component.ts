import {Component, Input} from '@angular/core';

export enum LoadingSpinnerSize {
    SMALL = 1,
    NORMAL = 2,
}

export enum LoadingElementOffset {
    CENTER = 1,
    TOP = 2,
}

@Component({
    selector: 'app-loading-element',
    templateUrl: './loading-element.component.html',
    styleUrls: ['./loading-element.component.scss'],
})
export class LoadingElementComponent {
    @Input() public loading: boolean = false;
    @Input() public size: LoadingSpinnerSize = LoadingSpinnerSize.NORMAL;
    @Input() public offset: LoadingElementOffset = LoadingElementOffset.CENTER;

    public get offsetModifier(): string {
        return this.offset === LoadingElementOffset.CENTER ? 'loading--center' : '';
    }
}
