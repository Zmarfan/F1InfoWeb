import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RouteHolder} from './route-holder';
import {HomepageComponent} from '../homepage/homepage/homepage.component';
import {LoginComponent} from '../login-page/login/login.component';
import {SignUpComponent} from '../login-page/sign-up/sign-up.component';
import {ForgotPasswordComponent} from '../login-page/password-reseting/forgot-password/forgot-password.component';
import {ResetPasswordComponent} from '../login-page/password-reseting/reset-password/reset-password.component';
import {TestComponent} from '../test/test.component';
import {SessionGuard} from './session-guard';
import {AnonymousGuard} from './anonymous-guard';

const routes: Routes = [
    { path: RouteHolder.HOMEPAGE, component: HomepageComponent },
    { path: RouteHolder.SIGN_UP_PAGE, component: SignUpComponent, canActivate: [AnonymousGuard] },
    { path: RouteHolder.FORGOT_PASSWORD_PAGE, component: ForgotPasswordComponent, canActivate: [AnonymousGuard] },
    { path: RouteHolder.LOGIN_PAGE, component: LoginComponent },
    { path: RouteHolder.RESET_PASSWORD_PAGE, component: ResetPasswordComponent },
    { path: 'test', component: TestComponent, canActivate: [SessionGuard] },
    { path: '**', redirectTo: RouteHolder.HOMEPAGE },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
