import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingSpinnerComponent } from './loading/loading-spinner/loading-spinner.component';
import { LoadingElementComponent } from './loading/loading-element/loading-element.component';
import { MainHeaderComponent } from './navigation/main-header/main-header.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import { ProfileHeaderComponent } from './navigation/profile-header/profile-header.component';
import {MatMenuModule} from '@angular/material/menu';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatListModule} from '@angular/material/list';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import { LanguageSelectorComponent } from './navigation/profile-header/language-selector/language-selector.component';
import {MatRadioModule} from '@angular/material/radio';
import {HttpClient} from '@angular/common/http';
import {HttpLoaderFactory} from '../app/app.module';
import {MatButtonModule} from '@angular/material/button';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
    declarations: [
        LoadingSpinnerComponent,
        LoadingElementComponent,
        MainHeaderComponent,
        ProfileHeaderComponent,
        LanguageSelectorComponent,
    ],
    imports: [
        CommonModule,
        FontAwesomeModule,
        MatMenuModule,
        MatExpansionModule,
        MatListModule,
        MatRadioModule,
        TranslateModule,
        MatButtonModule,
        MatDialogModule,
    ],
    exports: [
        MainHeaderComponent,
        LoadingElementComponent,
    ],
})
export class CoreModule {
}
