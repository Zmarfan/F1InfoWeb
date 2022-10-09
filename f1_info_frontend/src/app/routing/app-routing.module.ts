import { NgModule } from '@angular/core';
import {NavigationEnd, Router, RouterModule, Routes} from '@angular/router';
import {RouteHolder} from './route-holder';
import {HomepageComponent} from '../homepage/homepage/homepage.component';
import {LoginComponent} from '../login-page/login/login.component';
import {SignUpComponent} from '../login-page/sign-up/sign-up.component';
import {ForgotPasswordComponent} from '../login-page/password-reseting/forgot-password/forgot-password.component';
import {ResetPasswordComponent} from '../login-page/password-reseting/reset-password/reset-password.component';
import {DriverReportComponent} from '../driver-report/driver-report.component';
import {SessionGuard} from './session-guard';
import {AnonymousGuard} from './anonymous-guard';
import {RaceReportComponent} from '../race-report/race-report.component';
import {ConstructorReportComponent} from '../constructor-report/constructor-report.component';
import {DriverProfileComponent} from '../driver-profile/driver-profile.component';
import {ChangeLogComponent} from '../development/change-log/change-log.component';
import {FeedbackComponent} from '../development/feedback/feedback.component';
import {Session} from '../configuration/session';

const routes: Routes = [
    { path: RouteHolder.HOMEPAGE, component: HomepageComponent },
    { path: RouteHolder.SIGN_UP_PAGE, component: SignUpComponent, canActivate: [AnonymousGuard] },
    { path: RouteHolder.FORGOT_PASSWORD_PAGE, component: ForgotPasswordComponent, canActivate: [AnonymousGuard] },
    { path: RouteHolder.LOGIN_PAGE, component: LoginComponent },
    { path: RouteHolder.RESET_PASSWORD_PAGE, component: ResetPasswordComponent },
    { path: RouteHolder.DRIVER_PROFILE, component: DriverProfileComponent },
    { path: RouteHolder.DRIVER_REPORT, component: DriverReportComponent },
    { path: RouteHolder.CONSTRUCTOR_REPORT, component: ConstructorReportComponent },
    { path: RouteHolder.RACE_REPORT, component: RaceReportComponent },
    { path: RouteHolder.CHANGE_LOG, component: ChangeLogComponent },
    { path: RouteHolder.FEEDBACK, component: FeedbackComponent, canActivate: [SessionGuard] },
    { path: '**', redirectTo: RouteHolder.HOMEPAGE },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {
    public constructor(
        router: Router,
        session: Session
    ) {
        router.events.subscribe((value) => {
            if (value instanceof NavigationEnd) {
                session.fetchUser();
            }
        });
    }
}
