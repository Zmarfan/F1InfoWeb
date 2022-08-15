import {ComposeItem} from '../../../../core/compose/compose.component';
import {DriverEntryComponent, DriverEntryItem} from './driver-entry.component';

export class DriverEntry implements ComposeItem {
    public viewModel = DriverEntryComponent;
    public data: DriverEntryItem;

    public constructor(item: DriverEntryItem) {
        this.data = item;
    }
}
