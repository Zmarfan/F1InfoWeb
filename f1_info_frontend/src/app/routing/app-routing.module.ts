import { NgModule } from '@angular/core';
import {Router, RouterModule, Routes} from '@angular/router';
import {RouteHolder} from './routeHolder';
import {HomepageComponent} from '../homepage/homepage/homepage.component';
import {LoginComponent} from '../login-page/login/login.component';
import {SignUpComponent} from '../login-page/sign-up/sign-up.component';
import {ForgotPasswordComponent} from '../login-page/password-reseting/forgot-password/forgot-password.component';
import {ResetPasswordComponent} from '../login-page/password-reseting/reset-password/reset-password.component';

const anonymousRoutes: Routes = [
    { path: RouteHolder.HOMEPAGE, component: HomepageComponent },
    { path: RouteHolder.SIGN_UP_PAGE, component: SignUpComponent },
    { path: RouteHolder.LOGIN_PAGE, component: LoginComponent },
    { path: RouteHolder.FORGOT_PASSWORD_PAGE, component: ForgotPasswordComponent },
    { path: RouteHolder.RESET_PASSWORD_PAGE, component: ResetPasswordComponent },
    { path: '**', redirectTo: RouteHolder.HOMEPAGE },
];

const loggedInRoutes: Routes = [
    { path: RouteHolder.HOMEPAGE, component: HomepageComponent },
    { path: RouteHolder.SIGN_UP_PAGE, component: SignUpComponent },
    { path: '**', redirectTo: RouteHolder.HOMEPAGE },
];

@NgModule({
    imports: [RouterModule.forRoot(anonymousRoutes)],
    exports: [RouterModule],
})
export class AppRoutingModule {

    public constructor(
        private mRouter: Router
    ) {
    }

    public setupLoggedInRoutes() {
        this.mRouter.resetConfig(loggedInRoutes);
    }

    public setupAnonymousRoutes() {
        this.mRouter.navigateByUrl(RouteHolder.HOMEPAGE).then();
        this.mRouter.resetConfig(anonymousRoutes);
    }
}
