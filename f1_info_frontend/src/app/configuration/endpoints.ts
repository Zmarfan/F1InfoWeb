import {environment} from '../../environments/environment';

export class Endpoints {
    private static readonly BASE_URL: string = environment.baseUrl;
    private static readonly AUTHENTICATION_BASE: string = Endpoints.BASE_URL + 'Authentication/';

    // eslint-disable-next-line @typescript-eslint/member-ordering
    public static readonly AUTHENTICATION = {
        isLoggedIn: `${Endpoints.AUTHENTICATION_BASE}isLoggedIn`,
        login: `${Endpoints.AUTHENTICATION_BASE}login`,
        logout: `${Endpoints.AUTHENTICATION_BASE}logout`,
        register: `${Endpoints.AUTHENTICATION_BASE}register`,
        enableAccount: `${Endpoints.AUTHENTICATION_BASE}enable/{token}`,
        forgotPassword: `${Endpoints.AUTHENTICATION_BASE}forgot-password`,
        resetPassword: `${Endpoints.AUTHENTICATION_BASE}reset-password/{token}`,
    };
}
