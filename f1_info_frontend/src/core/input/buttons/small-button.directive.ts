import {Directive, HostBinding} from '@angular/core';

@Directive({ selector: '[appSmallButton]' })
export class SmallButtonDirective {
    @HostBinding('class')
    public elementClass: string = 'my-button--small';
}
