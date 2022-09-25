import {Component, Input} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {FriendsService} from '../friends.service';
import {GlobalMessageService} from '../../../information/global-message-display/global-message.service';
import {ValidatorFactory} from '../../../utils/validator-factory';
import {SearchFriendResponse} from '../../../../generated/server-responses';

@Component({
    selector: 'app-add-friends',
    templateUrl: './add-friends.component.html',
    styleUrls: ['./add-friends.component.scss'],
})
export class AddFriendsComponent {
    @Input() public myFriendCode!: string;

    public friendCode: FormControl;
    public formData: FormGroup;

    public hasSearched: boolean = false;
    public searching: boolean = false;
    public addingFriendLoading: boolean = false;
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
