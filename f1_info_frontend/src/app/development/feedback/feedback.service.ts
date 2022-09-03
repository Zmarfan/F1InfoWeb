import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FeedbackItemResponse} from '../../../generated/server-responses';
import {Endpoints} from '../../configuration/endpoints';
import {parseTemplate} from 'url-template';

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

    public createFeedbackItem(text: string) {
        return this.mHttpClient.put<void>(Endpoints.DEVELOPMENT.createFeedbackItem, { text });
    }

    public likeFeedbackItem(itemId: number) {
        return this.mHttpClient.put<void>(parseTemplate(Endpoints.DEVELOPMENT.likeFeedback).expand({ itemId }), {});
    }

    public removeFeedbackItemLike(itemId: number) {
        return this.mHttpClient.delete<void>(parseTemplate(Endpoints.DEVELOPMENT.removeFeedbackLike).expand({ itemId }), {});
    }

    public deleteFeedbackItem(itemId: number) {
        return this.mHttpClient.delete<void>(parseTemplate(Endpoints.DEVELOPMENT.deleteFeedbackItem).expand({ itemId }), {});
    }

    public markAsComplete(itemId: number) {
        return this.mHttpClient.post<void>(parseTemplate(Endpoints.MANAGER_DEVELOPMENT.markAsComplete).expand({ itemId }), {});
    }

    public markAsWillNotDo(itemId: number) {
        return this.mHttpClient.post<void>(parseTemplate(Endpoints.MANAGER_DEVELOPMENT.markAsWillNotDo).expand({ itemId }), {});
    }
}
