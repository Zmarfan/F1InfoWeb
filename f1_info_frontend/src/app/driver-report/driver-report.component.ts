import {Component} from '@angular/core';
import {TranslateEntry} from '../reports/entry/data-report-entry/translate-entry';
import {ReportColumn} from '../reports/report-element/report-column';
import {ReportSortConfig, SortDirection, SortSetting} from '../reports/report-element/report-element.component';

interface TestRow {
    name: string;
    age: number;
    country: string;
    key: TranslateEntry;
}

@Component({
    selector: 'app-driver-report',
    templateUrl: './driver-report.component.html',
    styleUrls: ['./driver-report.component.scss'],
})
export class DriverReportComponent {
    public columns: ReportColumn[] = [
        new ReportColumn('name', 'name'),
        new ReportColumn('age', 'age'),
        new ReportColumn('country', 'country'),
        new ReportColumn('key', 'unexpectedError'),
    ];
    public rows: TestRow[] = [
        { age: 24, name: 'Filip', country: 'Sweden', key: new TranslateEntry('unexpectedError') },
        { name: 'Bob', age: 44, country: 'USA', key: new TranslateEntry('unexpectedError') },
        { name: 'Daisy', age: 14, country: 'Turkey', key: new TranslateEntry('unexpectedError') },
    ];

    public sortConfig: ReportSortConfig = {
        sortCallback: this.sort,
        defaultSortName: 'age',
        defaultSortDirection: SortDirection.ASCENDING,
    };

    public sort(sortSetting: SortSetting) {
        console.log(sortSetting);
    }
}
