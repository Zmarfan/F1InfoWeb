import {Component, Input, OnInit} from '@angular/core';
import {faCircleCheck, faCircleExclamation} from '@fortawesome/free-solid-svg-icons';
import {IconDefinition} from '@fortawesome/fontawesome-common-types';

export enum PageInformationType {
    INFORMATION = 1,
    ERROR = 2,
    SUCCESS = 3,
}

export interface PageInformationConfig {
    type: PageInformationType;
    titleKey: string;
    titleParameters?: object;
    paragraphKeys?: string[];
}

@Component({
    selector: 'app-page-information',
    templateUrl: './page-information.component.html',
    styleUrls: ['./page-information.component.scss'],
})
export class PageInformationComponent implements OnInit {
    @Input() public config!: PageInformationConfig;
    @Input() public loading: boolean = false;

    public get icon(): IconDefinition {
        return this.config.type === PageInformationType.SUCCESS ? faCircleCheck : faCircleExclamation;
    }

    public get cssStyleClass(): string {
        if (this.loading) {
            return '';
        }

        if (this.config.type === PageInformationType.INFORMATION) {
            return 'container--information';
        }
        if (this.config.type === PageInformationType.ERROR) {
            return 'container--error';
        }
        return 'container--success';
    }

    public ngOnInit() {
        this.config.paragraphKeys = this.config.paragraphKeys ?? [];
    }
}
