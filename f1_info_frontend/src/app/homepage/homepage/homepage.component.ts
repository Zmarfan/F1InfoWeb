import {Component, OnDestroy, OnInit} from '@angular/core';
import {HomepageService} from '../homepage.service';
import {NextRaceInfoResponse} from '../../../generated/server-responses';
import {GlobalMessageService} from '../../../core/information/global-message-display/global-message.service';
import {Subscription} from 'rxjs';

@Component({
    selector: 'app-homepage',
    templateUrl: './homepage.component.html',
    styleUrls: ['./homepage.component.scss'],
})
export class HomepageComponent implements OnInit, OnDestroy {
    public nextRaceResponse: NextRaceInfoResponse | undefined = undefined;

    private mSubscription!: Subscription;

    public constructor(
        private mHomepageService: HomepageService,
        private mMessageService: GlobalMessageService
    ) {
    }

    public ngOnInit() {
        this.mSubscription = this.mHomepageService.getNextRaceInfo().subscribe({
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
