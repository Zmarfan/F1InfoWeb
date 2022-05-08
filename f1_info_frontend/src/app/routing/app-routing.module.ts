import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RouteHolder} from './routeHolder';
import {LoginSignUpComponent} from '../login-page/login-sign-up/login-sign-up.component';
import {HomepageComponent} from '../homepage/homepage/homepage.component';
import {LoginComponent} from '../login-page/login/login.component';

const routes: Routes = [
    { path: RouteHolder.HOMEPAGE, component: HomepageComponent },
    { path: RouteHolder.SIGN_UP_PAGE, component: LoginSignUpComponent },
    { path: RouteHolder.LOGIN_PAGE, component: LoginComponent },
    { path: '**', redirectTo: RouteHolder.HOMEPAGE },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {

}
