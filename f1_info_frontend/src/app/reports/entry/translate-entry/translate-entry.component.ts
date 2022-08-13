import { Component } from '@angular/core';
import {Compose} from '../../../../core/compose/compose.component';

export interface TranslateItem {
    key: string;
    params: any;
}

@Component({
    selector: 'app-translate-entry',
    template: '<div>{{ data.key | translate: data.params }}</div>',
})
export class TranslateEntryComponent implements Compose<TranslateItem> {
    public data!: TranslateItem;
}
