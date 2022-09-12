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
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { ResetPasswordComponent } from './login-page/password-reseting/reset-password/reset-password.component';
import { DriverReportComponent } from './driver-report/driver-report.component';
import {SessionGuard} from './routing/session-guard';
import {Session} from './configuration/session';
import {AnonymousGuard} from './routing/anonymous-guard';
import { ReportElementComponent } from './reports/report-element/report-element.component';
import { ComposeDirective } from '../core/compose/compose.directive';
import { ComposeComponent } from '../core/compose/compose.component';
import { TranslateEntryComponent } from './reports/entry/translate-entry/translate-entry.component';
import { DropDownFilterComponent } from './reports/filters/drop-down-filter/drop-down-filter.component';
import { CountryEntryComponent } from './reports/entry/country-entry/country-entry.component';
import { RaceReportComponent } from './race-report/race-report.component';
import { PositionMoveEntryComponent } from './reports/entry/position-move-entry/position-move-entry.component';
import { ConstructorReportComponent } from './constructor-report/constructor-report.component';
import { DriverProfileComponent } from './driver-profile/driver-profile.component';
import { DriverProfileInfoComponent } from './driver-profile/driver-profile-info/driver-profile-info.component';
import { DriverEntryComponent } from './reports/entry/driver-entry/driver-entry.component';
import {NgChartsModule} from 'ng2-charts';
import { DriverProfileChartsComponent } from './driver-profile/driver-profile-charts/driver-profile-charts.component';
import {HashLocationStrategy, LocationStrategy} from '@angular/common';
import { ChangeLogComponent } from './development/change-log/change-log.component';
import { FeedbackComponent } from './development/feedback/feedback.component';
import { FeedbackItemComponent } from './development/feedback/feedback-item/feedback-item.component';
import { FeedbackDeleteComponent } from './development/feedback/feedback-item/feedback-delete/feedback-delete.component';
import { CreateFeedbackComponent } from './development/feedback/create-feedback/create-feedback.component';
import {StorageHandler} from './storage-handler';

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
        FormsModule,
        NgChartsModule,
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
        DropDownFilterComponent,
        DropDownFilterComponent,
        CountryEntryComponent,
        RaceReportComponent,
        PositionMoveEntryComponent,
        ConstructorReportComponent,
        DriverProfileComponent,
        DriverProfileInfoComponent,
        DriverEntryComponent,
        DriverProfileChartsComponent,
        ChangeLogComponent,
        FeedbackComponent,
        FeedbackItemComponent,
        FeedbackItemComponent,
        FeedbackDeleteComponent,
        CreateFeedbackComponent,
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: TokenInterceptor,
            multi: true,
        },
        {
            provide: LocationStrategy,
            useClass: HashLocationStrategy,
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
        translateService.use(StorageHandler.getConfig().language);

        fontAwesomeLibrary.addIconPacks(fas, far);
        fontAwesomeConfig.defaultPrefix = 'fas';
    }
}
