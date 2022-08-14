import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {DriverProfileFilterResponse, DriverProfileService} from './driver-profile.service';

@Component({
    selector: 'app-driver-profile',
    templateUrl: './driver-profile.component.html',
    styleUrls: ['./driver-profile.component.scss', './../reports/report-styling.scss'],
})
export class DriverProfileComponent implements OnInit {
    public driverOptions: DropdownOption[] = [];
    public selectedDriverIdentifier: string | null = null;
    public filterLoading: boolean = false;

    public constructor(
        private mRoute: ActivatedRoute,
        private mDriverProfileService: DriverProfileService,
        private mMessageService: GlobalMessageService
    ) {
    }

    public ngOnInit() {
        this.fetchAndAssignFilterValues();
        this.mRoute.queryParams.subscribe((params: any) => {
            this.selectedDriverIdentifier = params.driver ?? this.selectedDriverIdentifier;
        });
    }

    public driverFilterChanged = (newDriver: string) => {
        this.selectedDriverIdentifier = newDriver;
    };

    private fetchAndAssignFilterValues() {
        this.filterLoading = true;
        this.mDriverProfileService.getAllDrivers().subscribe({
            next: (response) => {
                this.filterLoading = false;
                this.populateDriverFilter(response);
                this.runReport();
            },
            error: (error) => {
                this.filterLoading = false;
                this.mMessageService.addHttpError(error);
            },
        });
    }

    private populateDriverFilter(response: DriverProfileFilterResponse) {
        this.driverOptions = response.drivers.map((driver) => ({ displayValue: driver.fullName, value: driver.driverIdentifier }));
    }

    private runReport() {

    }
}
