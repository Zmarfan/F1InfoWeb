import {Component, Input} from '@angular/core';
import {NextRaceInfoResponse} from '../../../../generated/server-responses';
import * as moment from 'moment';

@Component({
    selector: 'app-next-race-info',
    templateUrl: './next-race-info.component.html',
    styleUrls: ['./next-race-info.component.scss'],
})
export class NextRaceInfoComponent {
    @Input() public nextRaceResponse!: NextRaceInfoResponse;

    public get countryKey(): string {
        return 'countries.' + this.nextRaceResponse.countryCode;
    }

    public formatDate(date: string): string {
        return moment(date).format('Do MMM YYYY');
    }
}
