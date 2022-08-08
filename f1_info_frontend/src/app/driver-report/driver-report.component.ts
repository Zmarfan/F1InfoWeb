import {Component, OnInit} from '@angular/core';
import {TranslateEntry} from '../reports/entry/data-report-entry/translate-entry';
import {ReportColumn} from '../reports/report-element/report-column';
import {ReportSortConfig, SortDirection, SortSetting} from '../reports/report-element/report-element.component';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {DropDownFilterProvider} from '../reports/filters/drop-down-filter/drop-down-filter-provider';
import {DriverReportService} from './driver-report.service';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {DriverReportDriverResponse} from '../../generated/server-responses';

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
export class DriverReportComponent implements OnInit {
    public seasonsOptions: DropdownOption[] = DropDownFilterProvider.createSeasonOptions();
    public driverOptions: DropdownOption[] = [];
    public driverSelectLoading: boolean = true;

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

    private mSelectedSeason: number = new Date().getFullYear();
    private mSelectedDriver: string | null = null;

    public constructor(
        private mDriverReportService: DriverReportService,
        private mMessageService: GlobalMessageService
    ) {
    }

    public ngOnInit() {
        this.fetchDriversFromSeason();
    }

    public sort(sortSetting: SortSetting) {
        console.log(sortSetting);
    }

    public seasonFilterChanged = (newSeason: number) => {
        this.mSelectedSeason = newSeason;
        this.fetchDriversFromSeason();
    };

    public driverFilterChanged = (newDriver: string | null) => {
        this.mSelectedDriver = newDriver;
    };

    private fetchDriversFromSeason() {
        this.driverSelectLoading = true;
        this.mDriverReportService.getDriversFromSeason(this.mSelectedSeason).subscribe({
            next: (response) => {
                this.driverSelectLoading = false;
                this.populateDriverFilter(response);
            },
            error: (error) => {
                this.driverSelectLoading = false;
                this.mMessageService.addHttpError(error);
            },
        });
    }

    private populateDriverFilter(response: DriverReportDriverResponse[]) {
        this.driverOptions = response
            .sort((d1, d2) => d1.fullName.localeCompare(d2.fullName))
            .map((driver) => ({ displayValue: driver.fullName, value: driver.driverIdentifier }));
    }
}
