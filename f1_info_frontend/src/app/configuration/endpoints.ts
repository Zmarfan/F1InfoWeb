/* eslint-disable @typescript-eslint/member-ordering,max-len */
import {environment} from '../../environments/environment';

export class Endpoints {
    private static readonly BASE_URL: string = environment.baseUrl;
    private static readonly AUTHENTICATION_BASE: string = Endpoints.BASE_URL + 'Authentication/';
    private static readonly USER_BASE: string = Endpoints.BASE_URL + 'User/';
    private static readonly DRIVER_BASE: string = Endpoints.BASE_URL + 'Drivers/';
    private static readonly REPORTS_BASE: string = Endpoints.BASE_URL + 'Reports/';
    private static readonly DEVELOPMENT_BASE: string = Endpoints.BASE_URL + 'Development/';
    private static readonly OPEN_DEVELOPMENT_BASE: string = Endpoints.BASE_URL + 'OpenDevelopment/';
    private static readonly MANAGER_DEVELOPMENT_BASE: string = Endpoints.BASE_URL + 'ManagerDevelopment/';
    private static readonly FRIENDS_BASE: string = Endpoints.BASE_URL + 'Friends/';

    public static readonly AUTHENTICATION = {
        getUser: `${Endpoints.AUTHENTICATION_BASE}user`,
        login: `${Endpoints.AUTHENTICATION_BASE}login`,
        logout: `${Endpoints.AUTHENTICATION_BASE}logout`,
        register: `${Endpoints.AUTHENTICATION_BASE}register`,
        enableAccount: `${Endpoints.AUTHENTICATION_BASE}enable/{token}`,
        forgotPassword: `${Endpoints.AUTHENTICATION_BASE}forgot-password`,
        resetPassword: `${Endpoints.AUTHENTICATION_BASE}reset-password/{token}`,
    };

    public static readonly USER = {
        updateSettings: `${Endpoints.USER_BASE}settings`,
        getBellNotificationsToDisplay: `${Endpoints.USER_BASE}bell-notifications`,
        markBellNotificationsAsOpened: `${Endpoints.USER_BASE}bell-notifications-opened-state`,
    };

    public static readonly DRIVERS = {
        getAllDrivers: `${Endpoints.DRIVER_BASE}all-drivers`,
        getDriverProfile: `${Endpoints.DRIVER_BASE}driver-profile/{driverIdentifier}`,
        getDriverChartInfo: `${Endpoints.DRIVER_BASE}driver-chart-info/{driverIdentifier}`,
    };

    public static readonly REPORTS = {
        getDriverReportFilterValues: `${Endpoints.REPORTS_BASE}driver-report-filter-values/{season}`,
        getAllDriverReport: `${Endpoints.REPORTS_BASE}all-driver-report/{season}/{round}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getIndividualDriverReport: `${Endpoints.REPORTS_BASE}individual-driver-report/{season}/{driverIdentifier}/{raceType}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getRaceReportFilterValues: `${Endpoints.REPORTS_BASE}race-report-filter-values/{season}`,
        getOverviewRaceReport: `${Endpoints.REPORTS_BASE}overview-race-report/{season}/{type}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getRaceResultReport: `${Endpoints.REPORTS_BASE}race-result-report/{season}/{round}/{type}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getFastestLapsReport: `${Endpoints.REPORTS_BASE}fastest-laps-report/{season}/{round}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getPitStopsReport: `${Endpoints.REPORTS_BASE}pit-stops-report/{season}/{round}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getQualifyingReport: `${Endpoints.REPORTS_BASE}qualifying-report/{season}/{round}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getConstructorReportFilterValues: `${Endpoints.REPORTS_BASE}constructor-report-filter-values/{season}`,
        getOverviewConstructorReport: `${Endpoints.REPORTS_BASE}overview-constructor-report/{season}/{round}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
        getIndividualConstructorReport: `${Endpoints.REPORTS_BASE}individual-constructor-report/{season}/{constructorIdentifier}/{raceType}?sortDirection={sortDirection}&sortColumn={sortColumn}`,
    };

    public static readonly OPEN_DEVELOPMENT = {
        getChangeLogItems: `${Endpoints.OPEN_DEVELOPMENT_BASE}change-log-items`,
    };

    public static readonly DEVELOPMENT = {
        getFeedbackItems: `${Endpoints.DEVELOPMENT_BASE}feedback-items`,
        createFeedbackItem: `${Endpoints.DEVELOPMENT_BASE}feedback-item`,
        likeFeedback: `${Endpoints.DEVELOPMENT_BASE}feedback-item-like/{itemId}`,
        removeFeedbackLike: `${Endpoints.DEVELOPMENT_BASE}feedback-item-like/{itemId}`,
        deleteFeedbackItem: `${Endpoints.DEVELOPMENT_BASE}feedback-item/{itemId}`,
    };

    public static readonly MANAGER_DEVELOPMENT = {
        markAsComplete: `${Endpoints.MANAGER_DEVELOPMENT_BASE}complete-feedback-item/{itemId}`,
        markAsWillNotDo: `${Endpoints.MANAGER_DEVELOPMENT_BASE}close-feedback-item/{itemId}`,
    };

    public static readonly FRIENDS = {
        getInfo: `${Endpoints.FRIENDS_BASE}info`,
        searchFriend: `${Endpoints.FRIENDS_BASE}search/{friendCode}`,
        sendFriendRequest: `${Endpoints.FRIENDS_BASE}friend-request`,
        acceptFriendRequest: `${Endpoints.FRIENDS_BASE}accept-friend-request`,
        declineFriendRequest: `${Endpoints.FRIENDS_BASE}decline-friend-request`,
        blockUser: `${Endpoints.FRIENDS_BASE}block-user`,
        removeFriend: `${Endpoints.FRIENDS_BASE}remove-friend`,
    };
}
