import { Component } from '@angular/core';
import {Compose} from '../../../../core/compose/compose.component';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faCaretDown, faCaretUp, faMinus} from '@fortawesome/free-solid-svg-icons';
import {PositionMove} from '../../../../generated/server-responses';

export interface PositionMoveEntryItem {
    position: number;
    move: PositionMove;
}

@Component({
    selector: 'app-position-move-entry',
    templateUrl: './position-move-entry.component.html',
    styleUrls: ['./position-move-entry.component.scss'],
})
export class PositionMoveEntryComponent implements Compose<PositionMoveEntryItem> {
    public data!: PositionMoveEntryItem;

    public get moveIcon(): IconDefinition {
        switch (this.data.move) {
        case 'UP': return faCaretUp;
        case 'DOWN': return faCaretDown;
        default: return faMinus;
        }
    }

    public get iconStyleClass(): string {
        switch (this.data.move) {
        case 'UP': return 'position-up';
        case 'DOWN': return 'position-down';
        default: return 'position-stay';
        }
    }
}
