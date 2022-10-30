import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'countryFlag',
})
export class CountryFlagPipe implements PipeTransform {

    public transform(countryCode: string, ...args: unknown[]): string {
        return `/assets/images/flags/${countryCode}.svg`;
    }
}
