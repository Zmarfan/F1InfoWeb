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
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { HomepageComponent } from './homepage/homepage/homepage.component';
import {Language} from '../common/constants/language';
import { LoginComponent } from './login-page/login/login.component';

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
    ],
    declarations: [
        AppComponent,
        HomepageComponent,
        LoginComponent,
    ],
    providers: [],
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
