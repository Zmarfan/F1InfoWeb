import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of, delay} from 'rxjs';
import {Endpoints} from '../../../app/configuration/endpoints';
import {FriendsInfoResponse} from '../../../generated/server-responses';

export type FriendStatus = 'FRIENDS' | 'PENDING' | 'NOT_FRIENDS';

export interface SearchFriendResponse {
    displayName: string;
    friendsInCommon: number;
    friendStatus: FriendStatus;
    friendCode: string;
}

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

    public searchFriendByCode(code: string): Observable<SearchFriendResponse> {
        if (code !== 'asd') {
            return of({} as any).pipe(delay(1000));
        }

        return of({
            displayName: 'Henry',
            friendsInCommon: 3,
            friendStatus: 'FRIENDS',
            friendCode: 'asd',
        } as any).pipe(delay(1000));
    }

    public addFriend(code: string): Observable<null> {
        return of(null).pipe(delay(2000));
    }
}
