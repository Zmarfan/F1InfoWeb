import {Component, OnInit} from '@angular/core';
import {FriendsResponse, FriendsService} from './friends.service';
import {GlobalMessageService} from '../../information/global-message-display/global-message.service';

@Component({
    selector: 'app-friends',
    templateUrl: './friends.component.html',
    styleUrls: ['./friends.component.scss'],
})
export class FriendsComponent implements OnInit {
    public friendsLoading: boolean = false;
    public friendResponse: FriendsResponse | undefined;

    public constructor(
        private mFriendsService: FriendsService,
        private mMessageService: GlobalMessageService
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
}
