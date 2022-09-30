import {Component, OnInit} from '@angular/core';
import {FriendsService} from './friends.service';
import {GlobalMessageService} from '../../information/global-message-display/global-message.service';
import {FriendsInfoResponse} from '../../../generated/server-responses';
import {MatDialogRef} from '@angular/material/dialog';
import {closeDialog} from '../../dialog/dialog';

@Component({
    selector: 'app-friends',
    templateUrl: './friends.component.html',
    styleUrls: ['./friends.component.scss'],
})
export class FriendsComponent implements OnInit {
    public friendsLoading: boolean = false;
    public friendResponse: FriendsInfoResponse | undefined;

    public constructor(
        private mFriendsService: FriendsService,
        private mMessageService: GlobalMessageService,
        private mDialogRef: MatDialogRef<FriendsComponent>
    ) {
    }

    public ngOnInit() {
        this.friendsLoading = true;
        this.mFriendsService.getFriendsData().subscribe({
            next: (response) => {
                this.friendResponse = response;
                this.friendsLoading = false;
            },
            error: (e) => {
                this.mMessageService.addHttpError(e);
                this.friendsLoading = false;
            },
        });
    }

    public closeFriends() {
        closeDialog(this.mDialogRef);
    }
}
