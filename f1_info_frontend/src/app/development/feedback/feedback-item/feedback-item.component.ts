import {Component, Input, OnInit} from '@angular/core';
import {FeedbackItemResponse} from '../../../../generated/server-responses';
import {MatDialog} from '@angular/material/dialog';
import {FeedbackDeleteComponent} from './feedback-delete/feedback-delete.component';
import {DialogResult} from '../../../../core/dialog/dialog';
import {Observable} from 'rxjs';
import {GlobalMessageService} from '../../../../core/information/global-message-display/global-message.service';
import {Session} from '../../../configuration/session';

@Component({
    selector: 'app-feedback-item',
    templateUrl: './feedback-item.component.html',
    styleUrls: ['./feedback-item.component.scss'],
})
export class FeedbackItemComponent implements OnInit {
    @Input() public item!: FeedbackItemResponse;
    @Input() public deleteCallback!: (itemId: number) => void;
    @Input() public toggleLikeCallback!: (itemId: number, liked: boolean) => Observable<void>;
    @Input() public markAsComplete!: (itemId: number) => void;

    public hasLiked: boolean = false;
    private mCurrentlyChangingLikeStatus: boolean = false;

    public constructor(
        public session: Session,
        private mDialog: MatDialog,
        private mMessageService: GlobalMessageService
    ) {
    }

    public ngOnInit() {
        this.hasLiked = this.item.hasGivenUpVote;
    }

    public deleteItem() {
        this.mDialog.open(FeedbackDeleteComponent).afterClosed().subscribe((result: DialogResult) => {
            if (result?.wasApplied) {
                this.deleteCallback(this.item.feedbackId);
            }
        });
    }

    public toggleOwnLike() {
        if (this.mCurrentlyChangingLikeStatus) {
            return;
        }

        this.mCurrentlyChangingLikeStatus = true;
        this.hasLiked = !this.hasLiked;
        this.toggleLikeCallback(this.item.feedbackId, this.hasLiked).subscribe({
            next: () => {
                this.mCurrentlyChangingLikeStatus = false;
            },
            error: (e) => {
                this.mMessageService.addHttpError(e);
                this.hasLiked = !this.hasLiked;
                this.mCurrentlyChangingLikeStatus = false;
            },
        });
    }

    public markItemAsComplete() {
        this.markAsComplete(this.item.feedbackId);
    }
}
