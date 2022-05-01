import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingSpinnerComponent } from './loading/loading-spinner/loading-spinner.component';
import { LoadingElementComponent } from './loading/loading-element/loading-element.component';

@NgModule({
    declarations: [
        LoadingSpinnerComponent,
        LoadingElementComponent,
    ],
    imports: [
        CommonModule,
    ],
    exports: [
        LoadingElementComponent,
    ],
})
export class CoreModule {
}
