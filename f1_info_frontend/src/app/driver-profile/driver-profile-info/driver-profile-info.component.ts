import {Component, Input, OnChanges} from '@angular/core';
import {DriverProfileResponse} from '../driver-profile.service';
import {WikipediaFetcherService} from '../../wikipedia_fetcher/wikipedia-fetcher.service';

@Component({
    selector: 'app-driver-profile-info',
    templateUrl: './driver-profile-info.component.html',
    styleUrls: ['./driver-profile-info.component.scss'],
})
export class DriverProfileInfoComponent implements OnChanges {
    private static readonly IMAGE_SIZE_PX = 300;

    @Input() public info!: DriverProfileResponse;

    public imageSrc: string | undefined = undefined;
    public summary: string | undefined = undefined;

    public constructor(
        private mWikipediaFetcher: WikipediaFetcherService
    ) {
    }

    public ngOnChanges() {
        this.fetchWikipediaInfo();
    }

    private fetchWikipediaInfo() {
        this.mWikipediaFetcher.getWikipediaImageSrc(this.info.wikipediaTitle, DriverProfileInfoComponent.IMAGE_SIZE_PX).subscribe((src) => {
            this.imageSrc = src;
        });
        this.mWikipediaFetcher.getWikipediaSummary(this.info.wikipediaTitle).subscribe((summary) => {
            this.summary = summary;
        });
    }
}
