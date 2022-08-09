import { Injectable } from '@angular/core';
import {GlobalMessageService} from '../../core/information/global-message-display/global-message.service';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class ReportHelperService {

    public constructor(
        private mMessageService: GlobalMessageService
    ) {
    }

    public runAllReport<T, R, Y>(
        assignCallback: (rows: Y[]) => void,
        loadingCallback: (loading: boolean) => void,
        params: T,
        fetchMethod: (params: T) => Observable<R[]>,
        parseMethod: (response: R) => Y
    ) {
        loadingCallback(true);
        fetchMethod(params).subscribe({
            next: (responses) => {
                loadingCallback(false);
                assignCallback(responses.map((response) => parseMethod(response)));
            },
            error: (error) => {
                loadingCallback(false);
                this.mMessageService.addHttpError(error);
            },
        });
    }
}
