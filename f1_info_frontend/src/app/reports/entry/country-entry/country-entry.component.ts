import { Component } from '@angular/core';
import {Compose} from '../../../../core/compose/compose.component';
import {CountryCodes} from '../../../../generated/server-responses';

export interface CountryEntryItem {
    isoCode: string;
    displayValue: string;
}

@Component({
    selector: 'app-country-entry',
    template: '<div><img [src]="flagSrc"><span>{{data.displayValue}}</span></div>',
    styleUrls: ['./country-entry.component.scss'],
})
export class CountryEntryComponent implements Compose<CountryEntryItem> {
    public data!: CountryEntryItem;

    public get flagSrc(): string {
        return `/assets/images/flags/${this.data.isoCode}.svg`;
    }
}
