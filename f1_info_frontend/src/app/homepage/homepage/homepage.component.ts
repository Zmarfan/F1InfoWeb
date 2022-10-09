import { Component } from '@angular/core';
import {Session} from '../../configuration/session';

@Component({
    selector: 'app-homepage',
    templateUrl: './homepage.component.html',
    styleUrls: ['./homepage.component.scss'],
})
export class HomepageComponent {
    public constructor(
        public session: Session
    ) {
    }
}
