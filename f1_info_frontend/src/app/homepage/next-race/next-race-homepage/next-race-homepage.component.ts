import {Component, OnDestroy, OnInit} from '@angular/core';
import {NextRaceInfoResponse} from '../../../../generated/server-responses';
import {mergeMap, Subscription, timer} from 'rxjs';
import {HomepageService} from '../../homepage.service';
import {GlobalMessageService} from '../../../../core/information/global-message-display/global-message.service';
import {Router} from '@angular/router';
import {RouteHolder} from '../../../routing/route-holder';
import * as moment from 'moment';

@Component({
    selector: 'app-next-race-homepage',
    templateUrl: './next-race-homepage.component.html',
    styleUrls: ['./next-race-homepage.component.scss'],
})
export class NextRaceHomepageComponent implements OnInit, OnDestroy {
    private static readonly REFRESH_INFO_MS: number = 600000; // 10 min

    public nextRaceResponse: NextRaceInfoResponse | undefined = undefined;

    private mSubscription!: Subscription;

    public constructor(
        private mRouter: Router,
        private mHomepageService: HomepageService,
        private mMessageService: GlobalMessageService
    ) {
    }

    public get timePeriod(): string {
        if (!this.nextRaceResponse) {
            return '';
        }

        const start: string = this.nextRaceResponse.sessionInfo[0].sessionStartTimeMyTime;
        const end: string = this.nextRaceResponse.sessionInfo[this.nextRaceResponse.sessionInfo.length - 1].sessionStartTimeMyTime;
        return `${this.formatDate(start)} - ${this.formatDate(end)}`;
    }

    public ngOnInit() {
        this.mSubscription = timer(0, NextRaceHomepageComponent.REFRESH_INFO_MS).pipe(mergeMap(() => this.mHomepageService.getNextRaceInfo())).subscribe({
            next: (response) => {
                this.nextRaceResponse = response;
            },
            error: (e) => this.mMessageService.addHttpError(e),
        });
    }

    public ngOnDestroy() {
        this.mSubscription.unsubscribe();
    }

    public open() {
        this.mRouter.navigateByUrl(RouteHolder.NEXT_RACE).then();
    }

    private formatDate(date: string): string {
        return moment(date).format('Do MMM YYYY');
    }
}
