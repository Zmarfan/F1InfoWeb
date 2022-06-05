import {environment} from '../../environments/environment';

export interface EndpointSection {
    base: string;
    path: string;
}

export class Endpoints {
    private static readonly BASE_URL: string = environment.baseUrl;
    private static readonly AUTHENTICATION_BASE: string = Endpoints.BASE_URL + 'Authentication/';

    // eslint-disable-next-line @typescript-eslint/member-ordering
    public static readonly AUTHENTICATION = {
        login: `${Endpoints.AUTHENTICATION_BASE}login`,
        register: `${Endpoints.AUTHENTICATION_BASE}register`,
        enableAccount: `${Endpoints.AUTHENTICATION_BASE}login/{token}`,
    };
}
