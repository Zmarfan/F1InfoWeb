import {Component, Input, OnInit} from '@angular/core';
import {ReportColumn} from './report-column';

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
    @Input() public columns!: ReportColumn[];
    @Input() public rows!: T[];

    public table!: ReportElementConfig<any>;

    public ngOnInit() {
        this.table = {
            columns: this.columns,
            rows: this.rows,
        };
    }

    public getRowFieldNames<T>(row: T): (keyof T)[] {
        return Object.keys(row) as (keyof T)[];
    }

    public rowEntryHasViewModel(entry: any): boolean {
        return entry.viewModel;
    }
}
