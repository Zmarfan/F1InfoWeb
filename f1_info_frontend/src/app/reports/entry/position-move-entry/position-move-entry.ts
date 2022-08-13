import {ComposeItem} from '../../../../core/compose/compose.component';
import {PositionMoveEntryComponent, PositionMoveEntryItem} from './position-move-entry.component';

export class PositionMoveEntry implements ComposeItem {
    public viewModel = PositionMoveEntryComponent;
    public data: PositionMoveEntryItem;

    public constructor(item: PositionMoveEntryItem) {
        this.data = item;
    }
}
