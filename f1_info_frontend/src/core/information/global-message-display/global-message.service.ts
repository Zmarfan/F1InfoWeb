import { Injectable } from '@angular/core';

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

    public getMessagesInStore(): GlobalMessageConfig[] {
        return this.mMessageQueue.map((message) => message.message);
    }

    public addMessage(message: GlobalMessageConfig) {
        const uniqueId: number = this.mUniqueId++;
        this.mMessageQueue.push({ message, uniqueId });
        setTimeout(() => this.removeMessageFromQueue(uniqueId), GlobalMessageService.MESSAGE_SHOW_TIME_IN_MS);
    }

    private removeMessageFromQueue(uniqueId: number) {
        this.mMessageQueue = this.mMessageQueue.filter((message) => message.uniqueId !== uniqueId);
    }
}
