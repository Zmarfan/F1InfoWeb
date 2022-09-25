import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of, delay} from 'rxjs';
import {Endpoints} from '../../../app/configuration/endpoints';
import {FriendsInfoResponse, SearchFriendResponse} from '../../../generated/server-responses';
import {parseTemplate} from 'url-template';

@Injectable({
    providedIn: 'root',
})
export class FriendsService {
    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public getFriendsData(): Observable<FriendsInfoResponse> {
        return this.mHttpClient.get<FriendsInfoResponse>(Endpoints.FRIENDS.getInfo);
    }

    public searchFriendByCode(friendCode: string): Observable<SearchFriendResponse> {
        return this.mHttpClient.get<SearchFriendResponse>(parseTemplate(Endpoints.FRIENDS.searchFriend).expand({ friendCode }));
    }

    public sendFriendRequest(friendCode: string) {
        return this.mHttpClient.post(Endpoints.FRIENDS.sendFriendRequest, { friendCode });
    }
}
