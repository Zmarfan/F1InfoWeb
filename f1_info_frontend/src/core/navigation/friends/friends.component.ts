import { Component } from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {ValidatorFactory} from '../../utils/validator-factory';
import {FriendsService} from './friends.service';
import {GlobalMessageService} from '../../information/global-message-display/global-message.service';

@Component({
    selector: 'app-friends',
    templateUrl: './friends.component.html',
    styleUrls: ['./friends.component.scss'],
})
export class FriendsComponent {
    public friendCode: FormControl;
    public formData: FormGroup;

    public hasSearched: boolean = false;
    public searching: boolean = false;
    public friendResponse: { myCode: number } = { myCode: 18518239123 };
    public searchFriendResponse: { user?: { displayName: string, friendsInCommon: number } } | undefined = {
        user: {
            displayName: 'test',
            friendsInCommon: 3,
        },
    };

    public constructor(
        private mFriendsService: FriendsService,
        private mMessageService: GlobalMessageService
    ) {
        this.friendCode = new FormControl('', [ValidatorFactory.notOnlySpaces]);
        this.formData = new FormGroup({ friendCode: this.friendCode });
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
}
