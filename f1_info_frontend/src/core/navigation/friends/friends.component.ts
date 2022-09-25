import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {ValidatorFactory} from '../../utils/validator-factory';
import {FriendsResponse, FriendsService, SearchFriendResponse} from './friends.service';
import {GlobalMessageService} from '../../information/global-message-display/global-message.service';

@Component({
    selector: 'app-friends',
    templateUrl: './friends.component.html',
    styleUrls: ['./friends.component.scss'],
})
export class FriendsComponent implements OnInit {
    public friendCode: FormControl;
    public formData: FormGroup;

    public friendsLoading: boolean = false;
    public hasSearched: boolean = false;
    public searching: boolean = false;
    public addingFriendLoading: boolean = false;
    public friendResponse: FriendsResponse | undefined;
    public searchFriendResponse: SearchFriendResponse | undefined;

    public constructor(
        private mFriendsService: FriendsService,
        private mMessageService: GlobalMessageService
    ) {
        this.friendCode = new FormControl('', [ValidatorFactory.notOnlySpaces]);
        this.formData = new FormGroup({ friendCode: this.friendCode });
    }

    public get searchIsFriend(): boolean {
        return this.searchFriendResponse !== undefined && this.searchFriendResponse.friendStatus === 'FRIENDS';
    }

    public get searchIsPending(): boolean {
        return this.searchFriendResponse !== undefined && this.searchFriendResponse.friendStatus === 'PENDING';
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

    public search() {
        this.hasSearched = true;
        this.searching = true;
        this.mFriendsService.searchFriendByCode(this.formData.value.friendCode)
            .subscribe({
                next: (response) => {
                    this.searchFriendResponse = response;
                    this.searching = false;
                },
                error: (e) => {
                    this.mMessageService.addHttpError(e);
                    this.searching = false;
                },
            });
    }

    public addFriend(searchFriendResponse: SearchFriendResponse) {
        this.addingFriendLoading = true;
        this.mFriendsService.addFriend(searchFriendResponse.friendCode)
            .subscribe({
                next: () => {
                    searchFriendResponse.friendStatus = 'PENDING';
                    this.addingFriendLoading = false;
                },
                error: (e) => {
                    this.mMessageService.addHttpError(e);
                    this.addingFriendLoading = false;
                },
            });
    }
}
