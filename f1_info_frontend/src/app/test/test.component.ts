import { Component } from '@angular/core';
import {TranslateEntry} from '../reports/entry/data-report-entry/translate-entry';
import {ReportColumn} from '../reports/report-element/report-column';

interface TestRow {
    name: string;
    age: number;
    country: string;
    key: TranslateEntry;
}

@Component({
    selector: 'app-test',
    templateUrl: './test.component.html',
})
export class TestComponent {
    public columns: ReportColumn[] = [
        new ReportColumn('name', 'name'),
        new ReportColumn('age', 'age'),
        new ReportColumn('age', 'age'),
        new ReportColumn('key', 'unexpectedError'),
    ];
    public rows: TestRow[] = [
        { age: 24, name: 'Filip', country: 'Sweden', key: new TranslateEntry('unexpectedError') },
        { name: 'Bob', age: 44, country: 'USA', key: new TranslateEntry('unexpectedError') },
        { name: 'Daisy', age: 14, country: 'Turkey', key: new TranslateEntry('unexpectedError') },
    ];
}
