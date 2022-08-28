import {Component, Input} from '@angular/core';

@Component({
    selector: 'app-feedback-item',
    templateUrl: './feedback-item.component.html',
    styleUrls: ['./feedback-item.component.scss'],
})
export class FeedbackItemComponent {
    @Input() public item: any;
}
