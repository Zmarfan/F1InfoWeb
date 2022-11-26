import {Component} from '@angular/core';
import {Session} from '../../configuration/session';
import {exists} from '../../../core/helper/app-util';

@Component({
    selector: 'app-homepage',
    templateUrl: './homepage.component.html',
    styleUrls: ['./homepage.component.scss'],
})
export class HomepageComponent {

    public constructor(
        private mSession: Session
    ) {
    }

    public get showPredictions(): boolean {
        return exists(this.mSession.user);
    }
}
