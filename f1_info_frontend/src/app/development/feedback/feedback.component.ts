import {Component, OnInit} from '@angular/core';
import {FeedbackService} from './feedback.service';
import {FeedbackItemResponse} from '../../../generated/server-responses';
import {Observable} from 'rxjs';

@Component({
    selector: 'app-feedback',
    templateUrl: './feedback.component.html',
    styleUrls: ['./feedback.component.scss'],
})
export class FeedbackComponent implements OnInit {
    public items$!: Observable<FeedbackItemResponse[]>;
    public showOnlyOwnItems: boolean = false;

    public constructor(
        private mFeedbackService: FeedbackService
    ) {
    }

    public ngOnInit() {
        this.fetchFeedbackItems();
    }

    public likeCallback = (itemId: number, liked: boolean) => {
        return this.mFeedbackService.toggleLikeFeedbackItem(itemId, liked);
    };

    public deleteCallback = (itemId: number) => {
        this.mFeedbackService.deleteFeedbackItem(itemId).subscribe(() => {
            this.fetchFeedbackItems();
        });
    };

    private fetchFeedbackItems() {
        this.items$ = this.mFeedbackService.getFeedbackItems();
    }
}
