import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingSpinnerComponent } from './loading/loading-spinner/loading-spinner.component';
import { LoadingElementComponent } from './loading/loading-element/loading-element.component';
import { MainHeaderComponent } from './navigation/main-header/main-header.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import { ProfileHeaderComponent } from './navigation/profile-header/profile-header.component';

@NgModule({
    declarations: [
        LoadingSpinnerComponent,
        LoadingElementComponent,
        MainHeaderComponent,
        ProfileHeaderComponent,
    ],
    imports: [
        CommonModule,
        FontAwesomeModule,
    ],
    exports: [
        MainHeaderComponent,
        LoadingElementComponent,
    ],
})
export class CoreModule {
}
