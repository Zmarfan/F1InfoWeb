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
        this.items$ = this.mFeedbackService.getFeedbackItems();
    }
}
