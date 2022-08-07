import {ReportEntryItem} from '../report-element-entry/report-element-entry.component';
import {TranslateEntryComponent, TranslateItem} from './translate-entry.component';

export class TranslateEntry implements ReportEntryItem {
    public viewModel = TranslateEntryComponent;
    public data: TranslateItem;

    public constructor(
        key: string,
        params?: any
    ) {
        this.data = { key, params };
    }
}
