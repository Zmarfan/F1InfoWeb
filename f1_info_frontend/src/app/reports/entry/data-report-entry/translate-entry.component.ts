import { Component } from '@angular/core';
import {ReportElement} from '../report-element-entry/report-element-entry.component';

export interface TranslateItem {
    key: string;
    params: any;
}

@Component({
    selector: 'app-data-report-entry',
    template: '<div>{{ data.key | translate: data.params }}</div>',
})
export class TranslateEntryComponent implements ReportElement<TranslateItem> {
    public data!: TranslateItem;
}
