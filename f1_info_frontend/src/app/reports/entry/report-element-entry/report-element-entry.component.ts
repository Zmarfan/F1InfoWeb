import {Component, Input, OnInit, Type, ViewChild} from '@angular/core';
import {ReportElementEntryDirective} from '../report-element-entry.directive';

export interface ReportElement<T> {
    data: T;
}

export interface ReportEntryItem {
    viewModel: Type<any>;
    data: any;
}

@Component({
    selector: 'app-report-element-entry',
    template: '<ng-template appReportElementEntry></ng-template>',
})
export class ReportElementEntryComponent implements OnInit {
    @Input() public item!: any;
    @ViewChild(ReportElementEntryDirective, { static: true }) public appReportElementEntry!: ReportElementEntryDirective;

    public ngOnInit() {
        this.appReportElementEntry.viewContainerRef.clear();
        const viewModelRef = this.appReportElementEntry.viewContainerRef.createComponent<ReportElement<any>>(this.item.viewModel);
        viewModelRef.instance.data = this.item.data;
    }
}
