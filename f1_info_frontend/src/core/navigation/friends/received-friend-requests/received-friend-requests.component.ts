import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {FriendsService} from '../friends.service';
import {GlobalMessageService} from '../../../information/global-message-display/global-message.service';
import {Observer} from 'rxjs';
import {LoadingSpinnerSize} from '../../../loading/loading-element/loading-element.component';
import {FriendRequestRecord} from '../../../../generated/server-responses';

export interface FriendRequest {
    loading: boolean;
    userId: number;
    displayName: string;
}

@Component({
    selector: 'app-received-friend-requests',
    templateUrl: './received-friend-requests.component.html',
    styleUrls: ['./received-friend-requests.component.scss'],
})
export class ReceivedFriendRequestsComponent implements OnChanges {
    @Input() public responseFriendRequests: FriendRequestRecord[] = [];
    public requests: FriendRequest[] = [];

    public constructor(
        private mFriendService: FriendsService,
        private mMessageService: GlobalMessageService
    ) {
    }

    public ngOnChanges() {
        this.requests = (this.responseFriendRequests ?? []).map((request) => ({
            loading: false,
            userId: request.userId,
            displayName: request.displayName,
        }));
    }

    public acceptFriendRequest(request: FriendRequest) {
        this.mFriendService.acceptFriendRequest(request.userId).subscribe(this.createRequestHandler(request));
    }

    public declineFriendRequest(request: FriendRequest) {
        this.mFriendService.declineFriendRequest(request.userId).subscribe(this.createRequestHandler(request));
    }

    public blockUser(request: FriendRequest) {
        this.mFriendService.acceptFriendRequest(request.userId).subscribe(this.createRequestHandler(request));
    }

    private createRequestHandler<T>(request: FriendRequest): Partial<Observer<T>> {
        request.loading = true;
        return {
            error: (e) => {
                this.mMessageService.addHttpError(e);
                request.loading = false;
            },
            next: () => {
                this.removeUserFromRequests(request.userId);
                request.loading = false;
            },
        };
    }

    private removeUserFromRequests(userId: number) {
        this.requests = this.requests.filter((request) => request.userId !== userId);
    }
}
