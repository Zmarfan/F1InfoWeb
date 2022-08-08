import {ComposeItem} from '../../../../core/compose/compose.component';
import {CountryEntryComponent, CountryEntryItem} from './country-entry.component';
import {CountryCodes} from '../../../../generated/server-responses';

export class CountryEntry implements ComposeItem {
    public viewModel = CountryEntryComponent;
    public data: CountryEntryItem;

    public constructor(item: CountryEntryItem) {
        this.data = item;
    }
}
