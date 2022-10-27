import {Component, OnDestroy, OnInit} from '@angular/core';
import {NextRaceInfoResponse} from '../../../generated/server-responses';
import {mergeMap, Subscription, timer} from 'rxjs';
import {HomepageService} from '../homepage.service';
import {GlobalMessageService} from '../../../core/information/global-message-display/global-message.service';

@Component({
    selector: 'app-next-race',
    templateUrl: './next-race.component.html',
    styleUrls: ['./next-race.component.scss'],
})
export class NextRaceComponent implements OnInit, OnDestroy {
    private static readonly REFRESH_INFO_MS: number = 600000; // 10 min

    public nextRaceResponse: NextRaceInfoResponse | undefined = undefined;

    private mSubscription!: Subscription;

    public constructor(
        private mHomepageService: HomepageService,
        private mMessageService: GlobalMessageService
    ) {
    }

    public ngOnInit() {
        this.mSubscription = timer(0, NextRaceComponent.REFRESH_INFO_MS).pipe(mergeMap(() => this.mHomepageService.getNextRaceInfo())).subscribe({
            next: (response) => {
                this.nextRaceResponse = response;
            },
            error: (e) => this.mMessageService.addHttpError(e),
        });
    }

    public ngOnDestroy() {
        this.mSubscription.unsubscribe();
    }
}
