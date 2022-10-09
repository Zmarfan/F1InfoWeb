import {Component, Input, OnChanges} from '@angular/core';
import {FriendsService} from '../friends.service';
import {Observer} from 'rxjs';
import {GlobalMessageService} from '../../../information/global-message-display/global-message.service';
import {FriendInfo} from '../../../../generated/server-responses';
import {parseTemplate} from 'url-template';
import {Endpoints} from '../../../../app/configuration/endpoints';

export interface Friend {
    loading: boolean;
    userId: number;
    displayName: string;
    friendCode: string;
}

@Component({
    selector: 'app-user-friends',
    templateUrl: './user-friends.component.html',
    styleUrls: ['./user-friends.component.scss'],
})
export class UserFriendsComponent implements OnChanges {
    @Input() public friendResponses: FriendInfo[] = [];
    public friends: Friend[] = [];

    public constructor(
        private mFriendService: FriendsService,
        private mMessageService: GlobalMessageService
    ) {
    }

    public ngOnChanges() {
        this.friends = (this.friendResponses ?? []).map((friend) => ({
            loading: false,
            userId: friend.userId,
            displayName: friend.displayName,
            friendCode: friend.friendCode,
        }));
    }

    public removeFriend(friend: Friend) {
        this.mFriendService.removeFriend(friend.userId).subscribe(this.createRequestHandler(friend));
    }

    public blockFriend(friend: Friend) {
        this.mFriendService.blockUser(friend.userId).subscribe(this.createRequestHandler(friend));
    }

    public getFriendProfileIconSrc(friendCode: string): string {
        return parseTemplate(Endpoints.FRIENDS.getProfileIcon).expand({ friendCode });
    }

    private createRequestHandler<T>(friend: Friend): Partial<Observer<T>> {
        friend.loading = true;
        return {
            error: (e) => {
                this.mMessageService.addHttpError(e);
                friend.loading = false;
            },
            next: () => {
                this.friends = this.friends.filter((friendInList) => friendInList.userId !== friend.userId);
                friend.loading = false;
            },
        };
    }
}
