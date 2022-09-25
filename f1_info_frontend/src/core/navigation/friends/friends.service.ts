import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of, delay} from 'rxjs';

export type FriendStatus = 'FRIENDS' | 'PENDING' | 'NOT_FRIENDS';

export interface SearchFriendResponse {
    displayName: string;
    friendsInCommon: number;
    friendStatus: FriendStatus;
    friendCode: string;
}

export interface FriendsResponse {
    myCode: string;
}

@Injectable({
    providedIn: 'root',
})
export class FriendsService {
    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public getFriendsData(): Observable<FriendsResponse> {
        return of({ myCode: '12359123' }).pipe(delay(1000));
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
