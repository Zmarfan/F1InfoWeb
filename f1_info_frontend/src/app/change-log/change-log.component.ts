import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {map, Observable} from 'rxjs';
import {ChangeLogService} from './change-log.service';

@Component({
    selector: 'app-change-log',
    templateUrl: './change-log.component.html',
    styleUrls: ['./change-log.component.scss'],
    encapsulation: ViewEncapsulation.None,
})
export class ChangeLogComponent implements OnInit {
    public items$!: Observable<string[]>;

    public constructor(
        private mChangeLogService: ChangeLogService
    ) {
    }

    public ngOnInit() {
        this.items$ = this.mChangeLogService.getChangeLogItems().pipe(map((response) => response.items));
    }
}
