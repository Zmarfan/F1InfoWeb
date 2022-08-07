import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './routing/app-routing.module';
import { AppComponent } from './app.component';
import {FaConfig, FaIconLibrary, FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {fas} from '@fortawesome/free-solid-svg-icons';
import {far} from '@fortawesome/free-regular-svg-icons';
import {CoreModule} from '../core/core.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {TranslateLoader, TranslateModule, TranslateService} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import { HomepageComponent } from './homepage/homepage/homepage.component';
import {Language} from '../common/constants/language';
import { LoginComponent } from './login-page/login/login.component';
import { SignUpComponent } from './login-page/sign-up/sign-up.component';
import {TokenInterceptor} from './configuration/http-interceptor';
import { ForgotPasswordComponent } from './login-page/password-reseting/forgot-password/forgot-password.component';
import {ReactiveFormsModule} from '@angular/forms';
import { ResetPasswordComponent } from './login-page/password-reseting/reset-password/reset-password.component';
import { DriverReportComponent } from './driver-report/driver-report.component';
import {SessionGuard} from './routing/session-guard';
import {Session} from './configuration/session';
import {AnonymousGuard} from './routing/anonymous-guard';
import { ReportElementComponent } from './reports/report-element/report-element.component';
import { ComposeDirective } from '../core/compose/compose.directive';
import { ComposeComponent } from '../core/compose/compose.component';
import { TranslateEntryComponent } from './reports/entry/data-report-entry/translate-entry.component';

export function HttpLoaderFactory(http: HttpClient) {
    return new TranslateHttpLoader(http);
}

@NgModule({
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        TranslateModule.forRoot({
            defaultLanguage: Language.ENGLISH,
            loader: {
                provide: TranslateLoader,
                useFactory: HttpLoaderFactory,
                deps: [HttpClient],
            },
        }),
        FontAwesomeModule,
        CoreModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
    ],
    declarations: [
        AppComponent,
        HomepageComponent,
        LoginComponent,
        SignUpComponent,
        ForgotPasswordComponent,
        ResetPasswordComponent,
        DriverReportComponent,
        ReportElementComponent,
        ComposeDirective,
        ComposeComponent,
        TranslateEntryComponent,
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: TokenInterceptor,
            multi: true,
        },
        Session,
        SessionGuard,
        AnonymousGuard,
    ],
    bootstrap: [AppComponent],
})
export class AppModule {
    public constructor(
        translateService: TranslateService,
        fontAwesomeConfig: FaConfig,
        fontAwesomeLibrary: FaIconLibrary
    ) {
        translateService.setDefaultLang(Language.ENGLISH);
        translateService.use(Language.ENGLISH);

        fontAwesomeLibrary.addIconPacks(fas, far);
        fontAwesomeConfig.defaultPrefix = 'fas';
    }
}
