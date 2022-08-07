import {Directive, ViewContainerRef} from '@angular/core';

@Directive({
    selector: '[appCompose]',
})
export class ComposeDirective {
    public constructor(
        public viewContainerRef: ViewContainerRef
    ) {
    }
}
