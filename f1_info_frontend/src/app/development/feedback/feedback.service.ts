import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FeedbackItemResponse} from '../../../generated/server-responses';
import {Endpoints} from '../../configuration/endpoints';

@Injectable({
    providedIn: 'root',
})
export class FeedbackService {

    public constructor(
        private mHttpClient: HttpClient
    ) {
    }

    public getFeedbackItems() {
        return this.mHttpClient.get<FeedbackItemResponse[]>(Endpoints.DEVELOPMENT.getFeedbackItems);
    }
}
