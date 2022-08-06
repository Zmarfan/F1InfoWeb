import { Injectable } from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {TranslateService} from '@ngx-translate/core';

export enum GlobalMessageType {
    INFO = 1,
    SUCCESS = 2,
    ERROR = 3,
}

export interface GlobalMessageConfig {
    message: string;
    type: GlobalMessageType;
}

interface GlobalMessageInternal {
    message: GlobalMessageConfig;
    uniqueId: number;
}

@Injectable({
    providedIn: 'root',
})
export class GlobalMessageService {
    private static readonly MESSAGE_SHOW_TIME_IN_MS: number = 8000;

    private mUniqueId: number = 0;
    private mMessageQueue: GlobalMessageInternal[] = [];

    public constructor(
        private mTranslate: TranslateService
    ) {
    }

    public getMessagesInStore(): GlobalMessageConfig[] {
        return this.mMessageQueue.map((message) => message.message);
    }

    public addInfo(message: string) {
        this.addMessage({ message, type: GlobalMessageType.INFO });
    }

    public addSuccess(message: string) {
        this.addMessage({ message, type: GlobalMessageType.SUCCESS });
    }

    public addError(message: string) {
        this.addMessage({ message, type: GlobalMessageType.ERROR });
    }

    public addHttpError(error: HttpErrorResponse) {
        this.addMessage({ message: this.mTranslate.instant('genericHttpError', { code: error.status }), type: GlobalMessageType.ERROR });
    }

    private addMessage(message: GlobalMessageConfig) {
        const uniqueId: number = this.mUniqueId++;
        this.mMessageQueue.push({ message, uniqueId });
        setTimeout(() => this.removeMessageFromQueue(uniqueId), GlobalMessageService.MESSAGE_SHOW_TIME_IN_MS);
    }

    private removeMessageFromQueue(uniqueId: number) {
        this.mMessageQueue = this.mMessageQueue.filter((message) => message.uniqueId !== uniqueId);
    }
}
