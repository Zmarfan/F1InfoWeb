import {Component, Input, OnChanges, OnDestroy, OnInit} from '@angular/core';
import {WikipediaFetcherService} from '../../wikipedia_fetcher/wikipedia-fetcher.service';
import {DriverProfileResponse} from '../../../generated/server-responses';
import {GlobalMessageService} from '../../../core/information/global-message-display/global-message.service';
import {TranslateService} from '@ngx-translate/core';
import {Language} from '../../../common/constants/language';
import {Subscription, tap} from 'rxjs';

@Component({
    selector: 'app-driver-profile-info',
    templateUrl: './driver-profile-info.component.html',
    styleUrls: ['./driver-profile-info.component.scss'],
})
export class DriverProfileInfoComponent implements OnInit, OnDestroy, OnChanges {
    private static readonly IMAGE_SIZE_PX = 300;

    @Input() public info!: DriverProfileResponse;

    public wikipediaImageLoading: boolean = false;
    public wikipediaSummaryLoading: boolean = false;
    public imageSrc: string | undefined = undefined;
    public summaryParagraphs: string[] = [];

    private mLanguageSubscription!: Subscription;

    public constructor(
        private mTranslate: TranslateService,
        private mWikipediaFetcher: WikipediaFetcherService,
        private mMessageService: GlobalMessageService
    ) {
    }

    public get wikipediaParagraphsAvailable(): boolean {
        return this.summaryParagraphs.length > 0;
    }

    public get wikipediaLinkByLanguage(): string {
        if (!this.wikipediaParagraphsAvailable) {
            return this.info.wikipediaUrl;
        }
        return `https://${this.mTranslate.currentLang}${this.info.wikipediaUrl.split('http://en')[1]}`;
    }

    public ngOnInit() {
        this.mLanguageSubscription = this.mTranslate.onLangChange.pipe(tap(() => this.fetchWikipediaInfo())).subscribe();
    }

    public ngOnDestroy() {
        this.mLanguageSubscription.unsubscribe();
    }

    public ngOnChanges() {
        this.resetValues();
        this.fetchWikipediaInfo();
    }

    public displayValue(value: string | number | undefined) {
        return value ?? '-';
    }

    private resetValues() {
        this.imageSrc = undefined;
        this.summaryParagraphs = [];
    }

    private fetchWikipediaInfo() {
        this.wikipediaImageLoading = true;
        this.wikipediaSummaryLoading = true;
        this.mWikipediaFetcher.getWikipediaImageSrc(this.info.wikipediaTitle, DriverProfileInfoComponent.IMAGE_SIZE_PX).subscribe({
            next: (src) => {
                this.wikipediaImageLoading = false;
                this.imageSrc = src;
            },
            error: (error) => {
                this.wikipediaSummaryLoading = false;
                this.mMessageService.addHttpError(error);
            },
        });
        this.mWikipediaFetcher.getWikipediaSummary(this.info.wikipediaTitle).subscribe({
            next: (summary) => {
                this.wikipediaSummaryLoading = false;
                this.summaryParagraphs = summary ?? [];
            },
            error: (error) => {
                this.wikipediaSummaryLoading = false;
                this.mMessageService.addHttpError(error);
            },
        });
    }
}
