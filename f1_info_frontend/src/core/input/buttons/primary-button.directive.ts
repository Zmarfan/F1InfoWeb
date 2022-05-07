import {Directive, HostBinding} from '@angular/core';

@Directive({ selector: '[appPrimaryButton]' })
export class PrimaryButtonDirective {
    @HostBinding('class')
    public elementClass: string = 'my-button my-button__primary';
}
