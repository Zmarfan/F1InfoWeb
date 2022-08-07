import {Directive, ViewContainerRef} from '@angular/core';

@Directive({
    selector: '[appReportElementEntry]',
})
export class ReportElementEntryDirective {
    public constructor(
        public viewContainerRef: ViewContainerRef
    ) {
    }
}
