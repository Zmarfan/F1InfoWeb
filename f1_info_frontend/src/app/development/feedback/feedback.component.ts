import {Component, OnInit} from '@angular/core';
import {FeedbackService} from './feedback.service';
import {FeedbackItemResponse} from '../../../generated/server-responses';
import {Observable} from 'rxjs';
import {MatDialog} from '@angular/material/dialog';
import {CreateFeedbackComponent} from './create-feedback/create-feedback.component';
import {DialogResult} from '../../../core/dialog/dialog';
import {GlobalMessageService} from '../../../core/information/global-message-display/global-message.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
    selector: 'app-feedback',
    templateUrl: './feedback.component.html',
    styleUrls: ['./feedback.component.scss'],
})
export class FeedbackComponent implements OnInit {
    public items$!: Observable<FeedbackItemResponse[]>;
    public showOnlyOwnItems: boolean = false;

    public constructor(
        private mDialog: MatDialog,
        private mTranslate: TranslateService,
        private mFeedbackService: FeedbackService,
        private mMessageService: GlobalMessageService
    ) {
    }

    public ngOnInit() {
        this.fetchFeedbackItems();
    }

    public createFeedbackItem() {
        this.mDialog.open(CreateFeedbackComponent).afterClosed().subscribe((result: DialogResult) => {
            if (result?.wasApplied) {
                this.mFeedbackService.createFeedbackItem(result.result).subscribe({
                    next: () => this.fetchFeedbackItems(),
                    error: (e) => this.mMessageService.addHttpError(e),
                });
            }
        });
    }

    public likeCallback = (itemId: number, liked: boolean) => {
        return this.mFeedbackService.toggleLikeFeedbackItem(itemId, liked);
    };

    public deleteCallback = (itemId: number) => {
        this.mFeedbackService.deleteFeedbackItem(itemId).subscribe({
            next: () => {
                this.fetchFeedbackItems();
                this.mMessageService.addSuccess(this.mTranslate.instant('feedback.successfullyDeleted'));
            },
            error: (e) => this.mMessageService.addHttpError(e),
        });
    };

    public markAsCompleteCallback = (itemId: number) => {
        this.mFeedbackService.markAsComplete(itemId).subscribe({
            next: () => {
                this.fetchFeedbackItems();
                this.mMessageService.addSuccess(this.mTranslate.instant('feedback.successfullyCompleted'));
            },
            error: (e) => this.mMessageService.addHttpError(e),
        });
    };

    private fetchFeedbackItems() {
        this.items$ = this.mFeedbackService.getFeedbackItems();
    }
}
