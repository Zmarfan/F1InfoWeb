import {Component, Input, OnInit} from '@angular/core';
import {FeedbackItemResponse} from '../../../../generated/server-responses';

@Component({
    selector: 'app-feedback-item',
    templateUrl: './feedback-item.component.html',
    styleUrls: ['./feedback-item.component.scss'],
})
export class FeedbackItemComponent implements OnInit {
    @Input() public item!: FeedbackItemResponse;
    public hasLiked: boolean = false;

    public ngOnInit() {
        this.hasLiked = this.item.hasGivenUpVote;
    }

    public toggleOwnLike() {
        this.hasLiked = !this.hasLiked;
    }
}
