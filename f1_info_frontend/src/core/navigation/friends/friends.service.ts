import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class FriendsService {
    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public getFriendsData(): Observable<{ myCode: number }> {
        return of({ myCode: 12359123 });
    }

    public searchFriendByCode(code: string): Observable<{ user?: { displayName: string, friendsInCommon: number } }> {
        if (code !== 'asd') {
            return of({});
        }

        return of({
            user: {
                friendsInCommon: 3,
                displayName: 'Henry',
            },
        });
    }
}
