import {Component, Input} from '@angular/core';

@Component({
    selector: 'app-copy-button',
    templateUrl: './copy-button.component.html',
})
export class CopyButtonComponent {
    private static readonly RESET_TIME_IN_MS: number = 2000;

    @Input() public copyText!: string | number;
    private mTimeoutId: number | undefined = undefined;

    public get buttonKey(): string {
        return this.mTimeoutId === undefined ? 'buttons.copy' : 'buttons.copied';
    }

    public async copy() {
        await this.copyTextToClipboard();
        clearTimeout(this.mTimeoutId);
        this.mTimeoutId = window.setTimeout(() => this.resetButton(), CopyButtonComponent.RESET_TIME_IN_MS);
    }

    private async copyTextToClipboard() {
        await navigator.clipboard.writeText(this.copyText.toString());
    }

    private resetButton() {
        this.mTimeoutId = undefined;
    }
}
