import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingSpinnerComponent } from './loading/loading-spinner/loading-spinner.component';
import { LoadingElementComponent } from './loading/loading-element/loading-element.component';
import { MainHeaderComponent } from './navigation/main-header/main-header.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import { ProfileHeaderComponent } from './navigation/profile-header/profile-header.component';
import {TranslateModule} from '@ngx-translate/core';
import { LanguageSelectorComponent } from './navigation/profile-header/language-selector/language-selector.component';
import { LoginComponent } from '../app/login-page/login/login.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import { PrimaryButtonDirective } from './input/buttons/primary-button.directive';
import { ButtonDirective } from './input/buttons/button.directive';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
    declarations: [
        LoadingSpinnerComponent,
        LoadingElementComponent,
        MainHeaderComponent,
        ProfileHeaderComponent,
        LanguageSelectorComponent,
        LoginComponent,
        PrimaryButtonDirective,
        ButtonDirective,
    ],
    imports: [
        CommonModule,
        FontAwesomeModule,
        TranslateModule,
        MatFormFieldModule,
        ReactiveFormsModule,
        MatInputModule,
        MatDialogModule,
    ],
    exports: [
        MainHeaderComponent,
        LoadingElementComponent,
    ],
})
export class CoreModule {
}
