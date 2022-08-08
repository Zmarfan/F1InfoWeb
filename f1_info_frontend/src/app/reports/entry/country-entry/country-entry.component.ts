import { Component } from '@angular/core';
import {Compose} from '../../../../core/compose/compose.component';
import {CountryCodes} from '../../../../generated/server-responses';

@Component({
    selector: 'app-country-entry',
    template: '<div><img [src]="flagSrc"><span>{{data.icoCode}}</span></div>',
    styleUrls: ['./country-entry.component.scss'],
})
export class CountryEntryComponent implements Compose<CountryCodes> {
    public data!: CountryCodes;

    public get flagSrc(): string {
        return '';
    }
}
