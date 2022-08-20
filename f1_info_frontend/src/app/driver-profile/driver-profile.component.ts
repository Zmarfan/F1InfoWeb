import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {DropdownOption} from '../reports/filters/drop-down-filter/drop-down-filter.component';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {DriverProfileService} from './driver-profile.service';
import {DriverChartInfoResponse, DriverProfileFilterResponse, DriverProfileResponse} from '../../generated/server-responses';

@Component({
    selector: 'app-driver-profile',
    templateUrl: './driver-profile.component.html',
    styleUrls: ['./driver-profile.component.scss', './../reports/report-styling.scss'],
})
export class DriverProfileComponent implements OnInit {
    public driverOptions: DropdownOption[] = [];
    public selectedDriverIdentifier: string = 'max_verstappen';
    public driverInfoLoading: boolean = false;
    public driverChartInfoLoading: boolean = false;
    public filterLoading: boolean = false;

    public driverInfo!: DriverProfileResponse;
    public driverChartInfo!: DriverChartInfoResponse;

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
        this.runReport();
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
        if (this.driverOptions.filter((driver) => driver.value === this.selectedDriverIdentifier).length === 0) {
            this.selectedDriverIdentifier = this.driverOptions[0].value!.toString();
        }
    }

    private runReport() {
        this.fetchDriverInfo();
        this.fetchDriverChartInfo();
    }

    private fetchDriverInfo() {
        this.driverInfoLoading = true;
        this.mDriverProfileService.getProfileInfo(this.selectedDriverIdentifier).subscribe({
            next: (response) => {
                this.driverInfoLoading = false;
                this.driverInfo = response;
            },
            error: (error) => {
                this.driverInfoLoading = false;
                this.mMessageService.addHttpError(error);
            },
        });
    }

    private fetchDriverChartInfo() {
        this.driverChartInfoLoading = true;
        this.mDriverProfileService.getChartInfo(this.selectedDriverIdentifier).subscribe({
            next: (response) => {
                this.driverChartInfoLoading = false;
                this.driverChartInfo = response;
            },
            error: (error) => {
                this.driverChartInfoLoading = false;
                this.mMessageService.addHttpError(error);
            },
        });
    }
}
