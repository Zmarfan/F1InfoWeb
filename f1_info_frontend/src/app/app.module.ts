import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FaConfig, FaIconLibrary, FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {fas} from '@fortawesome/free-solid-svg-icons';
import {far} from '@fortawesome/free-regular-svg-icons';
import {CoreModule} from '../core/core.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
    imports: [
        BrowserModule,
        AppRoutingModule,
        FontAwesomeModule,
        CoreModule,
        BrowserAnimationsModule,
    ],
    declarations: [
        AppComponent,
    ],
    providers: [],
    bootstrap: [AppComponent],
})
export class AppModule {
    public constructor(fontAwesomeConfig: FaConfig, fontAwesomeLibrary: FaIconLibrary) {
        fontAwesomeLibrary.addIconPacks(fas, far);
        fontAwesomeConfig.defaultPrefix = 'fas';
    }
}
