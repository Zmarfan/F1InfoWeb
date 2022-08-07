import {Component, Input, OnInit, Type, ViewChild} from '@angular/core';
import {ComposeDirective} from './compose.directive';

export interface Compose<T> {
    data: T;
}

export interface ComposeItem {
    viewModel: Type<any>;
    data: any;
}

@Component({
    selector: 'app-compose',
    template: '<ng-template appCompose></ng-template>',
})
export class ComposeComponent implements OnInit {
    @Input() public item!: any;
    @ViewChild(ComposeDirective, { static: true }) public appCompose!: ComposeDirective;

    public ngOnInit() {
        this.appCompose.viewContainerRef.clear();
        const viewModelRef = this.appCompose.viewContainerRef.createComponent<Compose<any>>(this.item.viewModel);
        viewModelRef.instance.data = this.item.data;
    }
}
