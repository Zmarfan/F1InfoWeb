/* eslint-disable @typescript-eslint/member-ordering,max-len */
import {environment} from '../../environments/environment';

export class Endpoints {
    private static readonly BASE_URL: string = environment.baseUrl;
    private static readonly AUTHENTICATION_BASE: string = Endpoints.BASE_URL + 'Authentication/';
    private static readonly USER_BASE: string = Endpoints.BASE_URL + 'User/';
    private static readonly DRIVER_BASE: string = Endpoints.BASE_URL + 'Drivers/';
    private static readonly REPORTS_BASE: string = Endpoints.BASE_URL + 'Reports/';
    private static readonly DEVELOPMENT_BASE: string = Endpoints.BASE_URL + 'Development/';

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
        getBellNotificationsToDisplay: `${Endpoints.USER_BASE}bell-notifications`,
        markBellNotificationsAsOpened: `${Endpoints.USER_BASE}mark-bell-notifications-opened`,
    };

    public static readonly DRIVERS = {
        getAllDrivers: `${Endpoints.DRIVER_BASE}all-drivers`,
        getDriverProfile: `${Endpoints.DRIVER_BASE}driver-profile/{driverIdentifier}`,
        getDriverChartInfo: `${Endpoints.DRIVER_BASE}driver-chart-info/{driverIdentifier}`,
    };

    public static readonly REPORTS = {
        getDriverReportFilterValues: `${Endpoints.REPORTS_BASE}driver-report-filter-values/{season}`,
        getAllDriverReport: `${Endpoints.REPORTS_BASE}get-all-driver-report/{season}/{round}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getIndividualDriverReport: `${Endpoints.REPORTS_BASE}get-individual-driver-report/{season}/{driverIdentifier}/{raceType}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getRaceReportFilterValues: `${Endpoints.REPORTS_BASE}race-report-filter-values/{season}`,
        getOverviewRaceReport: `${Endpoints.REPORTS_BASE}get-overview-race-report/{season}/{type}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getRaceResultReport: `${Endpoints.REPORTS_BASE}get-race-result-report/{season}/{round}/{type}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getFastestLapsReport: `${Endpoints.REPORTS_BASE}get-fastest-laps-report/{season}/{round}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getPitStopsReport: `${Endpoints.REPORTS_BASE}get-pit-stops-report/{season}/{round}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getQualifyingReport: `${Endpoints.REPORTS_BASE}get-qualifying-report/{season}/{round}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getConstructorReportFilterValues: `${Endpoints.REPORTS_BASE}constructor-report-filter-values/{season}`,
        getOverviewConstructorReport: `${Endpoints.REPORTS_BASE}get-overview-constructor-report/{season}/{round}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getIndividualConstructorReport: `${Endpoints.REPORTS_BASE}get-individual-constructor-report/{season}/{constructorIdentifier}/{raceType}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
    };

    public static readonly DEVELOPMENT = {
        getChangeLogItems: `${Endpoints.DEVELOPMENT_BASE}get-change-log-items`,
        getFeedbackItems: `${Endpoints.DEVELOPMENT_BASE}get-feedback-items`,
        createFeedbackItem: `${Endpoints.DEVELOPMENT_BASE}create-feedback-item`,
        toggleFeedbackLike: `${Endpoints.DEVELOPMENT_BASE}toggle-feedback-like/{itemId}/{liked}`,
        deleteFeedbackItem: `${Endpoints.DEVELOPMENT_BASE}delete-feedback-item/{itemId}`,
        markAsComplete: `${Endpoints.DEVELOPMENT_BASE}complete-feedback-item/{itemId}`,
    };
}
