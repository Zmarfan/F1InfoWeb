import {Component, Input, OnChanges} from '@angular/core';
import {WikipediaFetcherService} from '../../wikipedia_fetcher/wikipedia-fetcher.service';
import {DriverProfileResponse} from '../../../generated/server-responses';
import {GlobalMessageService} from '../../../core/information/global-message-display/global-message.service';

@Component({
    selector: 'app-driver-profile-info',
    templateUrl: './driver-profile-info.component.html',
    styleUrls: ['./driver-profile-info.component.scss'],
})
export class DriverProfileInfoComponent implements OnChanges {
    private static readonly IMAGE_SIZE_PX = 300;

    @Input() public info!: DriverProfileResponse;

    public wikipediaImageLoading: boolean = false;
    public wikipediaSummaryLoading: boolean = false;
    public imageSrc: string | undefined = undefined;
    public summaryParagraphs: string[] = [];

    public constructor(
        private mWikipediaFetcher: WikipediaFetcherService,
        private mMessageService: GlobalMessageService
    ) {
    }

    public ngOnChanges() {
        this.resetValues();
        this.fetchWikipediaInfo();
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
                this.summaryParagraphs = summary;
            },
            error: (error) => {
                this.wikipediaSummaryLoading = false;
                this.mMessageService.addHttpError(error);
            },
        });
    }
}
