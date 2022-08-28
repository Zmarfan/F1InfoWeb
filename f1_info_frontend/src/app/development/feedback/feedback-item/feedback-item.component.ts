import {Component, Input, OnInit} from '@angular/core';
import {FeedbackItemResponse} from '../../../../generated/server-responses';
import {MatDialog} from '@angular/material/dialog';
import {FeedbackDeleteComponent} from './feedback-delete/feedback-delete.component';
import {DialogResult} from '../../../../core/dialog/dialog';

@Component({
    selector: 'app-feedback-item',
    templateUrl: './feedback-item.component.html',
    styleUrls: ['./feedback-item.component.scss'],
})
export class FeedbackItemComponent implements OnInit {
    @Input() public item!: FeedbackItemResponse;
    @Input() public deleteCallback!: (itemId: number) => void;
    @Input() public toggleLikeCallback!: (itemId: number, liked: boolean) => void;

    public hasLiked: boolean = false;

    public constructor(
        private mDialog: MatDialog
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
        this.hasLiked = !this.hasLiked;
        this.toggleLikeCallback(this.item.feedbackId, this.hasLiked);
    }
}
