import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RouteHolder} from './routeHolder';
import {SignUpComponent} from '../login-page/sign-up/sign-up.component';
import {HomepageComponent} from '../homepage/homepage/homepage.component';

const routes: Routes = [
    { path: RouteHolder.HOMEPAGE, component: HomepageComponent },
    { path: RouteHolder.SIGN_UP_PAGE, component: SignUpComponent },
    { path: '**', redirectTo: RouteHolder.HOMEPAGE },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {

}
