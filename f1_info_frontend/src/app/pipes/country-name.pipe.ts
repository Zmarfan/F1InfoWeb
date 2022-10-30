import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'countryKey',
})
export class CountryKeyPipe implements PipeTransform {

    public transform(countryCode: string, ...args: unknown[]): string {
        return 'countries.' + countryCode;
    }
}
