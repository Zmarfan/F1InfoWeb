import {ComposeItem} from '../../../../core/compose/compose.component';
import {TranslateEntryComponent, TranslateItem} from './translate-entry.component';

export class TranslateEntry implements ComposeItem {
    public viewModel = TranslateEntryComponent;
    public data: TranslateItem;

    public constructor(
        key: string,
        params?: any
    ) {
        this.data = { key, params };
    }
}
