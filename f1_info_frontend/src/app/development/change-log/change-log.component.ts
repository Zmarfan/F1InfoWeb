import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ChangeLogService} from './change-log.service';
import {GlobalMessageService} from '../../../core/information/global-message-display/global-message.service';

@Component({
    selector: 'app-change-log',
    templateUrl: './change-log.component.html',
    styleUrls: ['./change-log.component.scss'],
    encapsulation: ViewEncapsulation.None,
})
export class ChangeLogComponent implements OnInit {
    public items: string[] = [];
    public loading: boolean = false;

    public constructor(
        private mChangeLogService: ChangeLogService,
        private mMessageService: GlobalMessageService
    ) {
    }

    public ngOnInit() {
        this.loading = true;
        this.mChangeLogService.getChangeLogItems().subscribe({
            next: (response) => {
                this.items = response.items;
                this.loading = false;
            },
            error: (e) => this.mMessageService.addHttpError(e),
        });
    }
}
