import {Component} from '@angular/core';
import {GlobalMessageService, GlobalMessageType} from './global-message.service';

interface MessageConfig {
    message: string;
    cssClass: string;
}

@Component({
    selector: 'app-global-message-display',
    templateUrl: './global-message-display.component.html',
    styleUrls: ['./global-message-display.component.scss'],
})
export class GlobalMessageDisplayComponent {
    private static readonly MAX_MESSAGES_TO_DISPLAY: number = 4;
    private mTestCounter: number = 1;

    public constructor(
        private mMessageService: GlobalMessageService
    ) {
    }

    public get messages(): MessageConfig[] {
        return this.mMessageService.getMessagesInStore()
            .slice(0, GlobalMessageDisplayComponent.MAX_MESSAGES_TO_DISPLAY)
            .map((globalMessage) => ({ message: globalMessage.message, cssClass: GlobalMessageDisplayComponent.getCssClassFromType(globalMessage.type) }));
    }

    private static getCssClassFromType(type: GlobalMessageType): string {
        switch (type) {
        case GlobalMessageType.INFO: return 'holder__entry--info';
        case GlobalMessageType.SUCCESS: return 'holder__entry--success';
        case GlobalMessageType.ERROR: return 'holder__entry--error';
        }
    }

    public testClick() {
        this.mMessageService.addMessage({ message: 'Test message' + this.mTestCounter++, type: GlobalMessageType.INFO });
    }
}
