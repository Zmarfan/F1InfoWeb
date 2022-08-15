import { Component } from '@angular/core';
import {Compose} from '../../../../core/compose/compose.component';
import {Router} from '@angular/router';
import {RouteHolder} from '../../../routing/route-holder';

export interface DriverEntryItem {
    name: string;
    driverIdentifier: string;
}

@Component({
    selector: 'app-driver-entry',
    templateUrl: './driver-entry.component.html',
    styleUrls: ['./driver-entry.component.scss'],
})
export class DriverEntryComponent implements Compose<DriverEntryItem> {
    public data!: DriverEntryItem;

    public constructor(
        private mRouter: Router
    ) {
    }

    public routeToDriverProfile() {
        this.mRouter.navigate([RouteHolder.DRIVER_PROFILE], { queryParams: { driver: this.data.driverIdentifier } }).then();
    }
}
