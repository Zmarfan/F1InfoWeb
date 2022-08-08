import {Component, Input, OnInit} from '@angular/core';
import {ReportColumn} from './report-column';
import {LoadingElementOffset} from '../../../core/loading/loading-element/loading-element.component';

export enum SortDirection {
    ASCENDING = 'asc',
    DESCENDING = 'desc',
}

export interface SortSetting {
    direction: SortDirection;
    columnName: string;
}

export interface ReportSortConfig {
    sortCallback: (sortObject: SortSetting) => void;
    defaultSortSetting: SortSetting;
}

export interface ReportParameters {
    sortDirection: SortDirection;
    sortColumn: string;
}

export interface ReportElementConfig<T> {
    columns: ReportColumn[];
    rows: T[];
}

@Component({
    selector: 'app-report-element',
    templateUrl: './report-element.component.html',
    styleUrls: ['./report-element.component.scss'],
})
export class ReportElementComponent<T> implements OnInit {
    @Input() public loading: boolean = false;
    @Input() public columns!: ReportColumn[];
    @Input() public rows!: T[];
    @Input() public sortConfig?: ReportSortConfig;

    public table!: ReportElementConfig<any>;
    public loadingOffset: LoadingElementOffset = LoadingElementOffset.TOP;
    private mSortingColumn: string = '';
    private mSortingDirection: SortDirection = SortDirection.ASCENDING;

    public ngOnInit() {
        if (this.sortConfig !== undefined) {
            this.mSortingColumn = this.sortConfig.defaultSortSetting.columnName;
            this.mSortingDirection = this.sortConfig.defaultSortSetting.direction;
        }

        this.table = {
            columns: this.columns,
            rows: this.rows,
        };
    }

    public sortColumn(column: ReportColumn) {
        this.setSortingState(column);
        if (this.sortConfig !== undefined) {
            this.sortConfig.sortCallback({ columnName: this.mSortingColumn, direction: this.mSortingDirection });
        }
    }

    public sortableColumnClass(column: ReportColumn): string {
        if (this.mSortingColumn === column.entryName) {
            return this.mSortingDirection === SortDirection.ASCENDING ? 'main-table__sortable-column--ascending' : 'main-table__sortable-column--descending';
        }
        return '';
    }

    public rowEntryHasViewModel(entry: any): boolean {
        return entry.viewModel;
    }

    private setSortingState(column: ReportColumn) {
        if (this.mSortingColumn !== column.entryName) {
            this.mSortingColumn = column.entryName;
            this.mSortingDirection = SortDirection.ASCENDING;
            return;
        }
        this.mSortingDirection = this.mSortingDirection === SortDirection.DESCENDING ? SortDirection.ASCENDING : SortDirection.DESCENDING;
    }
}
