import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {ReportColumn} from './report-column';
import {LoadingElementOffset} from '../../../core/loading/loading-element/loading-element.component';

export enum SortDirection {
    ASCENDING = 'asc',
    DESCENDING = 'desc',
}

export interface SortSetting<T> {
    direction: SortDirection;
    columnName: keyof T;
}

export interface ReportSortConfig<T> {
    sortCallback: (sortObject: SortSetting<T>) => void;
    defaultSortSetting: SortSetting<T>;
}

export interface ReportParameters {
    sortDirection: SortDirection;
    sortColumn: string;
}

export interface ReportElementConfig<T> {
    columns: ReportColumn<T>[];
    rows: T[];
}

@Component({
    selector: 'app-report-element',
    templateUrl: './report-element.component.html',
    styleUrls: ['./report-element.component.scss'],
})
export class ReportElementComponent<T> implements OnInit, OnChanges {
    @Input() public loading: boolean = false;
    @Input() public columns!: ReportColumn<T>[];
    @Input() public rows!: T[];
    @Input() public sortConfig?: ReportSortConfig<T>;

    public table!: ReportElementConfig<any>;
    public loadingOffset: LoadingElementOffset = LoadingElementOffset.TOP;
    private mSortingColumn!: keyof T;
    private mSortingDirection: SortDirection = SortDirection.ASCENDING;

    public ngOnInit() {
        if (this.sortConfig !== undefined) {
            this.mSortingColumn = this.sortConfig.defaultSortSetting.columnName;
            this.mSortingDirection = this.sortConfig.defaultSortSetting.direction;
        }
    }

    public ngOnChanges() {
        this.table = {
            columns: this.columns,
            rows: this.rows,
        };
    }

    public sortColumn(column: ReportColumn<T>) {
        this.setSortingState(column);
        if (this.sortConfig !== undefined) {
            this.sortConfig.sortCallback({ columnName: this.mSortingColumn, direction: this.mSortingDirection });
        }
    }

    public sortableColumnClass(column: ReportColumn<T>): string {
        if (this.mSortingColumn === column.entryName) {
            return this.mSortingDirection === SortDirection.ASCENDING ? 'main-table__sortable-column--ascending' : 'main-table__sortable-column--descending';
        }
        return '';
    }

    public rowEntryHasViewModel(entry: any | null): boolean {
        return entry?.viewModel;
    }

    private setSortingState(column: ReportColumn<T>) {
        if (this.mSortingColumn !== column.entryName) {
            this.mSortingColumn = column.entryName;
            this.mSortingDirection = SortDirection.ASCENDING;
            return;
        }
        this.mSortingDirection = this.mSortingDirection === SortDirection.DESCENDING ? SortDirection.ASCENDING : SortDirection.DESCENDING;
    }
}
