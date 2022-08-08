/* eslint-disable @typescript-eslint/member-ordering,max-len */
import {environment} from '../../environments/environment';

export class Endpoints {
    private static readonly BASE_URL: string = environment.baseUrl;
    private static readonly AUTHENTICATION_BASE: string = Endpoints.BASE_URL + 'Authentication/';
    private static readonly USER_BASE: string = Endpoints.BASE_URL + 'User/';
    private static readonly REPORTS_BASE: string = Endpoints.BASE_URL + 'Reports/';

    public static readonly AUTHENTICATION = {
        getUser: `${Endpoints.AUTHENTICATION_BASE}getUser`,
        login: `${Endpoints.AUTHENTICATION_BASE}login`,
        logout: `${Endpoints.AUTHENTICATION_BASE}logout`,
        register: `${Endpoints.AUTHENTICATION_BASE}register`,
        enableAccount: `${Endpoints.AUTHENTICATION_BASE}enable/{token}`,
        forgotPassword: `${Endpoints.AUTHENTICATION_BASE}forgot-password`,
        resetPassword: `${Endpoints.AUTHENTICATION_BASE}reset-password/{token}`,
    };

    public static readonly USER = {
        updateSettings: `${Endpoints.USER_BASE}update-settings`,
    };

    public static readonly REPORTS = {
        getDriverReportFilterValues: `${Endpoints.REPORTS_BASE}driver-report-filter-values/{season}`,
        getAllDriverReport: `${Endpoints.REPORTS_BASE}get-all-driver-report/{season}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getIndividualDriverReport: `${Endpoints.REPORTS_BASE}get-individual-driver-report/{season}/{driverIdentifier}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
    };
}
