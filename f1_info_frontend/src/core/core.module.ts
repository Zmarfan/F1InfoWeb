import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingSpinnerComponent } from './loading/loading-spinner/loading-spinner.component';
import { LoadingElementComponent } from './loading/loading-element/loading-element.component';
import { MainHeaderComponent } from './navigation/main-header/main-header.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import { ProfileHeaderComponent } from './navigation/profile-header/profile-header.component';
import {TranslateModule} from '@ngx-translate/core';
import { LanguageSelectorComponent } from './navigation/profile-header/language-selector/language-selector.component';
import { LoginSignUpComponent } from '../app/login-page/login-sign-up/login-sign-up.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import { PrimaryButtonDirective } from './input/buttons/primary-button.directive';
import { ButtonDirective } from './input/buttons/button.directive';
import {MatDialogModule} from '@angular/material/dialog';
import { TextInputComponent } from './input/fields/text-input/text-input.component';
import {RouterModule} from '@angular/router';
import { PageInformationComponent } from './information/page-information/page-information.component';
import { NavigationHeaderComponent } from './navigation/navigation-header/navigation-header.component';

@NgModule({
    declarations: [
        LoadingSpinnerComponent,
        LoadingElementComponent,
        MainHeaderComponent,
        ProfileHeaderComponent,
        LanguageSelectorComponent,
        LoginSignUpComponent,
        PrimaryButtonDirective,
        ButtonDirective,
        TextInputComponent,
        PageInformationComponent,
        NavigationHeaderComponent,
    ],
    imports: [
        CommonModule,
        FontAwesomeModule,
        TranslateModule,
        MatDialogModule,
        MatInputModule,
        ReactiveFormsModule,
        FormsModule,
        RouterModule,
    ],
    exports: [
        MainHeaderComponent,
        TextInputComponent,
        LoadingElementComponent,
        LoginSignUpComponent,
        PrimaryButtonDirective,
        PageInformationComponent,
    ],
})
export class CoreModule {
}
