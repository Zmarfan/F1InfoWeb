import {ComposeItem} from '../../../../core/compose/compose.component';
import {CountryEntryComponent} from './country-entry.component';
import {CountryCodes} from '../../../../generated/server-responses';

export class CountryEntry implements ComposeItem {
    public viewModel = CountryEntryComponent;
    public data: CountryCodes;

    public constructor(codes: CountryCodes) {
        this.data = codes;
    }
}
