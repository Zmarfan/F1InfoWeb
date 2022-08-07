import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingSpinnerComponent } from './loading/loading-spinner/loading-spinner.component';
import { LoadingElementComponent } from './loading/loading-element/loading-element.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {TranslateModule} from '@ngx-translate/core';
import { LanguageSelectorComponent } from './navigation/language-selector/language-selector.component';
import { LoginSignUpComponent } from '../app/login-page/login-sign-up/login-sign-up.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import { PrimaryButtonDirective } from './input/buttons/primary-button.directive';
import { ButtonDirective } from './input/buttons/button.directive';
import {MatDialogModule} from '@angular/material/dialog';
import { TextInputComponent } from './input/fields/text-input/text-input.component';
import {RouterModule} from '@angular/router';
import { PageInformationComponent } from './information/page-information/page-information.component';
import { NavigationHeaderComponent } from './navigation/navigation-header/navigation-header.component';
import {ProfileHeaderComponent} from './navigation/profile-header/profile-header.component';
import { NavigationMenuComponent } from './navigation/navigation-header/navigation-menu/navigation-menu.component';
import { DesktopProfileHeaderComponent } from './navigation/profile-header/desktop-profile-header/desktop-profile-header.component';
import { MobileProfileHeaderComponent } from './navigation/profile-header/mobile-profile-header/mobile-profile-header.component';
import { UserSettingsComponent } from './navigation/user-settings/user-settings.component';
import { GlobalMessageDisplayComponent } from './information/global-message-display/global-message-display.component';

@NgModule({
    declarations: [
        LoadingSpinnerComponent,
        LoadingElementComponent,
        ProfileHeaderComponent,
        LanguageSelectorComponent,
        LoginSignUpComponent,
        PrimaryButtonDirective,
        ButtonDirective,
        TextInputComponent,
        PageInformationComponent,
        NavigationHeaderComponent,
        NavigationMenuComponent,
        DesktopProfileHeaderComponent,
        MobileProfileHeaderComponent,
        UserSettingsComponent,
        GlobalMessageDisplayComponent,
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
        TextInputComponent,
        LoadingElementComponent,
        LoginSignUpComponent,
        PrimaryButtonDirective,
        PageInformationComponent,
        NavigationHeaderComponent,
        ProfileHeaderComponent,
        GlobalMessageDisplayComponent,
        ButtonDirective,
    ],
})
export class CoreModule {
}
