import {Directive, HostBinding} from '@angular/core';

@Directive({ selector: '[appErrorButton]' })
export class ErrorButtonDirective {
    @HostBinding('class')
    public elementClass: string = 'my-button my-button__error';
}
