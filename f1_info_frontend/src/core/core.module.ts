import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingSpinnerComponent } from './loading/loading-spinner/loading-spinner.component';
import { LoadingElementComponent } from './loading/loading-element/loading-element.component';
import { MainHeaderComponent } from './navigation/main-header/main-header.component';

@NgModule({
    declarations: [
        LoadingSpinnerComponent,
        LoadingElementComponent,
        MainHeaderComponent,
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
